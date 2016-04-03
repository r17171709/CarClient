package com.renyu.carclient.activity.my;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.renyu.carclient.R;
import com.renyu.carclient.adapter.MyCouponAdapter;
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.commons.ACache;
import com.renyu.carclient.commons.OKHttpHelper;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.CouponModel;
import com.renyu.carclient.model.JsonParse;
import com.renyu.carclient.model.UserModel;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by renyu on 16/3/28.
 */
public class MyCouponActivity extends BaseActivity {

    @Bind(R.id.view_toolbar_center_title)
    TextView view_toolbar_center_title;
    @Bind(R.id.view_toolbar_center_back)
    ImageView view_toolbar_center_back;
    @Bind(R.id.mycoupon_swipy)
    SwipyRefreshLayout mycoupon_swipy;
    @Bind(R.id.mycoupon_rv)
    RecyclerView mycoupon_rv;
    MyCouponAdapter adapter;

    UserModel userModel=null;
    ArrayList<CouponModel> models;

    int page_size=20;
    int page_no=1;

    @Override
    public int initContentView() {
        return R.layout.activity_mycoupon;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userModel= ACache.get(this).getAsObject("user")!=null?(UserModel) ACache.get(this).getAsObject("user"):null;
        models=new ArrayList<>();

        initViews();
        getCoupons();
    }

    private void initViews() {
        view_toolbar_center_title.setText("我的优惠券");
        view_toolbar_center_back.setVisibility(View.VISIBLE);
        adapter=new MyCouponAdapter(this, models);
        mycoupon_rv.setHasFixedSize(true);
        mycoupon_rv.setLayoutManager(new LinearLayoutManager(this));
        mycoupon_rv.setAdapter(adapter);
        mycoupon_swipy.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction==SwipyRefreshLayoutDirection.BOTTOM) {

                }
                else if (direction==SwipyRefreshLayoutDirection.TOP) {
                    page_no=1;
                }
                getCoupons();
            }
        });
    }

    private void getCoupons() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.coupon.list", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("user_id", ""+userModel.getUser_id());
        params.put("page_size", ""+page_size);
        params.put("page_no", ""+page_no);
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                if (page_no==1) {
                    models.clear();
                }
                models.addAll(JsonParse.getCouponModels(string));
                adapter.notifyDataSetChanged();
                page_no++;
                mycoupon_swipy.setRefreshing(false);
            }

            @Override
            public void onError() {

            }
        });
    }

    @OnClick({R.id.view_toolbar_center_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_toolbar_center_back:
                finish();
                break;
        }
    }
}
