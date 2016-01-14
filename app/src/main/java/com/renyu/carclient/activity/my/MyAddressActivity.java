package com.renyu.carclient.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.adapter.MyAddressAdapter;
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.commons.ACache;
import com.renyu.carclient.commons.OKHttpHelper;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.AddressModel;
import com.renyu.carclient.model.JsonParse;
import com.renyu.carclient.model.UserModel;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by renyu on 15/12/14.
 */
public class MyAddressActivity extends BaseActivity {

    @Bind(R.id.view_toolbar_center_back)
    ImageView view_toolbar_center_back;
    @Bind(R.id.view_toolbar_center_title)
    TextView view_toolbar_center_title;
    @Bind(R.id.my_address_rv)
    RecyclerView my_address_rv;
    MyAddressAdapter adapter=null;

    ArrayList<AddressModel> models;

    UserModel userModel=null;

    @Override
    public int initContentView() {
        return R.layout.activity_myaddress;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userModel= ACache.get(this).getAsObject("user")!=null?(UserModel) ACache.get(this).getAsObject("user"):null;

        models=new ArrayList<>();

        initViews();
    }

    private void initViews() {
        view_toolbar_center_back.setVisibility(View.VISIBLE);
        view_toolbar_center_title.setText("管理收货地址");
        my_address_rv.setHasFixedSize(true);
        my_address_rv.setLayoutManager(new LinearLayoutManager(this));
        adapter=new MyAddressAdapter(this, models, new MyAddressAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                Intent intent=new Intent(MyAddressActivity.this, MyAddressAddActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("address", models.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        my_address_rv.setAdapter(adapter);
    }

    @OnClick({R.id.my_address_add, R.id.view_toolbar_center_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_address_add:
                Intent intent=new Intent(MyAddressActivity.this, MyAddressAddActivity.class);
                startActivity(intent);
                break;
            case R.id.view_toolbar_center_back:
                finish();
                break;
        }
    }

    private void getAddressList() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.address.list", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("user_id", ""+userModel.getUser_id());
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                models.clear();
                ArrayList<AddressModel> temp= JsonParse.getAddressModels(string);
                if (temp!=null) {
                    models.addAll(temp);
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

    @Override
    protected void onResume() {
        super.onResume();

        getAddressList();
    }
}
