package com.renyu.carclient.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.renyu.carclient.R;
import com.renyu.carclient.activity.order.CartActivity;
import com.renyu.carclient.activity.order.OrderCenterDetailActivity;
import com.renyu.carclient.activity.order.OrderCenterSearchActivity;
import com.renyu.carclient.activity.order.OrderCenterSearchResultActivity;
import com.renyu.carclient.activity.pay.AliPayActivty;
import com.renyu.carclient.adapter.OrderAdapter;
import com.renyu.carclient.base.BaseFragment;
import com.renyu.carclient.commons.ACache;
import com.renyu.carclient.commons.CommonUtils;
import com.renyu.carclient.commons.OKHttpHelper;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.JsonParse;
import com.renyu.carclient.model.OrderModel;
import com.renyu.carclient.model.UserModel;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by renyu on 15/12/10.
 */
public class OrderFragment extends BaseFragment {

    @Bind(R.id.view_toolbar_center_title)
    TextView view_toolbar_center_title;
    @Bind(R.id.view_toolbar_center_next)
    ImageView view_toolbar_center_next;
    @Bind(R.id.ordercenter_swipy)
    SwipyRefreshLayout ordercenter_swipy;
    @Bind(R.id.ordercenter_lv)
    ListView ordercenter_lv;
    OrderAdapter adapter=null;

    ArrayList<View> views=null;

    ArrayList<TextView> numTextViews=null;
    ArrayList<TextView> textViews=null;
    ArrayList<ImageView> imageViews=null;

    ArrayList<OrderModel> models=null;

    UserModel userModel=null;

    int page_no=1;

    //当前选中的条件
    int current_choice=-1;
    //当前支付选项
    int pay_choice=-1;

    @Override
    public int initContentView() {
        return R.layout.fragment_order;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userModel= ACache.get(getActivity()).getAsObject("user")!=null?(UserModel) ACache.get(getActivity()).getAsObject("user"):null;

        views=new ArrayList<>();
        numTextViews=new ArrayList<>();
        textViews=new ArrayList<>();
        imageViews=new ArrayList<>();
        models=new ArrayList<>();

        initViews();
    }

    private void initViews() {
        view_toolbar_center_title.setText("订单");
        view_toolbar_center_next.setVisibility(View.VISIBLE);
        view_toolbar_center_next.setImageResource(R.mipmap.ic_goodslist_cart);
        View headview= LayoutInflater.from(getActivity()).inflate(R.layout.view_ordercenterheadview, null, false);
        final EditText ordercenter_edittext= (EditText) headview.findViewById(R.id.ordercenter_edittext);
        ordercenter_edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_SEARCH) {
                    Intent intent=new Intent(getActivity(), OrderCenterSearchResultActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("senior_status", "");
                    if (!ordercenter_edittext.getText().toString().equals("")) {
                        bundle.putString("senior_product", ordercenter_edittext.getText().toString());
                    }
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                return false;
            }
        });
        LinearLayout ordercenter_senior= (LinearLayout) headview.findViewById(R.id.ordercenter_senior);
        ordercenter_senior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(getActivity(), OrderCenterSearchActivity.class);
                startActivity(intent2);
            }
        });
        GridLayout ordercenter_gridlayout= (GridLayout) headview.findViewById(R.id.ordercenter_gridlayout);
        int width= CommonUtils.getScreenWidth(getActivity())/5;
        for (int i=0;i<9;i++) {
            final int i_=i;
            View itemView=LayoutInflater.from(getActivity()).inflate(R.layout.view_ordercenter_item, null, false);
            views.add(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j=0;j<textViews.size();j++) {
                        textViews.get(j).setTextColor(Color.BLACK);
                    }
                    for (int k=0;k<imageViews.size();k++) {
                        switch (k) {
                            case 0:
                                imageViews.get(k).setImageResource(R.mipmap.order_icon1_black);
                                break;
                            case 1:
                                imageViews.get(k).setImageResource(R.mipmap.order_icon2_black);
                                break;
                            case 2:
                                imageViews.get(k).setImageResource(R.mipmap.order_icon3_black);
                                break;
                            case 3:
                                imageViews.get(k).setImageResource(R.mipmap.order_icon4_black);
                                break;
                            case 4:
                                imageViews.get(k).setImageResource(R.mipmap.order_icon5_black);
                                break;
                            case 5:
                                imageViews.get(k).setImageResource(R.mipmap.order_icon6_black);
                                break;
                            case 6:
                                imageViews.get(k).setImageResource(R.mipmap.order_icon7_black);
                                break;
                            case 7:
                                imageViews.get(k).setImageResource(R.mipmap.order_icon8_black);
                                break;
                            case 8:
                                imageViews.get(k).setImageResource(R.mipmap.order_icon9_black);
                                break;
                        }
                    }
                    page_no=1;
                    current_choice=i_;
                    textViews.get(i_).setTextColor(Color.parseColor("#c40000"));
                    switch (i_) {
                        case 0:
                            getOrderLists("");
                            imageViews.get(i_).setImageResource(R.mipmap.order_icon1_red);
                            break;
                        case 1:
                            getOrderLists("WAIT_CONFRIM");
                            imageViews.get(i_).setImageResource(R.mipmap.order_icon2_red);
                            break;
                        case 2:
                            getOrderLists("DELIVER_GOODS");
                            imageViews.get(i_).setImageResource(R.mipmap.order_icon3_red);
                            break;
                        case 3:
                            getOrderLists("WAIT_GOODS");
                            imageViews.get(i_).setImageResource(R.mipmap.order_icon4_red);
                            break;
                        case 4:
                            getOrderLists("RECEIVE_GOODS");
                            imageViews.get(i_).setImageResource(R.mipmap.order_icon5_red);
                            break;
                        case 5:
                            getOrderLists("TRADE_FINISHED");
                            imageViews.get(i_).setImageResource(R.mipmap.order_icon6_red);
                            break;
                        case 6:
                            getOrderLists("TRADE_CLOSED");
                            imageViews.get(i_).setImageResource(R.mipmap.order_icon7_red);
                            break;
                        case 7:
                            getOrderLists("TRADE_CANCEL");
                            imageViews.get(i_).setImageResource(R.mipmap.order_icon8_red);
                            break;
                        case 8:
                            getOrderLists("aftersaleslist");
                            imageViews.get(i_).setImageResource(R.mipmap.order_icon9_red);
                            break;
                    }
                    ordercenter_swipy.setRefreshing(true);
                }
            });
            ImageView view_ordercenter_item_image= (ImageView) itemView.findViewById(R.id.view_ordercenter_item_image);
            imageViews.add(view_ordercenter_item_image);
            TextView view_ordercenter_item_num= (TextView) itemView.findViewById(R.id.view_ordercenter_item_num);
            numTextViews.add(view_ordercenter_item_num);
            TextView view_ordercenter_item_text= (TextView) itemView.findViewById(R.id.view_ordercenter_item_text);
            textViews.add(view_ordercenter_item_text);
            switch (i) {
                case 0:
                    view_ordercenter_item_text.setText("全部");
                    view_ordercenter_item_image.setImageResource(R.mipmap.order_icon1_black);
                    view_ordercenter_item_text.setTextColor(Color.BLACK);
                    break;
                case 1:
                    view_ordercenter_item_text.setText("待确认");
                    view_ordercenter_item_image.setImageResource(R.mipmap.order_icon2_black);
                    view_ordercenter_item_text.setTextColor(Color.BLACK);
                    break;
                case 2:
                    view_ordercenter_item_text.setText("待发货");
                    view_ordercenter_item_image.setImageResource(R.mipmap.order_icon3_black);
                    view_ordercenter_item_text.setTextColor(Color.BLACK);
                    break;
                case 3:
                    view_ordercenter_item_text.setText("待收货");
                    view_ordercenter_item_image.setImageResource(R.mipmap.order_icon4_black);
                    view_ordercenter_item_text.setTextColor(Color.BLACK);
                    break;
                case 4:
                    view_ordercenter_item_text.setText("待付款");
                    view_ordercenter_item_image.setImageResource(R.mipmap.order_icon5_black);
                    view_ordercenter_item_text.setTextColor(Color.BLACK);
                    break;
                case 5:
                    view_ordercenter_item_text.setText("已完成");
                    view_ordercenter_item_image.setImageResource(R.mipmap.order_icon6_black);
                    view_ordercenter_item_text.setTextColor(Color.BLACK);
                    break;
                case 6:
                    view_ordercenter_item_text.setText("已关闭");
                    view_ordercenter_item_image.setImageResource(R.mipmap.order_icon7_black);
                    view_ordercenter_item_text.setTextColor(Color.BLACK);
                    break;
                case 7:
                    view_ordercenter_item_text.setText("已取消");
                    view_ordercenter_item_image.setImageResource(R.mipmap.order_icon8_black);
                    view_ordercenter_item_text.setTextColor(Color.BLACK);
                    break;
                case 8:
                    view_ordercenter_item_text.setText("退货");
                    view_ordercenter_item_image.setImageResource(R.mipmap.order_icon9_black);
                    view_ordercenter_item_text.setTextColor(Color.BLACK);
                    break;
            }
            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.width=width;
            param.columnSpec = GridLayout.spec(i<5?i%5:(i+1)%5);
            if (i==0) {
                param.rowSpec = GridLayout.spec(0, 2);
                param.height=CommonUtils.dip2px(getActivity(), 170);
            }
            else {
                param.rowSpec = GridLayout.spec(i/5, 1);
                param.height=CommonUtils.dip2px(getActivity(), 85);
            }
            ordercenter_gridlayout.addView(itemView, param);
        }
        ordercenter_lv.addHeaderView(headview);
        adapter=new OrderAdapter(getActivity(), models, false, false, new OrderAdapter.OnReturnListener() {
            @Override
            public void returnValue(OrderModel model, int position) {
                returnSales(model, position);
            }
        }, new OrderAdapter.OnCancelListener() {
            @Override
            public void cancelValue(int position) {
                cancelSales(models.get(position));
            }
        }, new OrderAdapter.OnReceiveListener() {
            @Override
            public void receiveValue(int position) {
                commitReceive(models.get(position));
            }
        }, new OrderAdapter.OnPayListener() {
            @Override
            public void payValue(int position) {
                pay_choice=position;
                pay(models.get(position));
            }
        });
        ordercenter_lv.setAdapter(adapter);
        ordercenter_swipy.setDirection(SwipyRefreshLayoutDirection.BOTH);
        ordercenter_swipy.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        ordercenter_swipy.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction == SwipyRefreshLayoutDirection.BOTTOM) {

                } else if (direction == SwipyRefreshLayoutDirection.TOP) {
                    page_no=1;
                }
                switch (current_choice) {
                    case 0:
                        getOrderLists("");
                        break;
                    case 1:
                        getOrderLists("WAIT_CONFRIM");
                        break;
                    case 2:
                        getOrderLists("DELIVER_GOODS");
                        break;
                    case 3:
                        getOrderLists("WAIT_GOODS");
                        break;
                    case 4:
                        getOrderLists("RECEIVE_GOODS");
                        break;
                    case 5:
                        getOrderLists("TRADE_FINISHED");
                        break;
                    case 6:
                        getOrderLists("TRADE_CLOSED");
                        break;
                    case 7:
                        getOrderLists("TRADE_CANCEL");
                        break;
                    case 8:
                        getOrderLists("aftersaleslist");
                        break;
                }
            }
        });

        views.get(0).performClick();
    }

    private void pay(OrderModel orderModel) {
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.payment", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("user_id", "" + userModel.getUser_id());
        params.put("tid", ""+orderModel.getTid());
        params.put("pay_app_id", "alipay");
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                String payData=JsonParse.getPayData(string);
                if (payData==null) {
                    showToast("获取支付参数失败");
                }
                else {
                    Intent intent=new Intent(getActivity(), AliPayActivty.class);
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

    @OnClick({R.id.view_toolbar_center_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_toolbar_center_next:
                Intent intent=new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void getOrderLists(String status) {
        if (page_no==1) {
            getCount();
            models.clear();
            adapter.notifyDataSetChanged();
        }
        HashMap<String, String> params= ParamUtils.getSignParams("app.xiulichang.order.list", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        //退货单独处理
        if (status.equals("aftersaleslist")) {
            params= ParamUtils.getSignParams("app.xiulichang.aftersaleslist", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        }
        params.put("user_id", ""+userModel.getUser_id());
        params.put("page_size", "20");
        params.put("page_no", ""+page_no);
        if (!status.equals("") && !status.equals("aftersaleslist")) {
            params.put("status", status);
        }
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                ordercenter_swipy.setRefreshing(false);
                ArrayList<OrderModel> tempModels= JsonParse.getOrderListModel(string);
                if (page_no==1) {
                    models.clear();
                }
                if (tempModels!=null) {
                    models.addAll(tempModels);
                }
                adapter.notifyDataSetChanged();
                page_no++;
            }

            @Override
            public void onError() {
                ordercenter_swipy.setRefreshing(false);
                if (page_no==1) {
                    models.clear();
                }
                adapter.notifyDataSetChanged();
                showToast(getResources().getString(R.string.network_error));
            }
        });
    }

    private void returnSales(OrderModel model, int position) {
        Intent intent=new Intent(getActivity(), OrderCenterDetailActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("tid", ""+model.getTid());
        bundle.putString("status", model.getStatus());
        bundle.putBoolean("isEdit", true);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void cancelSales(OrderModel model) {
        HashMap<String, String> params= ParamUtils.getSignParams("app.xiulichang.order.cancel", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
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
                        ordercenter_swipy.setRefreshing(true);
                        page_no=1;
                        switch (current_choice) {
                            case 0:
                                getOrderLists("");
                                break;
                            case 1:
                                getOrderLists("WAIT_CONFRIM");
                                break;
                            case 2:
                                getOrderLists("DELIVER_GOODS");
                                break;
                            case 3:
                                getOrderLists("WAIT_GOODS");
                                break;
                            case 4:
                                getOrderLists("RECEIVE_GOODS");
                                break;
                            case 5:
                                getOrderLists("TRADE_FINISHED");
                                break;
                            case 6:
                                getOrderLists("TRADE_CLOSED");
                                break;
                            case 7:
                                getOrderLists("TRADE_CANCEL");
                                break;
                            case 8:
                                getOrderLists("aftersaleslist");
                                break;
                        }
                    }
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
                        ordercenter_swipy.setRefreshing(true);
                        page_no=1;
                        switch (current_choice) {
                            case 0:
                                getOrderLists("");
                                break;
                            case 1:
                                getOrderLists("WAIT_CONFRIM");
                                break;
                            case 2:
                                getOrderLists("DELIVER_GOODS");
                                break;
                            case 3:
                                getOrderLists("WAIT_GOODS");
                                break;
                            case 4:
                                getOrderLists("RECEIVE_GOODS");
                                break;
                            case 5:
                                getOrderLists("TRADE_FINISHED");
                                break;
                            case 6:
                                getOrderLists("TRADE_CLOSED");
                                break;
                            case 7:
                                getOrderLists("TRADE_CANCEL");
                                break;
                            case 8:
                                getOrderLists("aftersaleslist");
                                break;
                        }
                    }
                }
            }

            @Override
            public void onError() {
                dismissDialog();
                showToast(getResources().getString(R.string.network_error));
            }
        });
    }

    private void getCount() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.xiulichang.order.count", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("user_id", ""+userModel.getUser_id());
        httpHelper.commonPostRequest(ParamUtils.api, params, new OKHttpHelper.StartListener() {
            @Override
            public void onStart() {

            }
        }, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                Log.d("OrderFragment", string);
                HashMap<String, String> map=JsonParse.getNum(string);
                for (int i=0;i<numTextViews.size();i++) {
                    if (i==0) {

                    }
                    else if (i==1) {
                        numTextViews.get(i).setText(map.get("WAIT_CONFRIM"));
                    }
                    else if (i==2) {
                        numTextViews.get(i).setText(map.get("DELIVER_GOODS"));
                    }
                    else if (i==3) {
                        numTextViews.get(i).setText(map.get("WAIT_GOODS"));
                    }
                    else if (i==4) {
                        numTextViews.get(i).setText(map.get("RECEIVE_GOODS"));
                    }
                    else if (i==5) {
                        numTextViews.get(i).setText(map.get("TRADE_FINISHED"));
                    }
                    else if (i==6) {
                        numTextViews.get(i).setText(map.get("TRADE_CLOSED"));
                    }
                    else if (i==7) {
                        numTextViews.get(i).setText(map.get("TRADE_CANCEL"));
                    }
                    else if (i==8) {
                        numTextViews.get(i).setText(map.get("AFTERSALES"));
                    }
                    if (numTextViews.get(i).getText().toString().equals("0")) {
                        numTextViews.get(i).setVisibility(View.INVISIBLE);
                    }
                    else {
                        numTextViews.get(i).setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    public void onEventMainThread(OrderModel model) {
        ordercenter_swipy.setRefreshing(true);
        page_no=1;
        switch (current_choice) {
            case 0:
                getOrderLists("");
                break;
            case 1:
                getOrderLists("WAIT_CONFRIM");
                break;
            case 2:
                getOrderLists("DELIVER_GOODS");
                break;
            case 3:
                getOrderLists("WAIT_GOODS");
                break;
            case 4:
                getOrderLists("RECEIVE_GOODS");
                break;
            case 5:
                getOrderLists("TRADE_FINISHED");
                break;
            case 6:
                getOrderLists("TRADE_CLOSED");
                break;
            case 7:
                getOrderLists("TRADE_CANCEL");
                break;
            case 8:
                getOrderLists("aftersaleslist");
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ParamUtils.RESULT_PAY && resultCode== Activity.RESULT_OK) {

            Intent intent=new Intent(getActivity(), OrderCenterDetailActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("tid", ""+models.get(pay_choice).getTid());
            bundle.putString("status", "TRADE_FINISHED");
            bundle.putBoolean("isEdit", false);
            intent.putExtras(bundle);
            getActivity().startActivity(intent);

            EventBus.getDefault().post(new OrderModel());
        }
    }
}
