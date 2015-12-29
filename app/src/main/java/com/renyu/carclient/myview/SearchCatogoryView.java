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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by renyu on 15/12/28.
 */
public class SearchCatogoryView extends LinearLayout {

    OKHttpHelper httpHelper=null;

    Context context=null;

    ArrayList<CategoryModel> firstModels=null;
    ArrayList<CategoryModel> secondModels=null;
    ArrayList<CategoryModel> thirdModels=null;
    HashMap<String, ArrayList<CategoryModel>> childModels=null;

    RecyclerView goods_catogory_grandparent;
    SearchViewAdapter goods_catogory_adapter_grandparent;
    RecyclerView goods_catogory_parent;
    SearchViewAdapter goods_catogory_adapter_parent;
    RecyclerView goods_catogory_child;
    SearchViewAdapter goods_catogory_adapter_child;

    OnFinalChoiceListener listener;

    int cat_fir_id=-1;
    int cat_sec_id=-1;
    int cat_id=-1;

    int width=0;
    int height=0;

    public void setCatFirId(int cat_fir_id) {
        this.cat_fir_id=cat_fir_id;
    }

    public void setCatSecId(int cat_sec_id) {
        this.cat_sec_id=cat_sec_id;
    }

    public void setCatId(int cat_id) {
        this.cat_id=cat_id;
    }


    public interface OnFinalChoiceListener {
        void onChoicePosition(String cat_id);
    }

    public void setOnFinalChoiceListener(OnFinalChoiceListener listener) {
        this.listener=listener;
    }

    public SearchCatogoryView(Context context) {
        this(context, null);
    }

    public SearchCatogoryView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        this.context=context;
        httpHelper=new OKHttpHelper();

        firstModels=new ArrayList<>();
        secondModels=new ArrayList<>();
        thirdModels=new ArrayList<>();
        childModels=new HashMap<>();

        setOrientation(HORIZONTAL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        goods_catogory_grandparent= (RecyclerView) findViewById(R.id.goods_catogory_grandparent);
        goods_catogory_grandparent.setHasFixedSize(true);
        goods_catogory_grandparent.setLayoutManager(new LinearLayoutManager(context));
        goods_catogory_adapter_grandparent=new SearchViewAdapter(context, firstModels, new SearchViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                categorySecond(firstModels.get(position).getCat_id());
            }
        });
        goods_catogory_grandparent.setAdapter(goods_catogory_adapter_grandparent);

        goods_catogory_parent= (RecyclerView) findViewById(R.id.goods_catogory_parent);
        goods_catogory_parent.setHasFixedSize(true);
        goods_catogory_parent.setLayoutManager(new LinearLayoutManager(context));
        goods_catogory_adapter_parent=new SearchViewAdapter(context, secondModels, new SearchViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                thirdModels.clear();
                thirdModels.addAll(childModels.get(""+secondModels.get(position).getCat_id()));
                goods_catogory_child.setAdapter(goods_catogory_adapter_child);
            }
        });
        goods_catogory_parent.setAdapter(goods_catogory_adapter_parent);

        goods_catogory_child= (RecyclerView) findViewById(R.id.goods_catogory_child);
        goods_catogory_child.setHasFixedSize(true);
        goods_catogory_child.setLayoutManager(new LinearLayoutManager(context));
        goods_catogory_adapter_child=new SearchViewAdapter(context, thirdModels, new SearchViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                listener.onChoicePosition(""+thirdModels.get(position).getCat_id());
            }
        });
        goods_catogory_child.setAdapter(goods_catogory_adapter_child);

        category();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width=getMeasuredWidth();
        height=getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        goods_catogory_grandparent.layout(0, 0, width/3, height);
        goods_catogory_parent.layout(width/3, 0, width*2/3, height);
        goods_catogory_child.layout(width*2/3, 0, width, height);
    }

    private void category() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.category", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                ArrayList<CategoryModel> models= JsonParse.getCategoryListModel(string);
                if (models!=null) {
                    firstModels.addAll(models);
                    goods_catogory_adapter_grandparent.notifyDataSetChanged();
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void categorySecond(int cat_id) {
        HashMap<String, String> params = ParamUtils.getSignParams("app.user.category", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("cat_id", "" + cat_id);
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                secondModels.clear();
                thirdModels.clear();
                ArrayList<CategoryModel> models=JsonParse.getSecondCategoryListModel(string);
                if (models!=null) {
                    childModels.clear();
                    for (int i=0;i<models.size();i++) {
                        childModels.put(""+models.get(i).getCat_id(), models.get(i).getLists());
                        secondModels.add(models.get(i));
                        if (i==0) {
                            thirdModels.addAll(models.get(i).getLists());
                        }
                    }
                    goods_catogory_parent.setAdapter(goods_catogory_adapter_parent);
                    goods_catogory_child.setAdapter(goods_catogory_adapter_child);
                }
                else {
                    goods_catogory_adapter_parent.notifyDataSetChanged();
                    goods_catogory_adapter_child.notifyDataSetChanged();
                }
            }

            @Override
            public void onError() {

            }
        });
    }

}
