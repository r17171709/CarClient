package com.renyu.carclient.activity.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.adapter.SearchBrandAdapter;
import com.renyu.carclient.adapter.SearchBrandChildAdapter;
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.commons.CommonUtils;
import com.renyu.carclient.model.SearchBrandModel;
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
 * Created by renyu on 15/10/18.
 */
public class SearchBrandActivity extends BaseActivity {


    @Bind(R.id.view_toolbar_center_image)
    ImageView view_toolbar_center_image;
    @Bind(R.id.view_toolbar_center_title)
    TextView view_toolbar_center_title;
    @Bind(R.id.view_toolbar_center_back)
    ImageView view_toolbar_center_back;
    @Bind(R.id.searchbrand_letterindicator)
    LetterIndicatorView searchbrand_letterindicator;
    @Bind(R.id.searchbrand_rv)
    RecyclerView searchbrand_rv;
    SearchBrandAdapter adapter=null;
    LinearLayoutManager manager=null;
    @Bind(R.id.searchbrand_child)
    RecyclerView searchbrand_child;
    SearchBrandChildAdapter childAdapter=null;

    //首字母序列
    ArrayList<String> letterIndicator=null;
    //listview对应首字母位置
    HashMap<String, Integer> sections=null;
    //首字母对应对象集合
    HashMap<String, ArrayList<SearchBrandModel>> models=null;
    //列表加载对象
    ArrayList<Object> tempModels=null;

    @Override
    public int initContentView() {
        return R.layout.activity_searchbrand;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sections=new HashMap<>();
        models=new HashMap<>();
        tempModels=new ArrayList<>();

        initViews();

        initData();
    }

    private void initData() {
        //未加工对象
        ArrayList<SearchBrandModel> results=new ArrayList<>();
        String[] names={"福斯", "红线", "昆仑润滑油", "福特润滑油", "康普顿", "AMSOIL汽油机油", "MOTUL汽油机油", "加德士汽油机油", "美孚汽油机油", "四洲汽油机油"};
        for (int i=0;i<names.length;i++) {
            SearchBrandModel model1=new SearchBrandModel();
            model1.setTitle(names[i]);
            results.add(model1);
        }
        letterIndicatorCompare(results);
    }

    private void initViews() {
        view_toolbar_center_image.setImageResource(R.mipmap.ic_brand_logo);
        view_toolbar_center_title.setText("");
        view_toolbar_center_back.setVisibility(View.VISIBLE);
        searchbrand_letterindicator.setOnLetterChangedListener(new LetterIndicatorView.OnLetterChangedListener() {
            @Override
            public void LetterChanged(int position) {
                manager.scrollToPositionWithOffset(sections.get(letterIndicator.get(position)), 0);
            }
        });
        searchbrand_rv.setHasFixedSize(true);
        manager=new LinearLayoutManager(this);
        searchbrand_rv.setLayoutManager(manager);
        adapter=new SearchBrandAdapter(this, tempModels, new SearchBrandAdapter.OnOperationListener() {
            @Override
            public void positionChoice(int position) {
                if (searchbrand_child.getVisibility()==View.VISIBLE) {
                    searchbrand_child.setVisibility(View.GONE);
                }
                else {
                    searchbrand_child.setVisibility(View.VISIBLE);
                    ArrayList<String> childModels=new ArrayList<>();
                    childModels.add("123414124124");
                    childModels.add("123414124124");
                    childModels.add("123414124124");
                    childModels.add("123414124124");
                    childModels.add("123414124124");
                    childAdapter=new SearchBrandChildAdapter(SearchBrandActivity.this, childModels);
                    searchbrand_child.setAdapter(childAdapter);
                }
            }
        });
        searchbrand_rv.setAdapter(adapter);

        searchbrand_child.setHasFixedSize(true);
        searchbrand_child.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick(R.id.view_toolbar_center_back)
    public void onClick(View view) {
        finish();
    }

    /**
     * 首字母排序
     */
    private void letterIndicatorCompare(ArrayList<SearchBrandModel> results) {
        Observable.just(results).map(new Func1<ArrayList<SearchBrandModel>, HashMap<String, ArrayList<SearchBrandModel>>>() {
            @Override
            public HashMap<String, ArrayList<SearchBrandModel>> call(ArrayList<SearchBrandModel> searchBrandModels) {
                HashMap<String, ArrayList<SearchBrandModel>> map = new HashMap<>();
                for (int i = 0; i < searchBrandModels.size(); i++) {
                    String firstLetter = CommonUtils.getFirstPinyin(searchBrandModels.get(i).getTitle());
                    if (map.containsKey(firstLetter)) {
                        ArrayList<SearchBrandModel> arrayList = map.get(firstLetter);
                        arrayList.add(searchBrandModels.get(i));
                    } else {
                        ArrayList<SearchBrandModel> arrayList = new ArrayList<>();
                        arrayList.add(searchBrandModels.get(i));
                        map.put(firstLetter, arrayList);
                    }
                }
                return map;
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<HashMap<String, ArrayList<SearchBrandModel>>>() {
            @Override
            public void call(HashMap<String, ArrayList<SearchBrandModel>> stringArrayListHashMap) {
                //排序key
                ArrayList<String> keys=new ArrayList<String>();
                Iterator<String> iterator=stringArrayListHashMap.keySet().iterator();
                while (iterator.hasNext()) {
                    String key= iterator.next();
                    keys.add(key);
                }
                Collections.sort(keys, new MySort());
                letterIndicator=keys;
                searchbrand_letterindicator.setLetters(letterIndicator);
                models=stringArrayListHashMap;
                refreshList();
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

    public void refreshList() {
        tempModels.clear();
        sections.clear();

        ArrayList<Object> values=new ArrayList<>();
        for (int i=0;i<letterIndicator.size();i++) {
            sections.put(letterIndicator.get(i), values.size());
            //添加大写字母序号
            values.add(letterIndicator.get(i));
            //得到大写字母中所含有的model
            ArrayList<SearchBrandModel> searchBrandModels=models.get(letterIndicator.get(i));
            for (int j=0;j<searchBrandModels.size();j++) {
                SearchBrandModel searchBrandModel=searchBrandModels.get(j);
                values.add(searchBrandModel);
            }
        }
        tempModels.addAll(values);

        adapter.notifyDataSetChanged();
    }
}
