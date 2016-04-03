package com.renyu.carclient.activity.my;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.commons.ACache;
import com.renyu.carclient.commons.OKHttpHelper;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.UserModel;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by renyu on 16/3/29.
 */
public class SendMessageActivity extends BaseActivity {

    @Bind(R.id.view_toolbar_center_back)
    ImageView view_toolbar_center_back;
    @Bind(R.id.view_toolbar_center_title)
    TextView view_toolbar_center_title;
    @Bind(R.id.sendmessage_title)
    EditText sendmessage_title;
    @Bind(R.id.sendmessage_content)
    EditText sendmessage_content;
    @Bind(R.id.sendmessage_group)
    RadioGroup sendmessage_group;

    UserModel userModel=null;

    String tag="";

    @Override
    public int initContentView() {
        return R.layout.activity_sendmessage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
    }

    private void initViews() {
        view_toolbar_center_back.setVisibility(View.VISIBLE);
        view_toolbar_center_title.setText("站内信");
        userModel= ACache.get(this).getAsObject("user")!=null?(UserModel) ACache.get(this).getAsObject("user"):null;
        sendmessage_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton= (RadioButton) SendMessageActivity.this.findViewById(group.getCheckedRadioButtonId());
                tag=radioButton.getText().toString();
            }
        });
    }

    @OnClick({R.id.sendmessage_commit, R.id.view_toolbar_center_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sendmessage_commit:
                if (tag.equals("")) {
                    showToast("请选择收件人");
                    return;
                }
                else if (sendmessage_title.getText().toString().equals("")) {
                    showToast("请填写标题");
                    return;
                }
                else if (sendmessage_content.getText().toString().equals("")) {
                    showToast("请填写内容");
                    return;
                }
                sendMessage();
                break;
            case R.id.view_toolbar_center_back:
                finish();
                break;
        }
    }

    private void sendMessage() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.message.notice", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("user_id", ""+userModel.getUser_id());
        params.put("type", tag.equals("我的服务商")?"6":"5");
        params.put("title", sendmessage_title.getText().toString());
        params.put("content", sendmessage_content.getText().toString());
        httpHelper.commonPostRequest(ParamUtils.api, params, new OKHttpHelper.StartListener() {
            @Override
            public void onStart() {
                showDialog("提示", "正在发送");
            }
        }, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                dismissDialog();
                showToast("发送成功");
                finish();
            }

            @Override
            public void onError() {
                dismissDialog();
                showToast("发送失败");
            }
        });
    }
}
