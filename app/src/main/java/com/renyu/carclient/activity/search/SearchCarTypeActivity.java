package com.renyu.carclient.activity.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.renyu.carclient.R;
import com.renyu.carclient.adapter.SearchCarTypeAdapter;
import com.renyu.carclient.adapter.SearchCarTypeChildAdapter;
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.commons.CommonUtils;
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
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by renyu on 15/12/1.
 */
public class SearchCarTypeActivity extends BaseActivity {

    @Bind(R.id.searchcartype_letterindicator)
    LetterIndicatorView searchcartype_letterindicator;
    SearchCarTypeAdapter adapter=null;
    @Bind(R.id.searchcartype_rv)
    RecyclerView searchcartype_rv;
    LinearLayoutManager manager=null;
    @Bind(R.id.searchcartype_child)
    RecyclerView searchcartype_child;
    SearchCarTypeChildAdapter childAdapter=null;

    //首字母序列
    ArrayList<String> letterIndicator=null;
    //listview对应首字母位置
    HashMap<String, Integer> sections=null;
    //首字母对应对象集合
    HashMap<String, ArrayList<SearchCarTypeModel>> models=null;
    //列表加载对象
    ArrayList<Object> tempModels=null;
    ArrayList<SearchCarTypeChildModel> childModels=null;

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
        childModels=new ArrayList<>();

        initViews();

        initData();
    }

    private void initViews() {
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
            public void positionChoice(int position) {
                if (searchcartype_child.getVisibility()== View.VISIBLE) {
                    searchcartype_child.setVisibility(View.GONE);
                }
                else {
                    searchcartype_child.setVisibility(View.VISIBLE);
                    openChild(position);
                }
            }
        });
        searchcartype_rv.setAdapter(adapter);

        searchcartype_child.setHasFixedSize(true);
        searchcartype_child.setLayoutManager(new LinearLayoutManager(this));
        childAdapter=new SearchCarTypeChildAdapter(this, childModels, new SearchCarTypeChildAdapter.OnMenuChoiceListener() {
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

    private void initData() {
        //未加工对象
        ArrayList<SearchCarTypeModel> results=new ArrayList<>();
        String[] names={"福斯", "红线", "昆仑润滑油", "福特润滑油", "康普顿", "AMSOIL汽油机油", "MOTUL汽油机油", "加德士汽油机油", "美孚汽油机油", "四洲汽油机油"};
        for (int i=0;i<names.length;i++) {
            SearchCarTypeModel model1=new SearchCarTypeModel();
            model1.setTitle(names[i]);
            results.add(model1);
        }
        letterIndicatorCompare(results);
    }

    /**
     * 首字母排序
     */
    private void letterIndicatorCompare(ArrayList<SearchCarTypeModel> results) {
        Observable.just(results).map(new Func1<ArrayList<SearchCarTypeModel>, HashMap<String, ArrayList<SearchCarTypeModel>>>() {
            @Override
            public HashMap<String, ArrayList<SearchCarTypeModel>> call(ArrayList<SearchCarTypeModel> SearchCarTypeModels) {
                HashMap<String, ArrayList<SearchCarTypeModel>> map = new HashMap<>();
                for (int i = 0; i < SearchCarTypeModels.size(); i++) {
                    String firstLetter = CommonUtils.getFirstPinyin(SearchCarTypeModels.get(i).getTitle());
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

        SearchCarTypeHeadModel headModel=new SearchCarTypeHeadModel();
        ArrayList<SearchCarTypeModel> tempHead=new ArrayList<>();
        for (int i=0;i<30;i++) {
            SearchCarTypeModel temp=new SearchCarTypeModel();
            temp.setTitle("agerge"+i);
            tempHead.add(temp);
        }
        headModel.setLists(tempHead);
        tempModels.add(headModel);
        ArrayList<Object> values=new ArrayList<>();
        for (int i=0;i<letterIndicator.size();i++) {
            sections.put(letterIndicator.get(i), values.size());
            //添加大写字母序号
            values.add(letterIndicator.get(i));
            //得到大写字母中所含有的model
            ArrayList<SearchCarTypeModel> SearchCarTypeModels=models.get(letterIndicator.get(i));
            for (int j=0;j<SearchCarTypeModels.size();j++) {
                SearchCarTypeModel SearchCarTypeModel=SearchCarTypeModels.get(j);
                values.add(SearchCarTypeModel);
            }
        }
        tempModels.addAll(values);

        adapter.notifyDataSetChanged();
    }

    private void openChild(int position) {
        ArrayList<SearchCarTypeChildModel> tempModels=new ArrayList<>();
        for (int i=0;i<10;i++) {
            SearchCarTypeChildModel parentModel=new SearchCarTypeChildModel();
            parentModel.setId(i);
            parentModel.setImageUrl("");
            parentModel.setIsOpen(true);
            parentModel.setParentId(-1);
            parentModel.setText("acascas"+i);
            ArrayList<SearchCarTypeChildModel> temp=new ArrayList<>();
            for (int j=0;j<5;j++) {
                SearchCarTypeChildModel childModel=new SearchCarTypeChildModel();
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
        searchcartype_child.setAdapter(childAdapter);
    }
}
