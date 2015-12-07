package com.zbar.lib;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.renyu.carclient.R;
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.myview.NoScrollGridView;

import butterknife.Bind;
import butterknife.OnClick;

public class CaptureActivity extends BaseActivity implements CaptureCallBackListener {

	@Bind(R.id.view_toolbar_nor_title)
	TextView view_toolbar_nor_title;

	CaptureFragment qrcode=null;
	CaptureFragment barcode=null;

	@Override
	public int initContentView() {
		return R.layout.activity_capture;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initViews();
	}

	private void initViews() {
		view_toolbar_nor_title.setText("二维码|VIN码扫描");
		switchFragment("one");
	}

	@Override
	public void onCaptureSuccess(String result) {
		Intent resultIntent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("result", result);
		resultIntent.putExtras(bundle);
		setResult(RESULT_OK, resultIntent);
		finish();
	}

	@Override
	public void onCaptureFail() {
		Toast.makeText(this, "请允许摄像头使用权限", Toast.LENGTH_LONG).show();
		startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
		finish();
	}

	@Override
	public void onSwitchListener() {

	}

	@OnClick({R.id.capture_qrcode, R.id.capture_barcode, R.id.view_toolbar_nor_back})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.capture_qrcode:
				switchFragment("one");
				break;
			case R.id.capture_barcode:
				switchFragment("two");
				break;
			case R.id.view_toolbar_nor_back:
				finish();
		}
	}

	private void switchFragment(String title) {
		FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
		if (title.equals("one")) {
			if (barcode!=null) {
				transaction.hide(barcode);
			}
			if (qrcode==null) {
				qrcode=CaptureFragment.getInstance(CaptureFragment.QRCODE);
				transaction.add(R.id.capture_fl, qrcode, "one");
			}
			else {
				transaction.show(qrcode);
			}
		}
		else if (title.equals("two")) {
			if (qrcode!=null) {
				transaction.hide(qrcode);
			}
			if (barcode==null) {
				barcode=CaptureFragment.getInstance(CaptureFragment.BARCODE);
				transaction.add(R.id.capture_fl, barcode, "two");
			}
			else {
				transaction.show(barcode);
			}
		}
		transaction.commit();
	}
}