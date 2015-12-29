package com.renyu.carclient;

import android.app.Application;

import com.renyu.carclient.commons.CommonUtils;
import com.renyu.carclient.commons.ParamUtils;

/**
 * Created by renyu on 15/10/17.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CommonUtils.loadDir();

        CommonUtils.initImageLoader(getApplicationContext());

        CommonUtils.copyAssetsFile("area.db", ParamUtils.DB, getApplicationContext());
    }
}
