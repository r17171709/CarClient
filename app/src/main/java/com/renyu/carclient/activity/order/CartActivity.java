package com.renyu.carclient.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.adapter.CartAdapter;
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.commons.ACache;
import com.renyu.carclient.commons.OKHttpHelper;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.GoodsListModel;
import com.renyu.carclient.model.JsonParse;
import com.renyu.carclient.model.ProductModel;
import com.renyu.carclient.model.UserModel;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by renyu on 15/12/13.
 */
public class CartActivity extends BaseActivity {

    @Bind(R.id.view_toolbar_center_title)
    TextView view_toolbar_center_title;
    @Bind(R.id.view_toolbar_center_back)
    ImageView view_toolbar_center_back;
    @Bind(R.id.view_toolbar_center_text_next)
    TextView view_toolbar_center_text_next;
    @Bind(R.id.cart_content_rv)
    RecyclerView cart_content_rv;
    CartAdapter adapter=null;
    @Bind(R.id.cart_choice_all)
    CheckBox cart_choice_all;
    @Bind(R.id.cart_allmoney)
    TextView cart_allmoney;

    ArrayList<ProductModel> models=null;

    boolean isPermitChecked=false;

    UserModel userModel=null;

    @Override
    public int initContentView() {
        return R.layout.activity_cart;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userModel= ACache.get(this).getAsObject("user")!=null?(UserModel) ACache.get(this).getAsObject("user"):null;

        models=new ArrayList<>();

        initViews();
    }

    private void initViews() {
        view_toolbar_center_title.setText("购物车");
        view_toolbar_center_back.setVisibility(View.VISIBLE);
        view_toolbar_center_text_next.setVisibility(View.VISIBLE);
        view_toolbar_center_text_next.setText("编辑");
        cart_content_rv.setHasFixedSize(true);
        cart_content_rv.setLayoutManager(new LinearLayoutManager(this));
        adapter=new CartAdapter(this, models, new CartAdapter.OnCheckChangedListener() {
            @Override
            public void onCheckChanged(int position, boolean flag) {
                int checkedCount = 0;
                for (int i = 0; i < models.size(); i++) {
                    if (models.get(i).isChecked()) {
                        checkedCount++;
                    }
                }
                if (checkedCount == models.size() && !cart_choice_all.isChecked()) {
                    isPermitChecked = true;
                    cart_choice_all.setChecked(true);
                } else if (checkedCount != models.size() && cart_choice_all.isChecked()) {
                    isPermitChecked = true;
                    cart_choice_all.setChecked(false);
                } else {
                    cart_choice_all.setChecked(false);
                }

                double totalPrice=0;
                for (int i=0;i<models.size();i++) {
                    String price=models.get(i).getReal_price().equals("-1")?models.get(i).getPrice():models.get(i).getReal_price();
                    if (models.get(i).isChecked())
                    totalPrice+=Double.parseDouble(price)*models.get(i).getQuantity();
                }
                cart_allmoney.setText("合计："+totalPrice+"元");
            }
        }, new CartAdapter.OnDeleteListener() {
            @Override
            public void onDeleteValue(int position) {
                delete(models.get(position).getItem_id());
            }
        });
        cart_content_rv.setAdapter(adapter);
    }

    @OnClick({R.id.view_toolbar_center_text_next, R.id.view_toolbar_center_back, R.id.cart_cal, R.id.cart_collection, R.id.cart_choice_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_toolbar_center_text_next:
                for (int i=0;i<models.size();i++) {
                    models.get(i).setEdit(!models.get(i).isEdit());
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.cart_cal:
                int num=0;
                for (int i=0;i<models.size();i++) {
                    if (models.get(i).isChecked()) {
                        num++;
                    }
                }
                if (num==0) {
                    showToast("请至少选择一个商品");
                    return;
                }
                ArrayList<GoodsListModel> listModels=new ArrayList<>();
                for (int i=0;i<models.size();i++) {
                    GoodsListModel model=new GoodsListModel();
                    model.setQuantity(models.get(i).getQuantity());
                    model.setImage_default_id(models.get(i).getImage_default_id());
                    model.setTitle(models.get(i).getTitle());
                    model.setPrice(models.get(i).getPrice());
                    model.setCat_id(models.get(i).getCart_id());
                    model.setItem_id(models.get(i).getItem_id());
                    model.setChecked(models.get(i).isChecked());
                    model.setReal_price(models.get(i).getReal_price());
                    listModels.add(model);
                }
                Intent intent=new Intent(CartActivity.this, PayOrderActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("payorder", listModels);
                bundle.putBoolean("isCart", true);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.cart_collection:
                String items="";
                for (int i=0;i<models.size();i++) {
                    if (models.get(i).isChecked()) {
                        items+=models.get(i).getItem_id()+",";
                    }
                }
                items=items.substring(0, items.length()-1);
                if (items.equals("")) {
                    showToast("请至少选择一个商品");
                    return;
                }
                moveToFav(items);
                break;
            case R.id.view_toolbar_center_back:
                finish();
                break;
            case R.id.cart_choice_all:
                double totalPrice=0;
                for (int i=0;i<models.size();i++) {
                    String price=models.get(i).getReal_price().equals("-1")?models.get(i).getPrice():models.get(i).getReal_price();
                    if (models.get(i).isChecked())
                        totalPrice+=Double.parseDouble(price)*models.get(i).getQuantity();
                }
                cart_allmoney.setText("合计："+totalPrice+"元");
                break;
        }
    }

    @OnCheckedChanged(R.id.cart_choice_all)
    public void onCheckedChanged(boolean flag) {
        if (isPermitChecked) {
            isPermitChecked=false;
            return;
        }
        for (int i=0;i<models.size();i++) {
            models.get(i).setChecked(flag);
        }
        adapter.notifyDataSetChanged();
    }

    private void cartList() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.cart", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("user_id", ""+userModel.getUser_id());
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                models.clear();
                ArrayList<ProductModel> temp=JsonParse.getCartList(string);
                if (temp!=null) {
                    models.addAll(temp);
                    adapter.notifyDataSetChanged();
                }
                else {
                    showToast("未知错误");
                }
            }

            @Override
            public void onError() {
                showToast(getResources().getString(R.string.network_error));
            }
        });
    }

    private void moveToFav(String item_id) {
        HashMap<String, String> params= ParamUtils.getSignParams("app.itemcollect.move", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("user_id", ""+userModel.getUser_id());
        params.put("item_id", item_id);
        httpHelper.commonPostRequest(ParamUtils.api, params, new OKHttpHelper.StartListener() {
            @Override
            public void onStart() {
                showDialog("提示", "正在添加中");
            }
        }, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                dismissDialog();
                if (JsonParse.getResultValue(string)!=null) {
                    showToast(JsonParse.getResultValue(string));
                    if (JsonParse.getResultInt(string)==0) {
                        cartList();
                    }
                }
                else {
                    showToast("未知错误");
                }
            }

            @Override
            public void onError() {
                dismissDialog();
                showToast(getResources().getString(R.string.network_error));
            }
        });
    }

    private void delete(int item_id) {
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.check.delete", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("user_id", ""+userModel.getUser_id());
        params.put("item_id", ""+item_id);
        httpHelper.commonPostRequest(ParamUtils.api, params, new OKHttpHelper.StartListener() {
            @Override
            public void onStart() {
                showDialog("提示", "正在添加中");
            }
        }, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                dismissDialog();
                if (JsonParse.getResultValue(string)!=null) {
                    showToast(JsonParse.getResultValue(string));
                    if (JsonParse.getResultInt(string)==0) {
                        cartList();
                    }
                }
                else {
                    showToast("未知错误");
                }
            }

            @Override
            public void onError() {
                dismissDialog();
                showToast(getResources().getString(R.string.network_error));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        cartList();
    }
}
