package com.renyu.carclient.activity.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.renyu.carclient.R;
import com.renyu.carclient.adapter.SearchCarTypeDetailAdapter;
import com.renyu.carclient.base.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by renyu on 15/12/7.
 */
public class SearchCarTypeDetailActivity extends BaseActivity {

    @Bind(R.id.searchcartypedetail_rv)
    RecyclerView searchcartypedetail_rv;
    SearchCarTypeDetailAdapter adapter=null;

    ArrayList<String> models=null;

    @Override
    public int initContentView() {
        return R.layout.activity_searchcartypedetail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        models=new ArrayList<>();
        models.add("1");
        models.add("1");
        models.add("1");
        models.add("1");
        models.add("1");
        models.add("1");
        models.add("1");
        models.add("1");

        initViews();
    }

    private void initViews() {
        searchcartypedetail_rv.setHasFixedSize(true);
        searchcartypedetail_rv.setLayoutManager(new LinearLayoutManager(this));
        adapter=new SearchCarTypeDetailAdapter(this, models);
        searchcartypedetail_rv.setAdapter(adapter);

    }
}
