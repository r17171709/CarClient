package com.renyu.carclient.activity.order;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.renyu.carclient.R;
import com.renyu.carclient.activity.my.MyAddressChoiceActivity;
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.commons.ACache;
import com.renyu.carclient.commons.CommonUtils;
import com.renyu.carclient.commons.OKHttpHelper;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.AddressModel;
import com.renyu.carclient.model.GoodsListModel;
import com.renyu.carclient.model.JsonParse;
import com.renyu.carclient.model.OrderModel;
import com.renyu.carclient.model.UserModel;
import com.renyu.carclient.myview.PriceView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by renyu on 15/12/10.
 */
public class PayOrderActivity extends BaseActivity {

    @Bind(R.id.payorder_userinfo)
    TextView payorder_userinfo;
    @Bind(R.id.payorder_address)
    TextView payorder_address;
    @Bind(R.id.payorder_lists)
    LinearLayout payorder_lists;
    @Bind(R.id.payorder_invoice_check)
    CheckBox payorder_invoice_check;
    @Bind(R.id.payorder_invoice_message)
    EditText payorder_invoice_message;
    @Bind(R.id.payorder_all_price)
    TextView payorder_all_price;
    @Bind(R.id.payorder_invoice_type_layout)
    RelativeLayout payorder_invoice_type_layout;
    @Bind(R.id.payorder_invoice_type_text)
    TextView payorder_invoice_type_text;
    @Bind(R.id.payorder_invoice_title_layout)
    LinearLayout payorder_invoice_title_layout;
    @Bind(R.id.payorder_invoice_title_edit)
    EditText payorder_invoice_title_edit;
    @Bind(R.id.payorder_invoice_title_type_layout)
    RelativeLayout payorder_invoice_title_type_layout;
    @Bind(R.id.payorder_invoice_title_type_text)
    TextView payorder_invoice_title_type_text;
    @Bind(R.id.payorder_tip_bglayout)
    RelativeLayout payorder_tip_bglayout;
    @Bind(R.id.payorder_tip_layout)
    LinearLayout payorder_tip_layout;

    ArrayList<GoodsListModel> models=null;

    //是否正在执行动画
    boolean isDoAnimation=false;
    //当前展示的viewGroup
    ViewGroup viewGroup=null;
    String invoice_type;
    String invoice_title;

    int total=0;
    double money=0;

    boolean isBind=true;

    UserModel userModel=null;

    AddressModel addressModel;

    @Override
    public int initContentView() {
        return R.layout.activity_payorder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userModel= ACache.get(this).getAsObject("user")!=null?(UserModel) ACache.get(this).getAsObject("user"):null;

        models= (ArrayList<GoodsListModel>) getIntent().getExtras().getSerializable("payorder");

        initViews();

        getAddressList();
    }

    private void initViews() {
        for (int i=0;i<models.size();i++) {
            final int i_=i;
            if (!models.get(i).isChecked()) {
                continue;
            }
            View view= LayoutInflater.from(this).inflate(R.layout.adapter_ordercenter_payorder, null, false);
            TextView adapter_ordercenter_payorder_title= (TextView) view.findViewById(R.id.adapter_ordercenter_payorder_title);
            adapter_ordercenter_payorder_title.setText(models.get(i).getTitle());
            final TextView adapter_ordercenter_payorder_sec_title= (TextView) view.findViewById(R.id.adapter_ordercenter_payorder_sec_title);
            adapter_ordercenter_payorder_sec_title.setText("x"+models.get(i).getQuantity());
            PriceView price_layout= (PriceView) view.findViewById(R.id.price_layout);
            price_layout.setOnTextChangeListener(new PriceView.TextChangeListener() {
                @Override
                public void onTextChange(int num) {
                    Log.d("PayOrderActivity", "onTextChange");
                    if (isBind) {
                        return;
                    }
                    total=0;
                    money=0;
                    for (int i=0;i<models.size();i++) {
                        if (!models.get(i).isChecked()) {
                            continue;
                        }
                        total+=models.get(i).getQuantity();
                        money+=models.get(i).getReal_price().equals("-1")?Double.parseDouble(models.get(i).getPrice())*num:Double.parseDouble(models.get(i).getReal_price())*num;
                    }
                    payorder_all_price.setText("共"+total+"件商品  合计："+money+"元");

                    adapter_ordercenter_payorder_sec_title.setText("x"+num);
                    models.get(i_).setQuantity(num);
                }
            });
            price_layout.setCurrentNum(models.get(i).getQuantity());
            price_layout.setMaxNum(models.get(i).getStore());
            ImageView adapter_ordercenter_payorder_image= (ImageView) view.findViewById(R.id.adapter_ordercenter_payorder_image);
            ImageLoader.getInstance().displayImage(models.get(i).getImage_default_id(), adapter_ordercenter_payorder_image, getAvatarDisplayImageOptions());
            payorder_lists.addView(view);
            total+=models.get(i).getQuantity();
            money+=Double.parseDouble(models.get(i).getReal_price().equals("-1")?models.get(i).getPrice():models.get(i).getReal_price());
        }
        payorder_all_price.setText("共"+total+"件商品  合计："+money+"元");
        isBind=false;
    }

    public DisplayImageOptions getAvatarDisplayImageOptions() {
        return new DisplayImageOptions.Builder()
                .showImageOnFail(R.mipmap.ic_launcher)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    @OnCheckedChanged(R.id.payorder_invoice_check)
    public void onChecked(boolean flag) {
        if (flag) {
            payorder_invoice_type_layout.setVisibility(View.VISIBLE);
            invoice_type="dedicated";
            payorder_invoice_type_text.setText("专用发票");
        }
        else {
            payorder_invoice_type_layout.setVisibility(View.GONE);
            payorder_invoice_title_type_layout.setVisibility(View.GONE);
            payorder_invoice_title_layout.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.payorder_invoice_commit, R.id.payorder_tip_bglayout, R.id.payorder_invoice_type_layout, R.id.payorder_invoice_title_type_layout, R.id.payorder_address_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.payorder_invoice_commit:
                if (addressModel==null) {
                    showToast("请选择默认收货地址");
                    return;
                }
                if (getIntent().getExtras().getBoolean("isCart")) {
                    checkout();
                }
                else {
                    buyimme();
                }
                break;
            case R.id.payorder_tip_bglayout:
                if (viewGroup!=null) {
                    closeLayout(viewGroup);
                }
                break;
            case R.id.payorder_invoice_type_layout:
                payorder_tip_layout.removeAllViews();
                View view1=LayoutInflater.from(PayOrderActivity.this).inflate(R.layout.view_tip_bglayout, null, false);
                TextView tip_bglayout_title_payorder_invoicetype= (TextView) view1.findViewById(R.id.tip_bglayout_title);
                tip_bglayout_title_payorder_invoicetype.setText("发票类型");
                LinearLayout payorder_invoicetype_layout= (LinearLayout) view1.findViewById(R.id.tip_bglayout_layout);
                View view2=LayoutInflater.from(PayOrderActivity.this).inflate(R.layout.view_payorder_invoicetype_child, null, false);
                final TextView payorder_invoicetype_normal= (TextView) view2.findViewById(R.id.payorder_invoicetype_normal);
                final TextView payorder_invoicetype_special= (TextView) view2.findViewById(R.id.payorder_invoicetype_special);
                payorder_invoicetype_normal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        invoice_type="normal";
                        payorder_invoice_type_text.setText("普通发票");
                        payorder_invoice_title_type_layout.setVisibility(View.VISIBLE);
                        payorder_invoice_title_layout.setVisibility(View.VISIBLE);
                        closeLayout(viewGroup);
                    }
                });
                payorder_invoicetype_special.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        invoice_type="dedicated";
                        payorder_invoice_type_text.setText("专用发票");
                        payorder_invoice_title_type_layout.setVisibility(View.GONE);
                        payorder_invoice_title_layout.setVisibility(View.GONE);
                        closeLayout(viewGroup);
                    }
                });
                payorder_invoicetype_layout.addView(view2);
                payorder_tip_layout.addView(view1);
                openLayout(payorder_tip_layout);
                break;
            case R.id.payorder_invoice_title_type_layout:
                payorder_tip_layout.removeAllViews();
                View view3=LayoutInflater.from(PayOrderActivity.this).inflate(R.layout.view_tip_bglayout, null, false);
                TextView tip_bglayout_title_payorder_invoice_title_type= (TextView) view3.findViewById(R.id.tip_bglayout_title);
                tip_bglayout_title_payorder_invoice_title_type.setText("抬头类型");
                LinearLayout payorder_invoicetype_payorder_invoice_title_type_layout= (LinearLayout) view3.findViewById(R.id.tip_bglayout_layout);
                View view4=LayoutInflater.from(PayOrderActivity.this).inflate(R.layout.view_payorder_invoicetype_child, null, false);
                final TextView payorder_invoicetype_unit= (TextView) view4.findViewById(R.id.payorder_invoicetype_normal);
                payorder_invoicetype_unit.setText("单位");
                final TextView payorder_invoicetype_individual= (TextView) view4.findViewById(R.id.payorder_invoicetype_special);
                payorder_invoicetype_individual.setText("个人");
                payorder_invoicetype_unit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        invoice_title="unit";
                        payorder_invoice_title_type_text.setText("单位");
                        closeLayout(viewGroup);
                    }
                });
                payorder_invoicetype_individual.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        invoice_title="individual";
                        payorder_invoice_title_type_text.setText("个人");
                        closeLayout(viewGroup);
                    }
                });
                payorder_invoicetype_payorder_invoice_title_type_layout.addView(view4);
                payorder_tip_layout.addView(view3);
                openLayout(payorder_tip_layout);
                break;
            case R.id.payorder_address_layout:
                Intent intent=new Intent(PayOrderActivity.this, MyAddressChoiceActivity.class);
                Bundle bundle=new Bundle();
                //区分有收货地址和没有收货地址的情况
                if (addressModel==null) {
                    bundle.putInt("addr_id", -1);
                }
                else {
                    bundle.putInt("addr_id", addressModel.getAddr_id());
                }
                intent.putExtras(bundle);
                startActivityForResult(intent, ParamUtils.RESULT_ADDRESS);
                break;
        }
    }

    private void buyimme() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.buyimme", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("quantity", ""+models.get(0).getQuantity());
        params.put("item_id", ""+models.get(0).getItem_id());
        params.put("user_id", ""+userModel.getUser_id());
        params.put("addr_id", ""+addressModel.getAddr_id());
        params.put("need_invoice", payorder_invoice_check.isChecked()?"1":"0");
        if (payorder_invoice_check.isChecked()) {
            params.put("invoice_type", invoice_type);
            if (invoice_type.equals("normal")) {
                params.put("invoice_content", payorder_invoice_title_edit.getText().toString());
                params.put("invoice_title", invoice_title);
            }
            else {
                params.put("invoice_content", userModel.getRepairdepot_name());
                params.put("invoice_title", "unit");
            }
        }
        if (!payorder_invoice_message.getText().toString().equals("")) {
            params.put("user_mark", payorder_invoice_message.getText().toString());
        }
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                if (JsonParse.getResultValue(string)!=null) {
                    showToast(JsonParse.getResultValue(string));
                    if (JsonParse.getResultInt(string)==0) {
                        EventBus.getDefault().post(new OrderModel());
                        finish();
                    }
                }
                else {
                    showToast("未知错误");
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void checkout() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.checkout", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        String cart_ids="";
        String cart_nums="";
        String cart_flag="";
        for (int i=0;i<models.size();i++) {
            if (i==models.size()-1) {
                cart_ids+=models.get(i).getCat_id();
                cart_nums+=models.get(i).getQuantity();
                cart_flag+=models.get(i).isChecked()?"1":"0";
            }
            else {
                cart_ids+=models.get(i).getCat_id()+",";
                cart_nums+=models.get(i).getQuantity()+",";
                cart_flag+=models.get(i).isChecked()?"1,":"0,";
            }
        }
        params.put("cart_ids", cart_ids);
        params.put("cart_nums", cart_nums);
        params.put("cart_flag", cart_flag);
        params.put("user_id", ""+userModel.getUser_id());
        params.put("addr_id", ""+addressModel.getAddr_id());
        params.put("need_invoice", payorder_invoice_check.isChecked()?"1":"0");
        if (payorder_invoice_check.isChecked()) {
            params.put("invoice_type", invoice_type);
            if (invoice_type.equals("normal")) {
                params.put("invoice_content", payorder_invoice_title_edit.getText().toString());
                params.put("invoice_title", invoice_title);
            }
            else {
                params.put("invoice_content", userModel.getRepairdepot_name());
                params.put("invoice_title", "unit");
            }
        }
        if (!payorder_invoice_message.getText().toString().equals("")) {
            params.put("user_mark", payorder_invoice_message.getText().toString());
        }
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                if (JsonParse.getResultValue(string)!=null) {
                    showToast(JsonParse.getResultValue(string));
                    if (JsonParse.getResultInt(string)==0) {
                        EventBus.getDefault().post(new OrderModel());
                        finish();
                    }
                }
                else {
                    showToast("未知错误");
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (isDoAnimation) {
            return;
        }
        if (payorder_tip_bglayout.getVisibility()==View.VISIBLE) {
            closeLayout(payorder_tip_layout);
        }
        else {
            super.onBackPressed();
        }
    }

    private void closeLayout(final ViewGroup layout) {
        ValueAnimator animator0=ValueAnimator.ofFloat(0, 1f);
        animator0.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ArgbEvaluator argbEvaluator = new ArgbEvaluator();
                payorder_tip_bglayout.setBackgroundColor((Integer) argbEvaluator.evaluate(animation.getAnimatedFraction(), Color.parseColor("#70000000"), Color.parseColor("#00000000")));
            }
        });
        ObjectAnimator animator1=ObjectAnimator.ofFloat(layout, "translationY", 0, CommonUtils.dip2px(PayOrderActivity.this, 300));
        animator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isDoAnimation=false;
                payorder_tip_bglayout.setVisibility(View.GONE);
                layout.setVisibility(View.GONE);
                viewGroup=null;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isDoAnimation=true;
            }
        });
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.setDuration(300);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(animator0, animator1);
        animatorSet.start();
    }

    private void openLayout(final ViewGroup layout) {
        ValueAnimator animator0=ValueAnimator.ofFloat(0, 1f);
        animator0.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ArgbEvaluator argbEvaluator = new ArgbEvaluator();
                payorder_tip_bglayout.setBackgroundColor((Integer) argbEvaluator.evaluate(animation.getAnimatedFraction(), Color.parseColor("#00000000"), Color.parseColor("#70000000")));
            }
        });
        ObjectAnimator animator1=ObjectAnimator.ofFloat(layout, "translationY", CommonUtils.dip2px(PayOrderActivity.this, 300), 0);
        animator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isDoAnimation=false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isDoAnimation=true;
                payorder_tip_bglayout.setVisibility(View.VISIBLE);
                layout.setVisibility(View.VISIBLE);
                viewGroup=layout;
            }
        });
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.setDuration(300);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(animator0, animator1);
        animatorSet.start();
    }

    private void getAddressList() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.address.list", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("user_id", ""+userModel.getUser_id());
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                ArrayList<AddressModel> models=JsonParse.getAddressModels(string);
                for (int i=0;i<models.size();i++) {
                    if (models.get(i).getDef_addr()==1) {
                        addressModel=models.get(i);
                        break;
                    }
                }
                if (addressModel!=null) {
                    payorder_userinfo.setText(addressModel.getName()+" "+addressModel.getMobile());
                    String address="";
                    for (int i=0;i<addressModel.getArea().split("/").length;i++) {
                        address+= CommonUtils.getCityInfo(addressModel.getArea().split("/")[i])+" ";
                    }
                    address+=" "+addressModel.getAddr();
                    payorder_address.setText(address);
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            if (requestCode==ParamUtils.RESULT_ADDRESS) {
                if (data.getExtras().getSerializable("addr_id")!=null) {
                    addressModel= (AddressModel) data.getExtras().getSerializable("addr_id");

                    payorder_userinfo.setText(addressModel.getName()+" "+addressModel.getMobile());
                    String address="";
                    for (int i=0;i<addressModel.getArea().split("/").length;i++) {
                        address+= CommonUtils.getCityInfo(addressModel.getArea().split("/")[i])+" ";
                    }
                    address+=" "+addressModel.getAddr();
                    payorder_address.setText(address);
                }
            }
        }
    }
}
