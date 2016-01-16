package com.renyu.carclient.activity.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.adapter.SearchCarTypeAdapter;
import com.renyu.carclient.adapter.SearchCarTypeChildAdapter;
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.commons.CommonUtils;
import com.renyu.carclient.commons.OKHttpHelper;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.JsonParse;
import com.renyu.carclient.model.SearchCarTypeChildModel;
import com.renyu.carclient.model.SearchCarTypeHeadModel;
import com.renyu.carclient.model.SearchCarTypeModel;
import com.renyu.carclient.myview.LetterIndicatorView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by renyu on 15/12/1.
 */
public class SearchCarTypeActivity extends BaseActivity {

    @Bind(R.id.view_toolbar_center_title)
    TextView view_toolbar_center_title;
    @Bind(R.id.view_toolbar_center_back)
    ImageView view_toolbar_center_back;
    @Bind(R.id.searchcartype_letterindicator)
    LetterIndicatorView searchcartype_letterindicator;
    SearchCarTypeAdapter adapter=null;
    @Bind(R.id.searchcartype_rv)
    RecyclerView searchcartype_rv;
    LinearLayoutManager manager=null;
    @Bind(R.id.searchcartype_child)
    RecyclerView searchcartype_child;
    SearchCarTypeChildAdapter childAdapter=null;
    @Bind(R.id.searchcartype_edit)
    EditText searchcartype_edit;
    @Bind(R.id.searchcartype_rv2)
    RecyclerView searchcartype_rv2;
    SearchCarTypeAdapter adapter2=null;

    //全部一级目录对象集合
    ArrayList<SearchCarTypeModel> allModels;
    //首字母序列
    ArrayList<String> letterIndicator=null;
    //listview对应首字母位置
    HashMap<String, Integer> sections=null;
    //首字母对应对象集合
    HashMap<String, ArrayList<SearchCarTypeModel>> models=null;
    //列表加载对象
    ArrayList<Object> tempModels=null;
    ArrayList<Object> childModels=null;
    ArrayList<Object> tempModels2=null;

    String lastBrand="";

    @Override
    public int initContentView() {
        return R.layout.activity_searchcartype;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sections=new HashMap<>();
        models=new HashMap<>();
        tempModels=new ArrayList<>();
        tempModels2=new ArrayList<>();
        childModels=new ArrayList<>();

        initViews();
        getCarAllType();
    }

    private void initViews() {
        view_toolbar_center_title.setText("车型");
        view_toolbar_center_back.setVisibility(View.VISIBLE);
        searchcartype_letterindicator.setOnLetterChangedListener(new LetterIndicatorView.OnLetterChangedListener() {
            @Override
            public void LetterChanged(int position) {
                manager.scrollToPositionWithOffset(sections.get(letterIndicator.get(position))+1, 0);
            }
        });
        searchcartype_rv.setHasFixedSize(true);
        manager=new LinearLayoutManager(this);
        searchcartype_rv.setLayoutManager(manager);
        adapter=new SearchCarTypeAdapter(this, tempModels, new SearchCarTypeAdapter.OnOperationListener() {
            @Override
            public void positionChoice(String brand, int position) {
                if (lastBrand.equals(brand)) {
                    if (searchcartype_child.getVisibility()== View.VISIBLE) {
                        searchcartype_child.setVisibility(View.GONE);
                    }
                    else {
                        searchcartype_child.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    if (position!=-1) {
                        for (int i=0;i<tempModels.size();i++) {
                            if (tempModels.get(i) instanceof SearchCarTypeModel) {
                                ((SearchCarTypeModel) tempModels.get(i)).setSelect(false);
                            }
                        }
                        ((SearchCarTypeModel) tempModels.get(position)).setSelect(true);
                        adapter.notifyDataSetChanged();
                    }
                    searchcartype_child.setVisibility(View.VISIBLE);
                    getCarTypeByPosition(brand);
                }
            }
        });
        searchcartype_rv.setAdapter(adapter);

        searchcartype_child.setHasFixedSize(true);
        searchcartype_child.setLayoutManager(new LinearLayoutManager(this));
        childAdapter=new SearchCarTypeChildAdapter(this, childModels, new SearchCarTypeChildAdapter.OnMenuChoiceListener() {
            @Override
            public void openClose(int position) {
                boolean flag=((SearchCarTypeChildModel) (childModels.get(position+1))).isOpen();
                for (int i=0;i<childModels.size();i++) {
                    if (childModels.get(i) instanceof SearchCarTypeChildModel) {
                        if (((SearchCarTypeChildModel) (childModels.get(i))).getFlag().equals(childModels.get(position).toString())) {
                            ((SearchCarTypeChildModel) (childModels.get(i))).setOpen(!flag);
                        }
                    }
                }
                childAdapter.notifyDataSetChanged();
            }

            @Override
            public void selected(int position) {
                for (int i=0;i<childModels.size();i++) {
                    if (childModels.get(i) instanceof SearchCarTypeChildModel) {
                        ((SearchCarTypeChildModel) (childModels.get(i))).setSelect(false);
                    }
                }
                ((SearchCarTypeChildModel) (childModels.get(position))).setSelect(true);
                childAdapter.notifyDataSetChanged();
            }
        });

        adapter2=new SearchCarTypeAdapter(SearchCarTypeActivity.this, tempModels2, new SearchCarTypeAdapter.OnOperationListener() {
            @Override
            public void positionChoice(String brand, int position) {
                if (lastBrand.equals(brand)) {
                    if (searchcartype_child.getVisibility()== View.VISIBLE) {
                        searchcartype_child.setVisibility(View.GONE);
                    }
                    else {
                        searchcartype_child.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    if (position!=-1) {
                        for (int i=0;i<tempModels2.size();i++) {
                            if (tempModels2.get(i) instanceof SearchCarTypeModel) {
                                ((SearchCarTypeModel) tempModels2.get(i)).setSelect(false);
                            }
                        }
                        ((SearchCarTypeModel) tempModels2.get(position)).setSelect(true);
                        adapter2.notifyDataSetChanged();
                    }
                    searchcartype_child.setVisibility(View.VISIBLE);
                    getCarTypeByPosition(brand);
                }
            }
        });
        searchcartype_rv2.setHasFixedSize(true);
        searchcartype_rv2.setLayoutManager(new LinearLayoutManager(this));
        searchcartype_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (allModels!=null) {
                    if (s.toString().equals("")) {
                        searchcartype_rv2.setVisibility(View.GONE);
                    }
                    else {
                        searchcartype_rv2.setVisibility(View.VISIBLE);
                        tempModels2.clear();
                        for (int i=0;i<allModels.size();i++) {
                            if (allModels.get(i).getBrand().indexOf(s.toString())!=-1) {
                                allModels.get(i).setSelect(false);
                                tempModels2.add(allModels.get(i));
                            }
                        }
                        searchcartype_rv2.setAdapter(adapter2);
                    }
                }
            }
        });
    }

    /**
     * 首字母排序
     */
    private void letterIndicatorCompare(ArrayList<SearchCarTypeModel> results, final ArrayList<SearchCarTypeModel> hotModels) {
        Observable.just(results).map(new Func1<ArrayList<SearchCarTypeModel>, HashMap<String, ArrayList<SearchCarTypeModel>>>() {
            @Override
            public HashMap<String, ArrayList<SearchCarTypeModel>> call(ArrayList<SearchCarTypeModel> SearchCarTypeModels) {
                HashMap<String, ArrayList<SearchCarTypeModel>> map = new HashMap<>();
                for (int i = 0; i < SearchCarTypeModels.size(); i++) {
                    String firstLetter = CommonUtils.getFirstPinyin(SearchCarTypeModels.get(i).getBrand());
                    if (map.containsKey(firstLetter)) {
                        ArrayList<SearchCarTypeModel> arrayList = map.get(firstLetter);
                        arrayList.add(SearchCarTypeModels.get(i));
                    } else {
                        ArrayList<SearchCarTypeModel> arrayList = new ArrayList<>();
                        arrayList.add(SearchCarTypeModels.get(i));
                        map.put(firstLetter, arrayList);
                    }
                }
                return map;
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<HashMap<String, ArrayList<SearchCarTypeModel>>>() {
            @Override
            public void call(HashMap<String, ArrayList<SearchCarTypeModel>> stringArrayListHashMap) {
                //排序key
                ArrayList<String> keys=new ArrayList<>();
                Iterator<String> iterator=stringArrayListHashMap.keySet().iterator();
                while (iterator.hasNext()) {
                    String key= iterator.next();
                    keys.add(key);
                }
                Collections.sort(keys, new MySort());
                letterIndicator=keys;
                searchcartype_letterindicator.setLetters(letterIndicator);
                models=stringArrayListHashMap;
                refreshList(hotModels);
            }
        });
    }

    class MySort implements Comparator {
        public int compare(Object o1, Object o2) {
            String s1 = String.valueOf(o1);
            String s2 = String.valueOf(o2);
            return s1.compareTo(s2);
        }
    }

    public void refreshList(ArrayList<SearchCarTypeModel> hotModels) {
        tempModels.clear();
        sections.clear();
        SearchCarTypeHeadModel headModel=new SearchCarTypeHeadModel();
        headModel.setLists(hotModels);
        tempModels.add(headModel);
        ArrayList<Object> values=new ArrayList<>();
        for (int i=0;i<letterIndicator.size();i++) {
            sections.put(letterIndicator.get(i), values.size());
            //添加大写字母序号
            values.add(letterIndicator.get(i));
            //得到大写字母中所含有的model
            ArrayList<SearchCarTypeModel> searchCarTypeModels=models.get(letterIndicator.get(i));
            for (int j=0;j<searchCarTypeModels.size();j++) {
                SearchCarTypeModel SearchCarTypeModel=searchCarTypeModels.get(j);
                values.add(SearchCarTypeModel);
            }
        }
        tempModels.addAll(values);

        adapter.notifyDataSetChanged();
    }

    private void getCarAllType() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.modelsmatch.api.getBrands", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                allModels=JsonParse.getCarType(string);
                ArrayList<SearchCarTypeModel> hotModels=JsonParse.getHotCarType(string);
                if (tempModels!=null) {
                    letterIndicatorCompare(allModels, hotModels);
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

    private void getCarTypeByPosition(String brand) {
        HashMap<String, String> params= ParamUtils.getSignParams("app.modelsmatch.api.getCars", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("brand", brand);
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                ArrayList<Object> tempmodels=JsonParse.getCarTypeChilds(string);
                if (tempmodels==null) {
                    showToast("未知错误");
                }
                else {
                    childModels.clear();
                    childModels.addAll(tempmodels);
                    searchcartype_child.setAdapter(childAdapter);
                }
            }

            @Override
            public void onError() {
                showToast(getResources().getString(R.string.network_error));
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

    @Override
    public void onBackPressed() {
        if (searchcartype_child.getVisibility()==View.VISIBLE) {
            searchcartype_child.setVisibility(View.GONE);
        }
        else if (searchcartype_rv2.getVisibility()==View.VISIBLE) {
            searchcartype_rv2.setVisibility(View.GONE);
        }
        else {
            super.onBackPressed();
        }
    }
}
