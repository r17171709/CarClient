package com.renyu.carclient.activity.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.commons.CommonUtils;
import com.renyu.carclient.commons.OKHttpHelper;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.JsonParse;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by renyu on 15/12/14.
 */
public class JoinActivity extends BaseActivity {

    @Bind(R.id.view_toolbar_center_title)
    TextView view_toolbar_center_title;
    @Bind(R.id.view_toolbar_center_back)
    ImageView view_toolbar_center_back;
    @Bind(R.id.join_name)
    EditText join_name;
    @Bind(R.id.join_area)
    TextView join_area;
    @Bind(R.id.join_contact)
    EditText join_contact;
    @Bind(R.id.join_address)
    EditText join_address;
    @Bind(R.id.join_tel)
    EditText join_tel;
    @Bind(R.id.join_check)
    CheckBox join_check;
    @Bind(R.id.join_commit)
    TextView join_commit;

    String cityIds="";
    String cityNames="";

    @Override
    public int initContentView() {
        return R.layout.activity_join;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
    }

    private void initViews() {
        view_toolbar_center_title.setText("立即申请");
        view_toolbar_center_back.setVisibility(View.VISIBLE);
        join_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changeColor();
            }
        });
        join_contact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changeColor();
            }
        });
        join_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changeColor();
            }
        });
        join_tel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changeColor();
            }
        });
    }

    @OnClick({R.id.join_commit, R.id.join_area, R.id.view_toolbar_center_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.join_commit:
                if (join_check.isChecked()) {
                    join();
                }
                break;
            case R.id.join_area:
                Intent intent=new Intent(JoinActivity.this, AreaActivity.class);
                startActivityForResult(intent, ParamUtils.RESULT_AREA);
                break;
            case R.id.view_toolbar_center_back:
                finish();
                break;
        }
    }

    private void join() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.apply.join", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("repair_name", join_name.getText().toString());
        params.put("contact_person", join_contact.getText().toString());
        params.put("contact_tel", join_tel.getText().toString());
        params.put("area", cityIds);
        params.put("addrs", join_address.getText().toString());
        httpHelper.commonPostRequest(ParamUtils.api, params, new OKHttpHelper.StartListener() {
            @Override
            public void onStart() {
                showDialog("提示", "正在注册");
            }
        }, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                dismissDialog();
                if (JsonParse.getResultValue(string)!=null) {
                    showToast(JsonParse.getResultValue(string));
                    if (JsonParse.getResultInt(string)==0) {
                        join_name.setText("");
                        join_contact.setText("");
                        join_name.setText("");
                        join_address.setText("");
                        join_area.setText("");
                        join_tel.setText("");
                        cityIds="";
                        cityNames="";
                    }
                }
                else {
                    showToast("未知错误");
                }
            }

            @Override
            public void onError() {
                dismissDialog();
                showToast(getResources().getString(R.string.network_error));
            }
        });
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
            join_area.setText(cityNames);
            changeColor();
        }
    }

    @OnCheckedChanged(R.id.join_check)
    public void OnCheckedChanged(boolean flag) {
        changeColor();
    }

    private void changeColor() {
        if (!join_name.getText().toString().equals("") && !cityIds.equals("") && !join_contact.getText().toString().equals("") && !join_address.getText().toString().equals("") && !join_tel.getText().toString().equals("") && join_check.isChecked()) {
            join_commit.setBackgroundColor(Color.RED);
        }
        else {
            join_commit.setBackgroundColor(Color.parseColor("#acacac"));
        }
    }
}
