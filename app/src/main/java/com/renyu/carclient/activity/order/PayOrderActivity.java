package com.renyu.carclient.activity.order;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
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
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.commons.CommonUtils;
import com.renyu.carclient.commons.OKHttpHelper;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.GoodsListModel;
import com.renyu.carclient.model.JsonParse;
import com.renyu.carclient.myview.PriceView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by renyu on 15/12/10.
 */
public class PayOrderActivity extends BaseActivity {

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

    int total=0;
    double money=0;

    boolean isBind=true;

    @Override
    public int initContentView() {
        return R.layout.activity_payorder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        models= (ArrayList<GoodsListModel>) getIntent().getExtras().getSerializable("payorder");

        initViews();
    }

    private void initViews() {
        for (int i=0;i<models.size();i++) {
            View view= LayoutInflater.from(this).inflate(R.layout.adapter_ordercenter_payorder, null, false);
            TextView adapter_ordercenter_payorder_title= (TextView) view.findViewById(R.id.adapter_ordercenter_payorder_title);
            adapter_ordercenter_payorder_title.setText(models.get(i).getTitle());
            TextView adapter_ordercenter_payorder_sec_title= (TextView) view.findViewById(R.id.adapter_ordercenter_payorder_sec_title);
            adapter_ordercenter_payorder_sec_title.setText("x"+models.get(i).getQuantity());
            PriceView price_layout= (PriceView) view.findViewById(R.id.price_layout);
            price_layout.setOnTextChangeListener(new PriceView.TextChangeListener() {
                @Override
                public void onTextChange(int num) {
                    if (isBind) {
                        return;
                    }
                    total=0;
                    money=0;
                    for (int i=0;i<models.size();i++) {
                        total+=models.get(i).getQuantity();
                        money+=Double.parseDouble(models.get(i).getPrice());
                    }
                    payorder_all_price.setText("共"+total+"件商品  合计："+money+"元");
                }
            });
            price_layout.setCurrentNum(models.get(i).getQuantity());
            price_layout.setMaxNum(models.get(i).getStore());
            ImageView adapter_ordercenter_payorder_image= (ImageView) view.findViewById(R.id.adapter_ordercenter_payorder_image);
            ImageLoader.getInstance().displayImage(models.get(i).getImage_default_id(), adapter_ordercenter_payorder_image, getAvatarDisplayImageOptions());
            payorder_lists.addView(view);
            total+=models.get(i).getQuantity();
            money+=Double.parseDouble(models.get(i).getPrice());
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
            payorder_invoice_title_layout.setVisibility(View.VISIBLE);
        }
        else {
            payorder_invoice_type_layout.setVisibility(View.GONE);
            payorder_invoice_title_layout.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.payorder_invoice_commit, R.id.payorder_tip_bglayout, R.id.payorder_invoice_type_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.payorder_invoice_commit:
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
                View view1=LayoutInflater.from(PayOrderActivity.this).inflate(R.layout.view_payorder_invoicetype, null, false);
                LinearLayout payorder_invoicetype_layout= (LinearLayout) view1.findViewById(R.id.payorder_invoicetype_layout);
                View view2=LayoutInflater.from(PayOrderActivity.this).inflate(R.layout.view_payorder_invoicetype_child, null, false);
                final CheckBox payorder_invoicetype_normal= (CheckBox) view2.findViewById(R.id.payorder_invoicetype_normal);
                final CheckBox payorder_invoicetype_special= (CheckBox) view2.findViewById(R.id.payorder_invoicetype_special);
                payorder_invoicetype_normal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (payorder_invoicetype_special.isChecked()) {
                            payorder_invoicetype_special.setChecked(false);
                        }
                        invoice_type="normal";
                    }
                });
                payorder_invoicetype_special.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (payorder_invoicetype_normal.isChecked()) {
                            payorder_invoicetype_normal.setChecked(false);
                        }
                        invoice_type="dedicated";
                    }
                });
                payorder_invoicetype_layout.addView(view2);
                payorder_tip_layout.addView(view1);
                openLayout(payorder_tip_layout);
                break;
        }
    }

    private void buyimme() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.buyimme", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("quantity", ""+models.get(0).getQuantity());
        params.put("item_id", ""+models.get(0).getItem_id());
        params.put("user_id", "161");
        params.put("addr_id", "18");
        params.put("need_invoice", payorder_invoice_check.isChecked()?"1":"0");
        if (payorder_invoice_check.isChecked()) {
            params.put("invoice_type", invoice_type);
            params.put("invoice_content", payorder_invoice_title_edit.getText().toString());
            params.put("user_mark", payorder_invoice_message.getText().toString());
            params.put("invoice_title", "unit");
        }
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                if (JsonParse.getResultValue(string)!=null) {
                    showToast(JsonParse.getResultValue(string));
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
        params.put("user_id", "57");
        params.put("addr_id", "18");
        params.put("need_invoice", payorder_invoice_check.isChecked()?"1":"0");
        if (payorder_invoice_check.isChecked()) {
            params.put("invoice_type", invoice_type);
            params.put("invoice_content", payorder_invoice_title_edit.getText().toString());
            params.put("user_mark", payorder_invoice_message.getText().toString());
            params.put("invoice_title", "unit");
        }
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                if (JsonParse.getResultValue(string)!=null) {
                    showToast(JsonParse.getResultValue(string));
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
}
