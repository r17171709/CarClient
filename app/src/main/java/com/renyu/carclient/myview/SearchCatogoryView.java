package com.renyu.carclient.myview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.renyu.carclient.R;
import com.renyu.carclient.adapter.SearchViewAdapter;
import com.renyu.carclient.adapter.SearchViewTwoAdapter;
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

    RecyclerView goods_catogory_grandparent;
    SearchViewAdapter goods_catogory_adapter_grandparent;
    RecyclerView goods_catogory_parent;
    SearchViewTwoAdapter goods_catogory_adapter_parent;

    OnFinalChoiceListener listener;

    int cat_id=-1;

    int width=0;
    int height=0;

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
        goods_catogory_adapter_parent=new SearchViewTwoAdapter(context, secondModels, new SearchViewTwoAdapter.OnMenuChoiceListener() {
            @Override
            public void openClose(int position) {
                boolean flag=secondModels.get(position).isOpen();
                int parentId=secondModels.get(position).getCat_id();
                for (int i=0;i<secondModels.size();i++) {
                    if (secondModels.get(i).getParent_id()==parentId) {
                        secondModels.get(i).setOpen(!flag);
                    }
                }
                secondModels.get(position).setOpen(!flag);
                goods_catogory_adapter_parent.notifyDataSetChanged();
            }
        }, new SearchViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                for (int i=0;i<secondModels.size();i++) {
                    secondModels.get(i).setSelect(false);
                }
                secondModels.get(position).setSelect(true);
                goods_catogory_adapter_parent.notifyDataSetChanged();
                listener.onChoicePosition(""+secondModels.get(position).getCat_id());
            }
        });
        goods_catogory_parent.setAdapter(goods_catogory_adapter_parent);

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
        goods_catogory_grandparent.layout(0, 0, width/2, height);
        goods_catogory_parent.layout(width/2, 0, width, height);
    }

    private void category() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.category", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                ArrayList<CategoryModel> models= JsonParse.getCategoryListModel(string);
                if (models!=null) {
                    for (int i=0;i<models.size();i++) {
                        models.get(i).setOpen(false);
                    }
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
                ArrayList<CategoryModel> models=JsonParse.getSecondCategoryListModel(string);
                if (models!=null) {
                    for (int i=0;i<models.size();i++) {
                        secondModels.add(models.get(i));
                        secondModels.addAll(models.get(i).getLists());
                    }
                    goods_catogory_parent.setAdapter(goods_catogory_adapter_parent);
                }
                else {
                    goods_catogory_adapter_parent.notifyDataSetChanged();
                }
            }

            @Override
            public void onError() {

            }
        });
    }

}
