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
import com.renyu.carclient.model.SearchCategoryModel;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by renyu on 15/10/18.
 */
public class SearchCategoryActivity extends BaseActivity {

    @Bind(R.id.view_toolbar_center_image)
    ImageView view_toolbar_center_image;
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

    ArrayList<String> allModels=null;
    ArrayList<SearchCategoryModel> childModels=null;

    @Override
    public int initContentView() {
        return R.layout.activity_searchcategory;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        allModels=new ArrayList<>();
        allModels.add("123");
        allModels.add("123");
        allModels.add("123");
        allModels.add("123");
        allModels.add("123");
        allModels.add("123");
        allModels.add("123");
        allModels.add("123");
        allModels.add("123");
        allModels.add("123");
        childModels=new ArrayList<>();

        initViews();
    }

    @OnClick(R.id.view_toolbar_center_back)
    public void onClick(View view) {
        finish();
    }

    private void initViews() {
        view_toolbar_center_image.setImageResource(R.mipmap.ic_category_logo);
        view_toolbar_center_title.setText("");
        view_toolbar_center_back.setVisibility(View.VISIBLE);
        searchcategory_rv.setHasFixedSize(true);
        RecyclerView.LayoutManager manager= new LinearLayoutManager(this);
        searchcategory_rv.setLayoutManager(manager);
        adapter=new SearchCategoryAdapter(this, allModels, new SearchCategoryAdapter.OnCategoryChoiceListener() {
            @Override
            public void choice(int position) {
                openChild(position);
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
                int parentId=childModels.get(position).getId();
                for (int i=0;i<childModels.size();i++) {
                    if (childModels.get(i).getParentId()==parentId) {
                        childModels.get(i).setIsOpen(!flag);
                    }
                }
                childModels.get(position).setIsOpen(!flag);
                childAdapter.notifyDataSetChanged();
            }
        });
    }

    private void openChild(int position) {
        ArrayList<SearchCategoryModel> tempModels=new ArrayList<>();
        for (int i=0;i<10;i++) {
            SearchCategoryModel parentModel=new SearchCategoryModel();
            parentModel.setId(i);
            parentModel.setImageUrl("");
            parentModel.setIsOpen(true);
            parentModel.setParentId(-1);
            parentModel.setText("acascas"+i);
            ArrayList<SearchCategoryModel> temp=new ArrayList<>();
            for (int j=0;j<5;j++) {
                SearchCategoryModel childModel=new SearchCategoryModel();
                childModel.setId(j);
                childModel.setImageUrl("");
                childModel.setIsOpen(true);
                childModel.setParentId(i);
                childModel.setText("acascaschild"+j);
                temp.add(childModel);
            }
            parentModel.setLists(temp);
            tempModels.add(parentModel);
        }

        childModels.clear();
        for (int i=0;i<tempModels.size();i++) {
            childModels.add(tempModels.get(i));
            childModels.addAll(tempModels.get(i).getLists());
        }
        searchcategory_child_rv.setAdapter(childAdapter);
    }
}
