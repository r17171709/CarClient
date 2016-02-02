package com.renyu.carclient.activity.search;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.renyu.carclient.R;
import com.renyu.carclient.activity.login.LoginActivity;
import com.renyu.carclient.activity.order.CartActivity;
import com.renyu.carclient.adapter.GoodsListGridAdapter;
import com.renyu.carclient.adapter.GoodsListLinearAdapter;
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.commons.ACache;
import com.renyu.carclient.commons.OKHttpHelper;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.GoodsListModel;
import com.renyu.carclient.model.JsonParse;
import com.renyu.carclient.model.UserModel;
import com.renyu.carclient.myview.SearchBrandView;
import com.renyu.carclient.myview.SearchCarTypeView;
import com.renyu.carclient.myview.SearchCatogoryView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by renyu on 15/10/18.
 */
public class GoodsListActivity extends BaseActivity {

    @Bind(R.id.view_toolbar_center_back)
    ImageView view_toolbar_center_back;
    @Bind(R.id.view_toolbar_center_next)
    ImageView view_toolbar_center_next;
    @Bind(R.id.view_toolbar_center_title)
    TextView view_toolbar_center_title;
    @Bind(R.id.goods_list_swipy)
    SwipyRefreshLayout goods_list_swipy;
    @Bind(R.id.goods_list_rv)
    RecyclerView goods_list_rv;
    @Bind(R.id.goods_list_changetype)
    ImageView goods_list_changetype;
    @Bind(R.id.goods_list_choice)
    LinearLayout goods_list_choice;
    @Bind(R.id.goods_list_edit)
    EditText goods_list_edit;
    @Bind(R.id.goods_list_screening_text)
    TextView goods_list_screening_text;
    @Bind(R.id.goods_list_sale)
    TextView goods_list_sale;
    @Bind(R.id.goods_list_price)
    TextView goods_list_price;

    PopupWindow pop=null;

    GoodsListLinearAdapter linearAdapter=null;
    GoodsListGridAdapter gridAdapter=null;

    ArrayList<GoodsListModel> models=null;

    boolean isLinearMode=true;

    int page_no=1;

    String current_cat_id="";
    String current_brand_id="";
    String current_car_id="";
    String current_search="";

    UserModel userModel=null;

    //销量排序
    int choiceNum=0;
    //价格排序
    int choicePrice=0;

    @Override
    public int initContentView() {
        return R.layout.activity_goodslist;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userModel= ACache.get(this).getAsObject("user")!=null?(UserModel) ACache.get(this).getAsObject("user"):null;

        models=new ArrayList<>();

        initViews();
    }

    private void initViews() {
        view_toolbar_center_title.setText("商品列表");
        view_toolbar_center_back.setVisibility(View.VISIBLE);
        view_toolbar_center_next.setVisibility(View.VISIBLE);
        view_toolbar_center_next.setImageResource(R.mipmap.ic_goodslist_cart);

        goods_list_swipy.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        goods_list_swipy.setDirection(SwipyRefreshLayoutDirection.BOTH);
        goods_list_swipy.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction==SwipyRefreshLayoutDirection.BOTTOM) {

                }
                else if (direction==SwipyRefreshLayoutDirection.TOP) {
                    page_no=1;
                }
                if (getIntent().getExtras().getString("type").equals(ParamUtils.CAT)) {
                    getAllLists(current_cat_id, "", "");
                }
                else if (getIntent().getExtras().getString("type").equals(ParamUtils.BRAND)) {
                    getAllLists(current_cat_id, current_brand_id, "");
                }
                else if (getIntent().getExtras().getString("type").equals(ParamUtils.CAR)) {
                    getAllLists2(current_car_id, "");
                }
                else {
                    getAllLists3(current_search, "");
                }
            }
        });
        goods_list_rv.setHasFixedSize(true);
        goods_list_rv.setLayoutManager(new LinearLayoutManager(this));
        linearAdapter=new GoodsListLinearAdapter(this, models);
        gridAdapter=new GoodsListGridAdapter(this, models);

        View popView= LayoutInflater.from(this).inflate(R.layout.pop_goodslist, null, false);
        TextView pop_goodslist_grid= (TextView) popView.findViewById(R.id.pop_goodslist_grid);
        pop_goodslist_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLinearMode) {
                    goods_list_rv.setLayoutManager(new GridLayoutManager(GoodsListActivity.this, 2));
                    goods_list_rv.setAdapter(gridAdapter);
                    isLinearMode=!isLinearMode;
                }
                goods_list_changetype.setImageResource(R.mipmap.ic_goods_gridtype);
                pop.dismiss();
            }
        });
        TextView pop_goodslist_list= (TextView) popView.findViewById(R.id.pop_goodslist_list);
        pop_goodslist_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLinearMode) {
                    goods_list_rv.setLayoutManager(new LinearLayoutManager(GoodsListActivity.this));
                    goods_list_rv.setAdapter(linearAdapter);
                    isLinearMode=!isLinearMode;
                }
                goods_list_changetype.setImageResource(R.mipmap.ic_goods_listtype);
                pop.dismiss();
            }
        });
        pop=new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setTouchable(true);
        pop.setOutsideTouchable(true);

        if (getIntent().getExtras().getString("type").equals(ParamUtils.CAT)) {
            final SearchCatogoryView view= (SearchCatogoryView) LayoutInflater.from(this).inflate(R.layout.view_search_category, null, false);
            view.setCatId(getIntent().getExtras().getInt("cat_id"));
            view.setOnFinalChoiceListener(new SearchCatogoryView.OnFinalChoiceListener() {
                @Override
                public void onChoicePosition(String cat_id) {
                    current_cat_id=cat_id;
                    page_no=1;
                    goods_list_choice.setVisibility(View.GONE);
                    goods_list_swipy.setRefreshing(true);
                    getAllLists(cat_id, "", "");
                }
            });
            goods_list_choice.addView(view);
            goods_list_choice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goods_list_choice.setVisibility(View.GONE);
                }
            });
        }
        else if (getIntent().getExtras().getString("type").equals(ParamUtils.BRAND)){
            final SearchBrandView view=(SearchBrandView) LayoutInflater.from(this).inflate(R.layout.view_search_brand, null, false);
            view.setCatId(getIntent().getExtras().getInt("cat_id"));
            view.setBrandId(getIntent().getExtras().getInt("brand_id"));
            view.setOnFinalChoiceListener(new SearchBrandView.OnFinalChoiceListener() {
                @Override
                public void onChoicePosition(String cat_id, String brand_id) {
                    current_cat_id=cat_id;
                    current_brand_id=brand_id;
                    page_no=1;
                    goods_list_choice.setVisibility(View.GONE);
                    goods_list_swipy.setRefreshing(true);
                    getAllLists(cat_id, brand_id, "");
                }
            });
            goods_list_choice.addView(view);
            goods_list_choice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goods_list_choice.setVisibility(View.GONE);
                }
            });
        }
        else if (getIntent().getExtras().getString("type").equals(ParamUtils.CAR)) {
            final SearchCarTypeView view= (SearchCarTypeView) LayoutInflater.from(this).inflate(R.layout.view_search_cartype, null, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goods_list_choice.setVisibility(View.GONE);
                }
            });
            view.setOnFinalChoiceListener(new SearchCarTypeView.OnFinalChoiceListener() {
                @Override
                public void onChoicePosition(String brand) {
                    getAllLists2(brand, "");
                }
            });
            goods_list_choice.addView(view);
            goods_list_choice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.setVisibility(View.GONE);
                }
            });
        }

        if (getIntent().getExtras().getString("type").equals(ParamUtils.CAT)) {
            current_cat_id=""+getIntent().getExtras().getInt("cat_id");
            getAllLists(current_cat_id, "", "");
        }
        else if (getIntent().getExtras().getString("type").equals(ParamUtils.BRAND)) {
            current_cat_id=""+getIntent().getExtras().getInt("cat_id");
            current_brand_id=""+getIntent().getExtras().getInt("brand_id");
            getAllLists(current_cat_id, current_brand_id, "");
        }
        else if (getIntent().getExtras().getString("type").equals(ParamUtils.CAR)) {
            current_car_id=getIntent().getExtras().getString("nlevelid");
            getAllLists2(current_car_id, "");
        }
        else {
            current_search=getIntent().getExtras().getString("keyWords");
            goods_list_edit.setText(current_search);
            getAllLists3(current_search, "");
        }
        goods_list_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_SEARCH) {
                    Intent intent=new Intent(GoodsListActivity.this, GoodsListActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("type", ParamUtils.SEARCH);
                    bundle.putString("keyWords", goods_list_edit.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    @OnClick({R.id.goods_list_price, R.id.goods_list_sale, R.id.goods_list_screening, R.id.goods_list_changetype, R.id.view_toolbar_center_back, R.id.view_toolbar_center_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goods_list_price:
                goods_list_screening_text.setTextColor(Color.parseColor("#707070"));
                goods_list_sale.setTextColor(Color.parseColor("#707070"));
                goods_list_price.setTextColor(Color.parseColor("#cc0000"));
                goods_list_choice.setVisibility(View.GONE);

                goods_list_swipy.setRefreshing(true);
                choiceNum=0;
                page_no=1;
                String order2=choicePrice==0?"price asc":"price desc";
                if (getIntent().getExtras().getString("type").equals(ParamUtils.CAT)) {
                    getAllLists(current_cat_id, "", order2);
                }
                else if (getIntent().getExtras().getString("type").equals(ParamUtils.BRAND)) {
                    getAllLists(current_cat_id, current_brand_id, order2);
                }
                else if (getIntent().getExtras().getString("type").equals(ParamUtils.CAR)) {
                    getAllLists2(current_car_id, order2);
                }
                else {
                    getAllLists3(current_search, order2);
                }
                if (choicePrice==0) {
                    choicePrice=1;
                }
                else if (choicePrice==1) {
                    choicePrice=0;
                }
                break;
            case R.id.goods_list_sale:
                goods_list_screening_text.setTextColor(Color.parseColor("#707070"));
                goods_list_sale.setTextColor(Color.parseColor("#cc0000"));
                goods_list_price.setTextColor(Color.parseColor("#707070"));
                goods_list_choice.setVisibility(View.GONE);

                goods_list_swipy.setRefreshing(true);
                choicePrice=0;
                page_no=1;
                String order=choiceNum==0?"sold_quantity asc":"sold_quantity desc";
                if (getIntent().getExtras().getString("type").equals(ParamUtils.CAT)) {
                    getAllLists(current_cat_id, "", order);
                }
                else if (getIntent().getExtras().getString("type").equals(ParamUtils.BRAND)) {
                    getAllLists(current_cat_id, current_brand_id, order);
                }
                else if (getIntent().getExtras().getString("type").equals(ParamUtils.CAR)) {
                    getAllLists2(current_car_id, order);
                }
                else {
                    getAllLists3(current_search, order);
                }
                if (choiceNum==0) {
                    choiceNum=1;
                }
                else if (choiceNum==1) {
                    choiceNum=0;
                }
                break;
            case R.id.goods_list_screening:
                goods_list_screening_text.setTextColor(Color.parseColor("#cc0000"));
                goods_list_sale.setTextColor(Color.parseColor("#707070"));
                goods_list_price.setTextColor(Color.parseColor("#707070"));

                choiceNum=0;
                choicePrice=0;
                if (getIntent().getExtras().getString("type").equals(ParamUtils.SEARCH)) {
                    getAllLists3(current_search, "");
                    return;
                }
                if (goods_list_choice.getVisibility()==View.VISIBLE) {
                    goods_list_choice.setVisibility(View.GONE);
                }
                else {
                    goods_list_choice.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.goods_list_changetype:
                if (pop.isShowing()) {
                    pop.dismiss();
                    return;
                }
                pop.showAsDropDown(goods_list_changetype, 0, 0);
                break;
            case R.id.view_toolbar_center_back:
                finish();
                break;
            case R.id.view_toolbar_center_next:
                if (userModel==null) {
                    Intent intent=new Intent(GoodsListActivity.this, LoginActivity.class);
                    startActivityForResult(intent, ParamUtils.RESULT_LOGIN);
                    return;
                }
                Intent intent=new Intent(GoodsListActivity.this, CartActivity.class);
                startActivity(intent);
        }
    }

    private void getAllLists(String cat_id, String brand_id, String orderBy) {
        current_cat_id=cat_id;
        current_brand_id=brand_id;
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.item.list", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        if (getIntent().getExtras().getString("type").equals(ParamUtils.CAT)) {
            params.put("cat_id", cat_id);
        }
        else if (getIntent().getExtras().getString("type").equals(ParamUtils.BRAND)) {
            if (!cat_id.equals("-1")) {
                params.put("cat_id", cat_id);
            }
            params.put("brand_id", brand_id);
        }
        if (!orderBy.equals("")) {
            params.put("orderBy", orderBy);
        }
        params.put("page_size", "20");
        params.put("page_no", ""+page_no);
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                ArrayList<GoodsListModel> tempModels= JsonParse.getGoodsListModel(string);
                if (tempModels!=null) {
                    if (page_no==1) {
                        models.clear();
                    }
                    models.addAll(tempModels);
                    if (isLinearMode) {
                        if (page_no==1) {
                            goods_list_rv.setAdapter(linearAdapter);
                        }
                        else {
                            linearAdapter.notifyDataSetChanged();
                        }
                    }
                    else {
                        if (page_no==1) {
                            goods_list_rv.setAdapter(gridAdapter);
                        }
                        else {
                            gridAdapter.notifyDataSetChanged();
                        }
                    }
                    if (tempModels.size()==0) {
                        if (page_no==1) {
                            showToast("搜索结果为空");
                        }
                        else {
                            showToast("没有更多数据了");
                        }
                    }
                    page_no++;
                }
                else {
                    showToast("未知错误");
                }
                goods_list_swipy.setRefreshing(false);
            }

            @Override
            public void onError() {
                goods_list_swipy.setRefreshing(false);
                showToast(getResources().getString(R.string.network_error));
            }
        });
    }

    private void getAllLists2(String keyWords, String orderBy) {
        httpHelper.cancel(ParamUtils.api);
        current_car_id=keyWords;
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.item.search", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("page_size", "20");
        params.put("page_no", ""+page_no);
        params.put("search_keywords", keyWords);
        params.put("isCar", "1");
        if (!orderBy.equals("")) {
            params.put("orderBy", orderBy);
        }
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                ArrayList<GoodsListModel> tempModels= JsonParse.getGoodsListModel(string);
                if (tempModels!=null) {
                    if (page_no==1) {
                        models.clear();
                    }
                    models.addAll(tempModels);
                    if (isLinearMode) {
                        if (page_no==1) {
                            goods_list_rv.setAdapter(linearAdapter);
                        }
                        else {
                            linearAdapter.notifyDataSetChanged();
                        }
                    }
                    else {
                        if (page_no==1) {
                            goods_list_rv.setAdapter(gridAdapter);
                        }
                        else {
                            gridAdapter.notifyDataSetChanged();
                        }
                    }
                    if (tempModels.size()==0) {
                        if (page_no==1) {
                            showToast("搜索结果为空");
                        }
                        else {
                            showToast("没有更多数据了");
                        }
                    }
                    page_no++;
                }
                else {
                    showToast("未知错误");
                }
                goods_list_swipy.setRefreshing(false);
            }

            @Override
            public void onError() {
                goods_list_swipy.setRefreshing(false);
                showToast(getResources().getString(R.string.network_error));
            }
        });
    }

    private void getAllLists3(String keyWords, String orderBy) {
        httpHelper.cancel(ParamUtils.api);
        current_search=keyWords;
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.item.search", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("page_size", "20");
        params.put("page_no", ""+page_no);
        params.put("search_keywords", keyWords);
        if (!orderBy.equals("")) {
            params.put("orderBy", orderBy);
        }
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                ArrayList<GoodsListModel> tempModels= JsonParse.getGoodsListModel(string);
                if (tempModels!=null) {
                    if (page_no==1) {
                        models.clear();
                    }
                    models.addAll(tempModels);
                    if (isLinearMode) {
                        if (page_no==1) {
                            goods_list_rv.setAdapter(linearAdapter);
                        }
                        else {
                            linearAdapter.notifyDataSetChanged();
                        }
                    }
                    else {
                        if (page_no==1) {
                            goods_list_rv.setAdapter(gridAdapter);
                        }
                        else {
                            gridAdapter.notifyDataSetChanged();
                        }
                    }
                    if (tempModels.size()==0) {
                        if (page_no==1) {
                            showToast("搜索结果为空");
                        }
                        else {
                            showToast("没有更多数据了");
                        }
                    }
                    page_no++;
                }
                else {
                    showToast("未知错误");
                }
                goods_list_swipy.setRefreshing(false);
            }

            @Override
            public void onError() {
                goods_list_swipy.setRefreshing(false);
                showToast(getResources().getString(R.string.network_error));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (userModel!= ACache.get(this).getAsObject("user")) {
            userModel= ACache.get(this).getAsObject("user")!=null?(UserModel) ACache.get(this).getAsObject("user"):null;
            if (isLinearMode) {
                linearAdapter.notifyDataSetChanged();
            }
            else {
                gridAdapter.notifyDataSetChanged();
            }
        }
    }
}
