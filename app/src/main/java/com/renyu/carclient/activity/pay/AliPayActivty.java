package com.renyu.carclient.activity.pay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.renyu.carclient.model.PayResult;

/**
 * Created by renyu on 16/1/21.
 */
public class AliPayActivty extends Activity {

    static int SDK_PAY_FLAG = 1;
    static int SDK_CHECK_FLAG = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        check();
    }

    private void check() {
        Runnable checkRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask payTask = new PayTask(AliPayActivty.this);
                // 调用查询接口，获取查询结果
                boolean isExist = payTask.checkAccountIfExist();

                Message msg = new Message();
                msg.what = SDK_CHECK_FLAG;
                msg.obj = isExist?"OK":"FAIL";
                mHandler.sendMessage(msg);
            }
        };

        Thread checkThread = new Thread(checkRunnable);
        checkThread.start();

    }

    private void pay() {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(AliPayActivty.this);
                // 调用支付接口，获取支付结果
                String value=getIntent().getExtras().getString("payInfo");
                String result = alipay.pay(value, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if(msg.what==SDK_PAY_FLAG) {
                PayResult payResult = new PayResult((String) msg.obj);

                // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                String resultInfo = payResult.getResult();

                String resultStatus = payResult.getResultStatus();
                System.out.println("resultInfo:"+resultStatus);

                // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                if (TextUtils.equals(resultStatus, "9000")) {
                    Toast.makeText(AliPayActivty.this, "支付成功", Toast.LENGTH_SHORT).show();
                    Intent newIntent=new Intent();
                    setResult(RESULT_OK, newIntent);
                }
                else {
                    // 判断resultStatus 为非“9000”则代表可能支付失败
                    // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                    if (TextUtils.equals(resultStatus, "8000")) {
                        Toast.makeText(AliPayActivty.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                    } else {
                        // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                        Toast.makeText(AliPayActivty.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                }
                finish();
            }
            else if(msg.what==SDK_CHECK_FLAG) {
                if(String.valueOf(msg.obj).equals("OK")) {
                    pay();
                }
                else {
                    Toast.makeText(AliPayActivty.this, "您的手机暂无支付宝认证账户，请在支付宝客户端中添加后再试", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
}
