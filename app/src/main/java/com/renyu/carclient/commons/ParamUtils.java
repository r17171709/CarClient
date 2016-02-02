package com.renyu.carclient.commons;

import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by renyu on 15/10/17.
 */
public class ParamUtils {

    public final static String DIR= Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"carclient";
    public final static String IMAGECACHE=DIR+ File.separator+"cache";
    public final static String DB=DIR+File.separator+"db";
    //微信分享ID
    public final static String WEIXIN_SHAREID="";
    //QQ分享ID manifest里面也要配置
    public final static String QQ_SHAREID="";

    public final static String api="http://120.26.139.82/b2b2c/public/index.php/api";

    public final static String CAT="cat_type";
    public final static String BRAND="brand_type";
    public final static String CAR="car_type";
    public final static String SEARCH="search_type";

    public final static int RESULT_LOGIN=1000;
    public final static int RESULT_ADDRESS=1001;
    public final static int RESULT_AREA=1002;
    public final static int RESULT_PAY=1003;
    public final static int takecamera_result=10003;
    public final static int choicePic_result=10008;
    public final static int crop_result=10021;

    private static String getSign(String method, String token) {
        return CommonUtils.MD5(CommonUtils.MD5(method).toUpperCase() + token).toUpperCase();
    }

    public static HashMap<String, String> getSignParams(String method, String token) {
        HashMap<String, String> params=new HashMap<>();
        params.put("method", method);
        params.put("timestamp", ""+System.currentTimeMillis()/1000);
        params.put("format", "json");
        params.put("v", "v1");
        params.put("sign_type", "MD5");
        params.put("sign", getSign(method, token));
        return params;
    }

    public static String converNull(String value) {
        return (value == null || value.equals("null") ? "" : value);
    }

    public static int converInt(String value) {
        return (value == null || value.equals("null") ? 0 : Integer.parseInt(value));
    }

    public static long converLong(String value) {
        return (value == null || value.equals("null") ? 0 : Long.parseLong(value));
    }

    public static String getFormatTime(long time) {
        if (time==0) {
            return "";
        }
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(time));
    }

    public static String getFormatTime2(long time) {
        if (time==0) {
            return "";
        }
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(new Date(time));
    }
}
