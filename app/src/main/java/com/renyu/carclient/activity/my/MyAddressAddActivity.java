package com.renyu.carclient.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.activity.login.AreaActivity;
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.commons.ACache;
import com.renyu.carclient.commons.CommonUtils;
import com.renyu.carclient.commons.OKHttpHelper;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.AddressModel;
import com.renyu.carclient.model.JsonParse;
import com.renyu.carclient.model.UserModel;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by renyu on 15/12/14.
 */
public class MyAddressAddActivity extends BaseActivity {

    @Bind(R.id.view_toolbar_center_back)
    ImageView view_toolbar_center_back;
    @Bind(R.id.view_toolbar_center_title)
    TextView view_toolbar_center_title;
    @Bind(R.id.addcustomer_area)
    TextView addcustomer_area;
    @Bind(R.id.addcustomer_address)
    EditText addcustomer_address;
    @Bind(R.id.addcustomer_phonenum)
    EditText addcustomer_phonenum;
    @Bind(R.id.addcustomer_contact)
    EditText addcustomer_contact;
    @Bind(R.id.addcustomer_zip)
    EditText addcustomer_zip;
    @Bind(R.id.addcustomer_default)
    CheckBox addcustomer_default;

    String cityIds="";
    String cityNames="";

    UserModel userModel=null;
    AddressModel addressModel=null;

    @Override
    public int initContentView() {
        return R.layout.activity_myaddressadd;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addressModel= getIntent().getExtras()==null?null: (AddressModel) getIntent().getExtras().getSerializable("address");
        userModel= ACache.get(this).getAsObject("user")!=null?(UserModel) ACache.get(this).getAsObject("user"):null;

        initViews();
    }

    private void initViews() {

        if (addressModel!=null) {
            view_toolbar_center_back.setVisibility(View.VISIBLE);
            view_toolbar_center_title.setText("编辑收货地址");
            String address="";
            for (int i=0;i<addressModel.getArea().split("/").length;i++) {
                address+= CommonUtils.getCityInfo(addressModel.getArea().split("/")[i]);
                if (i!=addressModel.getArea().split("/").length-1) {
                    cityIds+=addressModel.getArea().split("/")[i]+",";
                }
                else {
                    cityIds+=addressModel.getArea().split("/")[i];
                }
            }
            cityNames=address;
            addcustomer_area.setText(address);
            addcustomer_address.setText(addressModel.getAddr());
            addcustomer_phonenum.setText(addressModel.getMobile());
            addcustomer_contact.setText(addressModel.getName());
            addcustomer_zip.setText(addressModel.getZip());
            addcustomer_default.setChecked(addressModel.getDef_addr()==1?true:false);
        }
        else {
            view_toolbar_center_back.setVisibility(View.VISIBLE);
            view_toolbar_center_title.setText("新增收货地址");
        }
    }

    @OnClick({R.id.addcustomer_area, R.id.addcustomer_add, R.id.view_toolbar_center_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addcustomer_area:
                Intent intent=new Intent(MyAddressAddActivity.this, AreaActivity.class);
                startActivityForResult(intent, ParamUtils.RESULT_AREA);
                break;
            case R.id.addcustomer_add:
                addAddress();
                break;
            case R.id.view_toolbar_center_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ParamUtils.RESULT_AREA && resultCode == RESULT_OK) {
            cityIds = data.getExtras().getString("value");
            cityNames = "";
            String[] values = data.getExtras().getString("value").split(",");
            for (int i = 0; i < values.length; i++) {
                cityNames += CommonUtils.getCityInfo(values[i]);
            }
            addcustomer_area.setText(cityNames);
        }
    }

    private void addAddress() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.address.addr", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        if (addressModel!=null) {
            params= ParamUtils.getSignParams("app.user.address.update", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
            params.put("addr_id", ""+addressModel.getAddr_id());
        }
        params.put("user_id", ""+userModel.getUser_id());
        params.put("area", cityIds);
        params.put("addr", addcustomer_address.getText().toString());
        params.put("zip", addcustomer_zip.getText().toString());
        params.put("name", addcustomer_contact.getText().toString());
        params.put("mobile", addcustomer_phonenum.getText().toString());
        params.put("def_addr", addcustomer_default.isChecked()?"1":"0");
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                if (JsonParse.getResultValue(string)!=null) {
                    showToast(JsonParse.getResultValue(string));
                    if (JsonParse.getResultInt(string)==0) {
                        finish();
                    }
                }
                else {
                    showToast("未知错误");
                }
            }

            @Override
            public void onError() {

            }
        });
    }
}
