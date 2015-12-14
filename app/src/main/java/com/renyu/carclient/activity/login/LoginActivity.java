package com.renyu.carclient.activity.login;

import android.os.Bundle;
import android.util.Log;

import com.renyu.carclient.R;
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.commons.CommonUtils;
import com.renyu.carclient.commons.OKHttpHelper;
import com.renyu.carclient.commons.ParamUtils;

import java.util.HashMap;

/**
 * Created by renyu on 15/10/20.
 */
public class LoginActivity extends BaseActivity {
    @Override
    public int initContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        login();
    }

    private void login() {
        HashMap<String, String> params=ParamUtils.getSignParams("user.login", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("user_name", "admin");
        params.put("password", "abc123");
        httpHelper.commonPostRequest(ParamUtils.api, params, new OKHttpHelper.StartListener() {
            @Override
            public void onStart() {

            }
        }, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                Log.d("LoginActivity", string);
            }

            @Override
            public void onError() {
                Log.d("LoginActivity", "fail");
            }
        });
    }
}
