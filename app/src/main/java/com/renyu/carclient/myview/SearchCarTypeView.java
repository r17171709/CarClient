package com.renyu.carclient.myview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.renyu.carclient.R;
import com.renyu.carclient.adapter.SearchCarTypeAdapter;
import com.renyu.carclient.commons.CommonUtils;
import com.renyu.carclient.commons.OKHttpHelper;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.JsonParse;
import com.renyu.carclient.model.SearchCarTypeModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by renyu on 16/1/14.
 */
public class SearchCarTypeView extends LinearLayout {

    OKHttpHelper httpHelper=null;

    OnFinalChoiceListener listener;

    public interface OnFinalChoiceListener {
        void onChoicePosition(String brand);
    }

    public void setOnFinalChoiceListener(OnFinalChoiceListener listener) {
        this.listener=listener;
    }

    Context context;
    RecyclerView goods_search_cartype;
    SearchCarTypeAdapter adapter=null;

    //首字母序列
    ArrayList<String> letterIndicator=null;
    //listview对应首字母位置
    HashMap<String, Integer> sections=null;
    //首字母对应对象集合
    HashMap<String, ArrayList<SearchCarTypeModel>> models=null;
    //列表加载对象
    ArrayList<Object> tempModels=null;

    public SearchCarTypeView(Context context) {
        this(context, null);
    }

    public SearchCarTypeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchCarTypeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.context=context;
        httpHelper=new OKHttpHelper();
        tempModels=new ArrayList<>();
        models=new HashMap<>();
        sections=new HashMap<>();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        goods_search_cartype= (RecyclerView) findViewById(R.id.goods_search_cartype);
        goods_search_cartype.setHasFixedSize(true);
        goods_search_cartype.setLayoutManager(new LinearLayoutManager(context));
        adapter=new SearchCarTypeAdapter(context, tempModels, new SearchCarTypeAdapter.OnOperationListener() {
            @Override
            public void positionChoice(String brand) {
                listener.onChoicePosition(brand);
            }
        });
        goods_search_cartype.setAdapter(adapter);

        getCarAllType();
    }

    private void getCarAllType() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.modelsmatch.api.getBrands", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                ArrayList<SearchCarTypeModel> tempModels= JsonParse.getCarType(string);
                if (tempModels!=null) {
                    letterIndicatorCompare(tempModels);
                }
            }

            @Override
            public void onError() {

            }
        });
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
            ArrayList<SearchCarTypeModel> SearchCarTypeModels=models.get(letterIndicator.get(i));
            for (int j=0;j<SearchCarTypeModels.size();j++) {
                SearchCarTypeModel SearchCarTypeModel=SearchCarTypeModels.get(j);
                values.add(SearchCarTypeModel);
            }
        }
        tempModels.addAll(values);

        adapter.notifyDataSetChanged();
    }

}
