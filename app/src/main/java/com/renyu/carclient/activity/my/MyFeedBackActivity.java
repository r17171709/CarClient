package com.renyu.carclient.activity.my;

import android.os.Bundle;
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
 * Created by renyu on 15/12/15.
 */
public class MyFeedBackActivity extends BaseActivity {

    @Bind(R.id.myfeedback_edit)
    EditText myfeedback_edit;

    UserModel userModel=null;

    @Override
    public int initContentView() {
        return R.layout.activity_myfeedback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userModel= ACache.get(this).getAsObject("user")!=null?(UserModel) ACache.get(this).getAsObject("user"):null;
    }

    @OnClick({R.id.myfeedback_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myfeedback_commit:
                sendFeedBack();
                break;
        }
    }

    private void sendFeedBack() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.xiulichang.aftersaleApply", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("contents", myfeedback_edit.getText().toString());
        params.put("user_id", "" + userModel.getUser_id());
        httpHelper.commonPostRequest(ParamUtils.api, params, new OKHttpHelper.StartListener() {
            @Override
            public void onStart() {
                showDialog("提示", "正在提交");
            }
        }, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                dismissDialog();
                if (JsonParse.getResultValue(string)!=null) {
                    showToast(JsonParse.getResultValue(string));
                    if (JsonParse.getResultInt(string) == 0) {
                        myfeedback_edit.setText("");
                    }
                }
            }

            @Override
            public void onError() {
                dismissDialog();
            }
        });
    }
}
