package com.renyu.carclient.activity.login;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.renyu.carclient.R;
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.commons.ACache;
import com.renyu.carclient.commons.OKHttpHelper;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.JsonParse;
import com.renyu.carclient.model.UserModel;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by renyu on 15/10/20.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.login_name)
    EditText login_name;
    @Bind(R.id.login_password)
    EditText login_password;

    @Override
    public int initContentView() {
        return R.layout.activity_login;
    }

    private void login() {
        HashMap<String, String> params=ParamUtils.getSignParams("app.user.account", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("user_name", login_name.getText().toString());
        params.put("password", login_password.getText().toString());
        httpHelper.commonPostRequest(ParamUtils.api, params, new OKHttpHelper.StartListener() {
            @Override
            public void onStart() {
                showDialog("提示", "正在登陆");
            }
        }, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                dismissDialog();
                if (JsonParse.getResultInt(string)==1) {
                    showToast(JsonParse.getErrorValue(string));
                }
                else {
                    final UserModel model=JsonParse.getLoginModel(string);
                    if (model==null) {
                        showToast("未知错误");
                    }
                    else {
                        ACache.get(LoginActivity.this).put("user", (UserModel) model);
                        setResult(RESULT_OK, new Intent());
                        finish();
                    }
                }
            }

            @Override
            public void onError() {
                dismissDialog();
                showToast(getResources().getString(R.string.network_error));
            }
        });
    }

    @OnClick({R.id.login_login, R.id.login_join})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_login:
                login();
                break;
            case R.id.login_join:
                Intent intent=new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
                break;
        }
    }
}
