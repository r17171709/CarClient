package com.renyu.carclient.wxapi;

import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.JsonParse;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import java.util.HashMap;

public class PayActivity extends Activity {
	
	PayReq req;
	IWXAPI api;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		req = new PayReq();
		api=WXAPIFactory.createWXAPI(this, ParamUtils.WEIXIN_SHAREID, false);
		
		genPayReq();
		sendPayReq();
		
		finish();
	}
	
	private void genPayReq() {
		HashMap<String, String> value=JsonParse.getWeixinPayData(getIntent().getExtras().getString("payInfo"));
		req.appId = ParamUtils.WEIXIN_SHAREID;
		req.partnerId = value.get("partnerid");
		req.prepayId = value.get("prepayid");
		req.packageValue = value.get("package");
		req.nonceStr = value.get("noncestr");
		req.timeStamp = value.get("timestamp");
		req.sign = value.get("sign");
	}
	
	private void sendPayReq() {
		api.registerApp(ParamUtils.WEIXIN_SHAREID);
		api.sendReq(req);
	}
}
