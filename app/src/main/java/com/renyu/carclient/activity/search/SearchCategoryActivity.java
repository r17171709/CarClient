package com.renyu.carclient.activity.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.adapter.SearchCategoryAdapter;
import com.renyu.carclient.adapter.SearchCategoryChildAdapter;
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.commons.OKHttpHelper;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.CategoryModel;
import com.renyu.carclient.model.JsonParse;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by renyu on 15/10/18.
 */
public class SearchCategoryActivity extends BaseActivity {

    @Bind(R.id.view_toolbar_center_title)
    TextView view_toolbar_center_title;
    @Bind(R.id.view_toolbar_center_back)
    ImageView view_toolbar_center_back;
    @Bind(R.id.searchcategory_rv)
    RecyclerView searchcategory_rv;
    SearchCategoryAdapter adapter=null;
    @Bind(R.id.searchcategory_child_rv)
    RecyclerView searchcategory_child_rv;
    SearchCategoryChildAdapter childAdapter=null;

    ArrayList<CategoryModel> allModels=null;
    ArrayList<CategoryModel> childModels=null;

    HashMap<String, ArrayList<CategoryModel>> tempCategory=null;

    int currentChoice=-1;

    @Override
    public int initContentView() {
        return R.layout.activity_searchcategory;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        allModels=new ArrayList<>();
        childModels=new ArrayList<>();
        tempCategory=new HashMap<>();

        initViews();
    }

    @OnClick(R.id.view_toolbar_center_back)
    public void onClick(View view) {
        finish();
    }

    private void initViews() {
        view_toolbar_center_title.setText("分类");
        view_toolbar_center_back.setVisibility(View.VISIBLE);
        searchcategory_rv.setHasFixedSize(true);
        RecyclerView.LayoutManager manager= new LinearLayoutManager(this);
        searchcategory_rv.setLayoutManager(manager);
        adapter=new SearchCategoryAdapter(this, allModels, new SearchCategoryAdapter.OnCategoryChoiceListener() {
            @Override
            public void choice(int position) {
                httpHelper.cancel(ParamUtils.api);
                if (currentChoice!=position) {
                    currentChoice=position;
                    for (int i=0;i<allModels.size();i++) {
                        allModels.get(i).setSelect(false);
                    }
                    allModels.get(position).setSelect(true);
                    adapter.notifyDataSetChanged();

                    searchcategory_child_rv.setVisibility(View.VISIBLE);
                    if (tempCategory.containsKey(allModels.get(position).getCat_id())) {
                        ArrayList<CategoryModel> models=tempCategory.get(allModels.get(position).getCat_id());
                        childModels.clear();
                        for (int i=0;i<models.size();i++) {
                            childModels.add(models.get(i));
                            childModels.addAll(models.get(i).getLists());
                        }
                        childAdapter.setFirstChoiceCat(allModels.get(currentChoice).getCat_id());
                        searchcategory_child_rv.setAdapter(childAdapter);
                    }
                    else {
                        categorySecond(allModels.get(position).getCat_id());
                    }
                }
                else {
                    if (searchcategory_child_rv.getVisibility()==View.VISIBLE) {
                        searchcategory_child_rv.setVisibility(View.GONE);
                    }
                    else {
                        searchcategory_child_rv.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        searchcategory_rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        searchcategory_child_rv.setHasFixedSize(true);
        searchcategory_child_rv.setLayoutManager(new LinearLayoutManager(this));
        childAdapter=new SearchCategoryChildAdapter(this, childModels, new SearchCategoryChildAdapter.OnMenuChoiceListener() {
            @Override
            public void openClose(int position) {
                boolean flag=childModels.get(position).isOpen();
                int parentId=childModels.get(position).getCat_id();
                for (int i=0;i<childModels.size();i++) {
                    if (childModels.get(i).getParent_id()==parentId) {
                        childModels.get(i).setOpen(!flag);
                    }
                }
                childModels.get(position).setOpen(!flag);
                childAdapter.notifyDataSetChanged();
            }

            @Override
            public void choiceItem(int position) {
                for (int i=0;i<childModels.size();i++) {
                    childModels.get(i).setSelect(false);
                }
                childModels.get(position).setSelect(true);
                childAdapter.notifyDataSetChanged();
            }
        });

        category();
    }

    private void category() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.category", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                ArrayList<CategoryModel> models=JsonParse.getCategoryListModel(string);
                if (models!=null) {
                    allModels.addAll(models);
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

    private void categorySecond(final int cat_id) {
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.category", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("cat_id", ""+cat_id);
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                ArrayList<CategoryModel> models=JsonParse.getSecondCategoryListModel(string);
                if (models!=null) {
                    childModels.clear();
                    for (int i=0;i<models.size();i++) {
                        childModels.add(models.get(i));
                        childModels.addAll(models.get(i).getLists());
                    }
                    childAdapter.setFirstChoiceCat(allModels.get(currentChoice).getCat_id());
                    searchcategory_child_rv.setAdapter(childAdapter);
                    tempCategory.put(""+cat_id, models);
                }
                else {
                    childModels.clear();
                    searchcategory_child_rv.setAdapter(null);
                    showToast("未知错误");
                }
            }

            @Override
            public void onError() {
                showToast(getResources().getString(R.string.network_error));
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (searchcategory_child_rv.getVisibility()==View.VISIBLE) {
            searchcategory_child_rv.setVisibility(View.GONE);
        }
        else {
            super.onBackPressed();
        }
    }
}
