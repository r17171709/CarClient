package com.renyu.carclient.activity.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.renyu.carclient.R;
import com.renyu.carclient.adapter.GoodsListLinearAdapter;
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.commons.ACache;
import com.renyu.carclient.commons.OKHttpHelper;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.GoodsListModel;
import com.renyu.carclient.model.JsonParse;
import com.renyu.carclient.model.UserModel;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;

/**
 * Created by renyu on 16/1/12.
 */
public class GoodsListSearchActivity extends BaseActivity {

    @Bind(R.id.view_toolbar_center_back)
    ImageView view_toolbar_center_back;
    @Bind(R.id.view_toolbar_center_title)
    TextView view_toolbar_center_title;
    @Bind(R.id.goodslist_search_swipy)
    SwipyRefreshLayout goodslist_search_swipy;
    @Bind(R.id.goodslist_search_rv)
    RecyclerView goodslist_search_rv;
    GoodsListLinearAdapter linearAdapter=null;

    ArrayList<GoodsListModel> models=null;

    String keyWords="";
    int page_no=1;

    UserModel userModel=null;

    @Override
    public int initContentView() {
        return R.layout.activity_goodslistsearch;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userModel= ACache.get(this).getAsObject("user")!=null?(UserModel) ACache.get(this).getAsObject("user"):null;
        keyWords=getIntent().getExtras().getString("keyWords");
        models=new ArrayList<>();

        initViews();
    }

    private void initViews() {
        view_toolbar_center_title.setText("商品列表");
        view_toolbar_center_back.setVisibility(View.VISIBLE);
        goodslist_search_swipy.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        goodslist_search_swipy.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction==SwipyRefreshLayoutDirection.BOTTOM) {

                }
                else if (direction==SwipyRefreshLayoutDirection.TOP) {
                    page_no=1;
                }
                getAllLists();
            }
        });
        goodslist_search_rv.setHasFixedSize(true);
        goodslist_search_rv.setLayoutManager(new LinearLayoutManager(this));
        linearAdapter=new GoodsListLinearAdapter(this, models);
        goodslist_search_rv.setAdapter(linearAdapter);
        getAllLists();
    }

    private void getAllLists() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.item.search", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("page_size", "20");
        params.put("page_no", ""+page_no);
        params.put("search_keywords", keyWords);
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                ArrayList<GoodsListModel> tempModels= JsonParse.getGoodsListModel(string);
                if (tempModels!=null) {
                    if (page_no==1) {
                        models.clear();
                    }
                    models.addAll(tempModels);
                    page_no++;
                    linearAdapter.notifyDataSetChanged();
                }
                else {
                    showToast("未知错误");
                }
                goodslist_search_swipy.setRefreshing(false);
            }

            @Override
            public void onError() {
                goodslist_search_swipy.setRefreshing(false);
                showToast(getResources().getString(R.string.network_error));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (userModel!= ACache.get(this).getAsObject("user")) {
            userModel= ACache.get(this).getAsObject("user")!=null?(UserModel) ACache.get(this).getAsObject("user"):null;
            linearAdapter.notifyDataSetChanged();
        }
    }
}
