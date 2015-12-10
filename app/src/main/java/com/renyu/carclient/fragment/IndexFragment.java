package com.renyu.carclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.renyu.carclient.R;
import com.renyu.carclient.base.BaseFragment;
import com.zbar.lib.CaptureActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by renyu on 15/10/18.
 */
public class IndexFragment extends BaseFragment {

    @Bind(R.id.main_wv)
    WebView main_wv;

    @Override
    public int initContentView() {
        return R.layout.fragment_main;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        main_wv.setSaveEnabled(true);
        main_wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        main_wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);
                return false;
            }
        });
        main_wv.setWebChromeClient(new WebChromeClient());
        WebSettings settings=main_wv.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.setBlockNetworkLoads(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDatabaseEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);
        main_wv.loadUrl("http://www.baidu.com");
    }

    @OnClick({R.id.toolbar_main_right_button})
    public void jumpClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_main_right_button:
                Intent intent1=new Intent(getActivity(), CaptureActivity.class);
                startActivity(intent1);
                break;
        }
    }

    public boolean checkBack() {
        boolean flag=main_wv.canGoBack();
        if (flag) {
            main_wv.goBack();
        }
        return flag;
    }
}
