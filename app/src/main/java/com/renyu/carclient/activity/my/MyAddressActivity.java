package com.renyu.carclient.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.renyu.carclient.R;
import com.renyu.carclient.adapter.MyAddressAdapter;
import com.renyu.carclient.base.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by renyu on 15/12/14.
 */
public class MyAddressActivity extends BaseActivity {

    @Bind(R.id.my_address_rv)
    RecyclerView my_address_rv;
    MyAddressAdapter adapter=null;

    ArrayList<String> models;

    @Override
    public int initContentView() {
        return R.layout.activity_myaddress;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        models=new ArrayList<>();
        models.add("");
        models.add("");
        models.add("");
        models.add("");
        models.add("");
        models.add("");
        models.add("");

        initViews();
    }

    private void initViews() {
        my_address_rv.setHasFixedSize(true);
        my_address_rv.setLayoutManager(new LinearLayoutManager(this));
        adapter=new MyAddressAdapter(this, models);
        my_address_rv.setAdapter(adapter);
    }

    @OnClick({R.id.my_address_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_address_add:
                Intent intent=new Intent(MyAddressActivity.this, MyAddressAddActivity.class);
                startActivity(intent);
                break;
        }
    }
}
