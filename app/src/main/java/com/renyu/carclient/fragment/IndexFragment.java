package com.renyu.carclient.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.renyu.carclient.R;
import com.renyu.carclient.activity.search.GoodsDetailActivity;
import com.renyu.carclient.activity.search.GoodsListActivity;
import com.renyu.carclient.activity.search.SearchCategoryActivity;
import com.renyu.carclient.base.BaseFragment;
import com.renyu.carclient.commons.ParamUtils;
import com.zbar.lib.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

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
        main_wv.addJavascriptInterface(new WebAppInterface(getActivity()), "android");
        main_wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
        WebSettings settings=main_wv.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.setBlockNetworkLoads(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDatabaseEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);
        main_wv.loadUrl("http://www.kzmall.cn/wap?type=android");
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

    public class WebAppInterface {
        Context context=null;

        public WebAppInterface(Context context) {
            this.context=context;
        }

        @JavascriptInterface
        public void showToast(String string) {
            Log.d("WebAppInterface", string);
            try {
                JSONObject object=new JSONObject(string);
                if (object.getString("type").equals("cat")) {
                    int cat_id=object.getInt("cat_id");
                    //前往分类主页面
                    if (cat_id==-1) {
                        Intent intent1=new Intent(getActivity(), SearchCategoryActivity.class);
                        startActivity(intent1);
                    }
                    //商品分类列表
                    else {
                        Intent intent=new Intent(getActivity(), GoodsListActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("type", ParamUtils.CAT);
                        bundle.putInt("cat_id", cat_id);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
                //商品品牌列表
                else if (object.getString("type").equals("brand")) {
                    Intent intent=new Intent(getActivity(), GoodsListActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("type", ParamUtils.BRAND);
                    bundle.putInt("cat_id", -1);
                    bundle.putInt("brand_id", object.getInt("brand_id"));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                //商品详情页
                else if (object.getString("type").equals("item")) {
                    Intent intent=new Intent(getActivity(), GoodsDetailActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("item_id", object.getString("item_id"));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
