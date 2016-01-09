package com.renyu.carclient.myview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.renyu.carclient.R;
import com.renyu.carclient.adapter.SearchViewAdapter;
import com.renyu.carclient.commons.OKHttpHelper;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.CategoryModel;
import com.renyu.carclient.model.JsonParse;
import com.renyu.carclient.model.SearchBrandModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by renyu on 15/12/28.
 */
public class SearchBrandView extends LinearLayout {

    OKHttpHelper httpHelper=null;

    Context context=null;

    OnFinalChoiceListener listener;

    RecyclerView goods_brand_parent;
    SearchViewAdapter goods_brand_parent_adapter;
    RecyclerView goods_brand_child;
    SearchViewAdapter goods_brand_child_adapter;

    ArrayList<CategoryModel> firstModels=null;
    ArrayList<CategoryModel> secondModels=null;

    int cat_id=-1;
    int brand_id=-1;

    int width=0;
    int height=0;

    public void setCatId(int cat_id) {
        this.cat_id=cat_id;
    }

    public void setBrandId(int brand_id) {
        this.brand_id=brand_id;
    }

    public interface OnFinalChoiceListener {
        void onChoicePosition(String cat_id, String brand_id);
    }

    public void setOnFinalChoiceListener(OnFinalChoiceListener listener) {
        this.listener=listener;
    }

    public SearchBrandView(Context context) {
        this(context, null);
    }

    public SearchBrandView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        this.context=context;
        httpHelper=new OKHttpHelper();

        firstModels=new ArrayList<>();
        secondModels=new ArrayList<>();

        setOrientation(HORIZONTAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        goods_brand_parent= (RecyclerView) findViewById(R.id.goods_brand_parent);
        goods_brand_parent.setHasFixedSize(true);
        goods_brand_parent.setLayoutManager(new LinearLayoutManager(context));
        goods_brand_parent_adapter=new SearchViewAdapter(context, firstModels, new SearchViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                brand_id=firstModels.get(position).getCat_id();
                getBrandList(firstModels.get(position).getCat_id());
            }
        });
        goods_brand_parent.setAdapter(goods_brand_parent_adapter);

        goods_brand_child= (RecyclerView) findViewById(R.id.goods_brand_child);
        goods_brand_child.setHasFixedSize(true);
        goods_brand_child.setLayoutManager(new LinearLayoutManager(context));
        goods_brand_child_adapter=new SearchViewAdapter(context, secondModels, new SearchViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                listener.onChoicePosition(""+secondModels.get(position).getCat_id(), ""+brand_id);
            }
        });
        goods_brand_child.setAdapter(goods_brand_child_adapter);

        getBrandList();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width=getMeasuredWidth();
        height=getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        goods_brand_parent.layout(0, 0, width/2, height);
        goods_brand_child.layout(width/2, 0, width, height);
    }

    private void getBrandList() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.brand.list", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                ArrayList<SearchBrandModel> models= JsonParse.getSearchBrandListModel(string);
                for (int i=0;i<models.size();i++) {
                    CategoryModel model=new CategoryModel();
                    model.setCat_name(models.get(i).getBrand_name());
                    model.setCat_id(models.get(i).getBrand_id());
                    model.setOpen(false);
                    firstModels.add(model);
                }
                goods_brand_parent_adapter.notifyDataSetChanged();
            }

            @Override
            public void onError() {

            }
        });
    }

    private void getBrandList(int brand_id) {
        secondModels.clear();
        goods_brand_child.setAdapter(null);
        httpHelper.cancel(ParamUtils.api);

        HashMap<String, String> params= ParamUtils.getSignParams("app.user.brand.list", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("brand_id", ""+brand_id);
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                ArrayList<SearchBrandModel> models= JsonParse.getSecondSearchBrandModel(string);
                for (int i=0;i<models.size();i++) {
                    CategoryModel model=new CategoryModel();
                    model.setCat_name(models.get(i).getCat_name());
                    model.setCat_id(Integer.parseInt(models.get(i).getCat_id()));
                    model.setOpen(false);
                    secondModels.add(model);
                }
                goods_brand_child.setAdapter(goods_brand_child_adapter);
            }

            @Override
            public void onError() {

            }
        });
    }
}
