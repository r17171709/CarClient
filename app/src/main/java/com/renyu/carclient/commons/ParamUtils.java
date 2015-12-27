package com.renyu.carclient.commons;

import java.io.File;
import java.util.HashMap;

/**
 * Created by renyu on 15/10/17.
 */
public class ParamUtils {

    public final static String DIR="carclient";
    public final static String IMAGECACHE=DIR+ File.separator+"cache";
    //微信分享ID
    public final static String WEIXIN_SHAREID="";
    //QQ分享ID manifest里面也要配置
    public final static String QQ_SHAREID="";

    public final static String api="http://120.26.139.82/b2b2c/public/index.php/api";
    public final static String CAT="cat_type";
    public final static String BRAND="brand_type";

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
}
