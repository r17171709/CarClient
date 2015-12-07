package com.renyu.carclient;

import android.app.Application;

import com.renyu.carclient.commons.CommonUtils;

/**
 * Created by renyu on 15/10/17.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CommonUtils.loadDir();

        CommonUtils.initImageLoader(getApplicationContext());
    }
}
