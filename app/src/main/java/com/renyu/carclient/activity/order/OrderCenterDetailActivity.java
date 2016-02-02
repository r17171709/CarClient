package com.renyu.carclient.activity.order;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.activity.pay.AliPayActivty;
import com.renyu.carclient.adapter.OrderAdapter;
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.commons.ACache;
import com.renyu.carclient.commons.CommonUtils;
import com.renyu.carclient.commons.OKHttpHelper;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.JsonParse;
import com.renyu.carclient.model.OrderModel;
import com.renyu.carclient.model.UserModel;
import com.renyu.carclient.myview.NoScrollListview;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by renyu on 15/12/28.
 */
public class OrderCenterDetailActivity extends BaseActivity {

    @Bind(R.id.view_toolbar_center_layout)
    RelativeLayout view_toolbar_center_layout;
    @Bind(R.id.view_toolbar_center_back)
    ImageView view_toolbar_center_back;
    @Bind(R.id.view_toolbar_center_title)
    TextView view_toolbar_center_title;
    @Bind(R.id.view_toolbar_center_image)
    ImageView view_toolbar_center_image;
    @Bind(R.id.ordercenter_userinfo)
    TextView ordercenter_userinfo;
    @Bind(R.id.ordercenter_address)
    TextView ordercenter_address;
    @Bind(R.id.ordercenter_createtime)
    TextView ordercenter_createtime;
    @Bind(R.id.ordercenter_sendtime)
    TextView ordercenter_sendtime;
    @Bind(R.id.ordercenter_receivetime)
    TextView ordercenter_receivetime;
    @Bind(R.id.ordercenter_paytime)
    TextView ordercenter_paytime;
    @Bind(R.id.ordercenter_state_layout)
    LinearLayout ordercenter_state_layout;
    @Bind(R.id.ordercenter_state_receivegoods)
    LinearLayout ordercenter_state_receivegoods;
    @Bind(R.id.ordercenter_state_receivegoodstime)
    TextView ordercenter_state_receivegoodstime;
    @Bind(R.id.ordercenter_state_waitgoods)
    LinearLayout ordercenter_state_waitgoods;
    @Bind(R.id.ordercenter_state_waitgoodstime)
    TextView ordercenter_state_waitgoodstime;
    @Bind(R.id.ordercenter_slv)
    NoScrollListview ordercenter_slv;
    OrderAdapter adapter=null;
    @Bind(R.id.ordercenter_commit)
    TextView ordercenter_commit;
    @Bind(R.id.ordercenter_applyreturn)
    TextView ordercenter_applyreturn;
    @Bind(R.id.ordercenter_oper)
    LinearLayout ordercenter_oper;
    @Bind(R.id.ordercenter_timeinfo)
    LinearLayout ordercenter_timeinfo;
    @Bind(R.id.ordercenter_return)
    LinearLayout ordercenter_return;
    @Bind(R.id.ordercenter_reason)
    EditText ordercenter_reason;

    String status;
    String tid;

    UserModel userModel=null;

    OrderModel model=null;

    ArrayList<OrderModel> models=null;

    @Override
    public int initContentView() {
        return R.layout.activity_ordercenterdetail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        models=new ArrayList<>();

        status=getIntent().getExtras().getString("status");
        tid=getIntent().getExtras().getString("tid");

        userModel= ACache.get(this).getAsObject("user")!=null?(UserModel) ACache.get(this).getAsObject("user"):null;

        initViews();

        getOrderDetail();
    }

    private void initViews() {
        view_toolbar_center_layout.setBackgroundColor(Color.parseColor("#717171"));
        view_toolbar_center_title.setText("订单详情");
        view_toolbar_center_back.setVisibility(View.VISIBLE);
        view_toolbar_center_image.setImageResource(R.mipmap.logo_white);
        if (status.equals("WAIT_GOODS")) {
            ordercenter_state_layout.setVisibility(View.VISIBLE);
            ordercenter_state_waitgoods.setVisibility(View.VISIBLE);
        }
        else if (status.equals("RECEIVE_GOODS")) {
            ordercenter_state_layout.setVisibility(View.VISIBLE);
            ordercenter_state_receivegoods.setVisibility(View.VISIBLE);
        }
        else {
            ordercenter_state_layout.setVisibility(View.GONE);
        }
        adapter=new OrderAdapter(this, models, true, getIntent().getExtras().getBoolean("isEdit"), null, null, null, null);
        ordercenter_slv.setAdapter(adapter);
        if (getIntent().getExtras().getBoolean("isEdit")) {
            ordercenter_oper.setVisibility(View.GONE);
            ordercenter_timeinfo.setVisibility(View.GONE);
            ordercenter_return.setVisibility(View.VISIBLE);
        }
    }

    private void getOrderDetail() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.xiulichang.order.list", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        //退货单独处理
        if (status.equals("aftersaleslist")) {
            params= ParamUtils.getSignParams("app.xiulichang.aftersaleslist", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        }
        params.put("user_id", "" + userModel.getUser_id());
        params.put("tid", tid);
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                ArrayList<OrderModel> tempModels= JsonParse.getOrderListModel(string);
                if (tempModels!=null) {
                    model=tempModels.get(0);
                }
                else {
                    return;
                }
                ordercenter_userinfo.setText(model.getReceiver_name()+" "+model.getReceiver_mobile());
                String address="";
                for (int i=0;i<model.getBuyer_area().split("/").length;i++) {
                    address+= CommonUtils.getCityInfo(model.getBuyer_area().split("/")[i])+" ";
                }
                address+=model.getReceiver_address();
                ordercenter_address.setText(address);
                ordercenter_createtime.setText(model.getCreated_time()==0?"订单创建：":"订单创建："+ParamUtils.getFormatTime(Long.parseLong(model.getCreated_time()+"000")));
                ordercenter_sendtime.setText(model.getConsign_time()==0?"订单发货：":"订单发货"+ParamUtils.getFormatTime(Long.parseLong(model.getConsign_time()+"000")));
                ordercenter_receivetime.setText(model.getCreated_time()==0?"确认收货：":"确认收货："+ParamUtils.getFormatTime(Long.parseLong(model.getReceiver_time()+"000")));
                ordercenter_paytime.setText(model.getCreated_time()==0?"订单付款：":"订单付款："+ParamUtils.getFormatTime(Long.parseLong(model.getPay_time()+"000")));
                if (status.equals("WAIT_GOODS")) {
                    long extraTime=Long.parseLong(model.getConsign_time()+"000")+Long.parseLong(model.getAutoreceive_parameter()+"000")-System.currentTimeMillis();
                    Log.d("OrderCenterDetailActivi", "extraTime:" + extraTime);

                    String time="";
                    int day= (int) (extraTime/(1000*3600*24));
                    if (day>0) {
                        time+=day+"天";
                    }
                    int hour= (int) ((extraTime-day*(1000*3600*24))/(1000*3600));
                    if (hour>0) {
                        time+=hour+"小时";
                    }
                    int minute= (int) ((extraTime-day*(1000*3600*24)-hour*(1000*3600))/(1000*60));
                    if (minute>0) {
                        time+=minute+"分";
                    }
                    ordercenter_state_waitgoodstime.setText(time);
                }
                else if (status.equals("RECEIVE_GOODS")) {
                    long extraTime=Long.parseLong(model.getReceiver_time()+"000")+Long.parseLong(model.getNeedpaytime_parameter()+"000")-System.currentTimeMillis();
                    Log.d("OrderCenterDetailActivi", "extraTime:" + extraTime);

                    String time="";
                    int day= (int) (extraTime/(1000*3600*24));
                    if (day>0) {
                        time+=day+"天";
                    }
                    int hour= (int) ((extraTime-day*(1000*3600*24))/(1000*3600));
                    if (hour>0) {
                        time+=hour+"小时";
                    }
                    int minute= (int) ((extraTime-day*(1000*3600*24)-hour*(1000*3600))/(1000*60));
                    if (minute>0) {
                        time+=minute+"分";
                    }
                    ordercenter_state_receivegoodstime.setText("订单已签收，请于"+time+"内付款");
                }
                models.clear();
                models.addAll(tempModels);
                adapter.notifyDataSetChanged();
                if (status.equals("WAIT_CONFRIM")) {
                    ordercenter_commit.setVisibility(View.VISIBLE);
                    ordercenter_commit.setText("取消订单");
                    ordercenter_commit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cancelSales(model);
                        }
                    });
                    ordercenter_applyreturn.setVisibility(View.GONE);
                }
                else if (status.equals("DELIVER_GOODS")) {
                    ordercenter_commit.setVisibility(View.VISIBLE);
                    ordercenter_commit.setText("取消订单");
                    ordercenter_commit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cancelSales(model);
                        }
                    });
                    ordercenter_applyreturn.setVisibility(View.GONE);
                }
                else if (status.equals("WAIT_GOODS")) {
                    ordercenter_commit.setVisibility(View.VISIBLE);
                    ordercenter_commit.setText("确认收货");
                    ordercenter_commit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            commitReceive(model);
                        }
                    });
                    ordercenter_applyreturn.setVisibility(View.VISIBLE);
                    ordercenter_applyreturn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adapter.setEdit(!adapter.isEdit());

                            ordercenter_oper.setVisibility(View.GONE);
                            ordercenter_timeinfo.setVisibility(View.GONE);
                            ordercenter_return.setVisibility(View.VISIBLE);
                        }
                    });
                }
                else if (status.equals("RECEIVE_GOODS")) {
                    ordercenter_commit.setVisibility(View.VISIBLE);
                    ordercenter_commit.setText("付款");
                    ordercenter_commit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getPayDetail();
                        }
                    });
                    ordercenter_applyreturn.setVisibility(View.VISIBLE);
                    ordercenter_applyreturn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adapter.setEdit(!adapter.isEdit());

                            ordercenter_oper.setVisibility(View.GONE);
                            ordercenter_timeinfo.setVisibility(View.GONE);
                            ordercenter_return.setVisibility(View.VISIBLE);
                        }
                    });
                }
                else if (status.equals("TRADE_FINISHED")) {
                    ordercenter_commit.setVisibility(View.GONE);
                    ordercenter_applyreturn.setVisibility(View.GONE);
                }
                else if (status.equals("TRADE_CLOSED")) {
                    ordercenter_commit.setVisibility(View.GONE);
                    ordercenter_applyreturn.setVisibility(View.GONE);
                }
                else if (status.equals("TRADE_CANCEL")) {
                    ordercenter_commit.setVisibility(View.GONE);
                    ordercenter_applyreturn.setVisibility(View.GONE);
                }
                else if (status.equals("AFTERSALES")) {
                    ordercenter_commit.setVisibility(View.GONE);
                    ordercenter_applyreturn.setText("取消退货");
                    ordercenter_applyreturn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError() {
                showToast(getResources().getString(R.string.network_error));
            }
        });
    }

    private void getPayDetail() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.payment", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("user_id", "" + userModel.getUser_id());
        params.put("tid", tid);
        params.put("pay_app_id", "alipay");
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                String payData=JsonParse.getPayData(string);
                if (payData==null) {
                    showToast("获取支付参数失败");
                }
                else {
                    Intent intent=new Intent(OrderCenterDetailActivity.this, AliPayActivty.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("payInfo", payData);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, ParamUtils.RESULT_PAY);
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    @OnClick({R.id.ordercenter_return_back, R.id.ordercenter_return_commit, R.id.view_toolbar_center_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ordercenter_return_back:
                adapter.setEdit(false);
                ordercenter_oper.setVisibility(View.VISIBLE);
                ordercenter_timeinfo.setVisibility(View.VISIBLE);
                ordercenter_return.setVisibility(View.GONE);
                break;
            case R.id.ordercenter_return_commit:
                adapter.setEdit(false);

                String oid="";
                for (int i=0;i<model.getOrder().size();i++) {
                    if (model.getOrder().get(i).isChecked()) {
                        oid+=""+model.getOrder().get(i).getOid()+",";
                    }
                }
                if (oid.equals("")) {
                    showToast("请选择至少一件商品");
                    return;
                }
                returnSales(model, oid.substring(0, oid.length()-1));

                ordercenter_oper.setVisibility(View.VISIBLE);
                ordercenter_timeinfo.setVisibility(View.VISIBLE);
                ordercenter_return.setVisibility(View.GONE);
                break;
            case R.id.view_toolbar_center_back:
                finish();
                break;
        }
    }

    private void returnSales(OrderModel model, String oid) {
        HashMap<String, String> params = ParamUtils.getSignParams("app.xiulichang.aftersales.apply", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("user_id", "" + userModel.getUser_id());
        params.put("tid", "" + model.getTid());
        params.put("oids", oid);
        params.put("description", ordercenter_reason.getText().toString());
        httpHelper.commonPostRequest(ParamUtils.api, params, new OKHttpHelper.StartListener() {
            @Override
            public void onStart() {
                showDialog("提示", "正在申请退货");
            }
        }, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                dismissDialog();
                if (JsonParse.getResultValue(string)!=null) {
                    showToast(JsonParse.getResultValue(string));
                    if (JsonParse.getResultInt(string) == 0) {
                        getOrderDetail();
                        EventBus.getDefault().post(new OrderModel());
                    }
                }
                else {
                    showToast("未知错误");
                }
            }

            @Override
            public void onError() {
                dismissDialog();
                showToast(getResources().getString(R.string.network_error));
            }
        });
    }

    private void cancelSales(OrderModel model) {
        HashMap<String, String> params= ParamUtils.getSignParams("app.xiulichang.order.cancel", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("tid", ""+model.getTid());
        httpHelper.commonPostRequest(ParamUtils.api, params, new OKHttpHelper.StartListener() {
            @Override
            public void onStart() {
                showDialog("提示", "正在提交");
            }
        }, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                dismissDialog();

                if (JsonParse.getResultValue(string)!=null) {
                    showToast(JsonParse.getResultValue(string));
                    if (JsonParse.getResultInt(string) == 0) {
                        getOrderDetail();
                        EventBus.getDefault().post(new OrderModel());
                    }
                }
                else {
                    showToast("未知错误");
                }
            }

            @Override
            public void onError() {
                dismissDialog();
                showToast(getResources().getString(R.string.network_error));
            }
        });
    }

    private void commitReceive(OrderModel model) {
        HashMap<String, String> params= ParamUtils.getSignParams("app.xiulichang.order.confirm", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("user_id", ""+userModel.getUser_id());
        params.put("tid", ""+model.getTid());
        httpHelper.commonPostRequest(ParamUtils.api, params, new OKHttpHelper.StartListener() {
            @Override
            public void onStart() {
                showDialog("提示", "正在提交");
            }
        }, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                dismissDialog();

                if (JsonParse.getResultValue(string)!=null) {
                    showToast(JsonParse.getResultValue(string));
                    if (JsonParse.getResultInt(string) == 0) {
                        getOrderDetail();
                        EventBus.getDefault().post(new OrderModel());
                    }
                }
                else {
                    showToast("未知错误");
                }
            }

            @Override
            public void onError() {
                dismissDialog();
                showToast(getResources().getString(R.string.network_error));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ParamUtils.RESULT_PAY && resultCode==RESULT_OK) {
            status="TRADE_FINISHED";
            initViews();
            getOrderDetail();
            EventBus.getDefault().post(new OrderModel());
        }
    }
}
