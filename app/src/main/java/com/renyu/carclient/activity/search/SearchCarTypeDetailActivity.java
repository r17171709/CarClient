package com.renyu.carclient.activity.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.adapter.SearchCarTypeDetailAdapter;
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.commons.OKHttpHelper;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.JsonParse;
import com.renyu.carclient.model.SearchCarDetalModel;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by renyu on 15/12/7.
 */
public class SearchCarTypeDetailActivity extends BaseActivity {

    @Bind(R.id.view_toolbar_center_title)
    TextView view_toolbar_center_title;
    @Bind(R.id.view_toolbar_center_back)
    ImageView view_toolbar_center_back;
    @Bind(R.id.searchcartypedetail_rv)
    RecyclerView searchcartypedetail_rv;
    SearchCarTypeDetailAdapter adapter=null;

    ArrayList<SearchCarDetalModel> models=null;

    @Override
    public int initContentView() {
        return R.layout.activity_searchcartypedetail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        models=new ArrayList<>();

        initViews();
        getDetail();
    }

    private void initViews() {
        view_toolbar_center_title.setText(getIntent().getExtras().getString("models"));
        view_toolbar_center_back.setVisibility(View.VISIBLE);
        searchcartypedetail_rv.setHasFixedSize(true);
        searchcartypedetail_rv.setLayoutManager(new LinearLayoutManager(this));
        adapter=new SearchCarTypeDetailAdapter(this, models);
        searchcartypedetail_rv.setAdapter(adapter);
    }

    private void getDetail() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.modelsmatch.api.getModels", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("brand", getIntent().getExtras().getString("brand"));
        params.put("models", getIntent().getExtras().getString("models"));
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                ArrayList<SearchCarDetalModel> temps=JsonParse.getCarDetails(string);
                if (temps==null) {
                    showToast("未知错误");
                }
                else {
                    models.addAll(temps);
                    adapter.notifyDataSetChanged();
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
}
