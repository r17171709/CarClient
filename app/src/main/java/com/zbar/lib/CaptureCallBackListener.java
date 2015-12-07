package com.zbar.lib;

/**
 * Created by renyu on 15/10/18.
 */
public interface CaptureCallBackListener {
    void onCaptureSuccess(String result);
    void onCaptureFail();
    void onSwitchListener();
}
