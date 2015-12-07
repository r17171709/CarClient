package com.renyu.carclient.wxapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.renyu.carclient.commons.ParamUtils;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by renyu on 15/10/19.
 */
public class AppRegister extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        final IWXAPI api= WXAPIFactory.createWXAPI(context, ParamUtils.WEIXIN_SHAREID);

        // 将该app注册到微信
        api.registerApp(ParamUtils.WEIXIN_SHAREID);
    }

}
