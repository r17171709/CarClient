package com.renyu.carclient.qqapi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.commons.CommonUtils;
import com.renyu.carclient.commons.ParamUtils;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class QQActivity extends BaseActivity {

    public Tencent mTencent;

    int mExtarFlag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        mTencent=Tencent.createInstance(ParamUtils.QQ_SHAREID, getApplicationContext());
        if(!mTencent.isSupportSSOLogin(this)) {
            Toast.makeText(this, "请您先安装手机版QQ再执行分享操作", Toast.LENGTH_SHORT).show();
            finish();
            return ;
        }

        share(getIntent().getExtras().getString("title")
                , getIntent().getExtras().getString("url")
                , getIntent().getExtras().getString("text")
                , getIntent().getExtras().getString("imageUrl")
                , getIntent().getExtras().getBoolean("isQQKJ"));
    }

    @Override
    public int initContentView() {
        return 0;
    }

    public void share(String title, String url, String text, String imageUrl, boolean isQQKJ) {
        if (!isQQKJ) {
            mExtarFlag &= (0xFFFFFFFF - QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        }
        else {
            mExtarFlag |= QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN;
        }
        final Bundle params = new Bundle();
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, text);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, CommonUtils.getApplicationName(this));
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, mExtarFlag);
        ThreadManager.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                mTencent.shareToQQ(QQActivity.this, params, qqShareListener);
            }
        });
    }

    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
            System.out.println("onCancel");
        }
        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            System.out.println("onComplete: " + response.toString());
        }
        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
            System.out.println("onError: " + e.errorMessage);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode,resultCode,data,qqShareListener);
        if (requestCode == Constants.REQUEST_QQ_SHARE) {
            if (resultCode == Constants.ACTIVITY_OK) {
                Tencent.handleResultData(data, qqShareListener);
            }
        }
    }
}