package com.renyu.carclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.renyu.carclient.R;
import com.renyu.carclient.activity.search.SearchBrandActivity;
import com.renyu.carclient.activity.search.SearchCarTypeActivity;
import com.renyu.carclient.activity.search.SearchCategoryActivity;
import com.renyu.carclient.adapter.SearchhotBrandAdapter;
import com.renyu.carclient.adapter.SearchhotCarTypeAdapter;
import com.renyu.carclient.adapter.SearchhotCategoryAdapter;
import com.renyu.carclient.base.BaseFragment;
import com.renyu.carclient.myview.NoScrollGridView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by renyu on 15/10/18.
 */
public class SearchFragment extends BaseFragment {

    @Bind(R.id.search_hot_category)
    NoScrollGridView search_hot_category;
    @Bind(R.id.search_hot_brand)
    NoScrollGridView search_hot_brand;
    @Bind(R.id.search_hot_cartype)
    NoScrollGridView search_hot_cartype;

    SearchhotCategoryAdapter hotCateAdapter=null;
    SearchhotBrandAdapter hotbrandAdapter=null;
    SearchhotCarTypeAdapter hotCarTypeAdapter=null;

    ArrayList<String> modelsHotCate=null;
    ArrayList<String> modelsHotBrand=null;
    ArrayList<String> modelsHotCarType=null;

    @Override
    public int initContentView() {
        return R.layout.fragment_search;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        modelsHotCate=new ArrayList<>();
        modelsHotCate.add("");
        modelsHotCate.add("");
        modelsHotCate.add("");
        modelsHotCate.add("");
        modelsHotCate.add("");
        modelsHotCate.add("");
        modelsHotCate.add("");
        modelsHotCate.add("");
        modelsHotCate.add("");
        modelsHotCate.add("");
        modelsHotBrand=new ArrayList<>();
        modelsHotBrand.add("");
        modelsHotBrand.add("");
        modelsHotBrand.add("");
        modelsHotBrand.add("");
        modelsHotBrand.add("");
        modelsHotBrand.add("");
        modelsHotBrand.add("");
        modelsHotBrand.add("");
        modelsHotBrand.add("");
        modelsHotBrand.add("");
        modelsHotBrand.add("");
        modelsHotBrand.add("");
        modelsHotBrand.add("");
        modelsHotBrand.add("");
        modelsHotBrand.add("");
        modelsHotBrand.add("");
        modelsHotBrand.add("");
        modelsHotBrand.add("");
        modelsHotBrand.add("");
        modelsHotBrand.add("");
        modelsHotBrand.add("");
        modelsHotBrand.add("");
        modelsHotBrand.add("");
        modelsHotBrand.add("");
        modelsHotCarType=new ArrayList<>();
        modelsHotCarType.add("");
        modelsHotCarType.add("");
        modelsHotCarType.add("");
        modelsHotCarType.add("");
        modelsHotCarType.add("");
        modelsHotCarType.add("");
        modelsHotCarType.add("");
        modelsHotCarType.add("");
        modelsHotCarType.add("");
        modelsHotCarType.add("");

        initViews();
    }

    private void initViews() {
        hotCateAdapter=new SearchhotCategoryAdapter(getActivity(), modelsHotCate);
        search_hot_category.setAdapter(hotCateAdapter);
        hotbrandAdapter=new SearchhotBrandAdapter(getActivity(), modelsHotBrand);
        search_hot_brand.setAdapter(hotbrandAdapter);
        hotCarTypeAdapter=new SearchhotCarTypeAdapter(getActivity(), modelsHotCarType);
        search_hot_cartype.setAdapter(hotCarTypeAdapter);
    }

    @OnClick({R.id.search_category, R.id.search_brand, R.id.search_cartype})
    public void jumpClick(View view) {
        switch (view.getId()) {
            case R.id.search_category:
                Intent intent1=new Intent(getActivity(), SearchCategoryActivity.class);
                startActivity(intent1);
                break;
            case R.id.search_brand:
                Intent intent2=new Intent(getActivity(), SearchBrandActivity.class);
                startActivity(intent2);
                break;
            case R.id.search_cartype:
                Intent intent3=new Intent(getActivity(), SearchCarTypeActivity.class);
                startActivity(intent3);
                break;

        }
    }
}
