package com.renyu.carclient.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.renyu.carclient.R;
import com.renyu.carclient.adapter.OrderAdapter;
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.commons.ACache;
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
 * Created by renyu on 16/1/11.
 */
public class OrderCenterSearchResultActivity extends BaseActivity {

    @Bind(R.id.view_toolbar_center_title)
    TextView view_toolbar_center_title;
    @Bind(R.id.view_toolbar_center_back)
    ImageView view_toolbar_center_back;
    @Bind(R.id.ordercenter_swipy)
    SwipyRefreshLayout ordercenter_swipy;
    @Bind(R.id.ordercenter_lv)
    ListView ordercenter_lv;
    OrderAdapter adapter = null;

    ArrayList<OrderModel> models = null;

    UserModel userModel = null;

    int page_no = 1;

    @Override
    public int initContentView() {
        return R.layout.fragment_order;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userModel = ACache.get(OrderCenterSearchResultActivity.this).getAsObject("user") != null ? (UserModel) ACache.get(OrderCenterSearchResultActivity.this).getAsObject("user") : null;

        models = new ArrayList<>();

        initViews();

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    private void initViews() {
        view_toolbar_center_title.setText("订单查询结果");
        view_toolbar_center_back.setVisibility(View.VISIBLE);
        adapter = new OrderAdapter(OrderCenterSearchResultActivity.this, models, false, false, new OrderAdapter.OnReturnListener() {
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
        });
        ordercenter_lv.setAdapter(adapter);
        ordercenter_swipy.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        ordercenter_swipy.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction == SwipyRefreshLayoutDirection.BOTTOM) {

                } else if (direction == SwipyRefreshLayoutDirection.TOP) {
                    page_no = 1;
                }
                getOrderLists();
            }
        });
        getOrderLists();
    }

    @OnClick({R.id.view_toolbar_center_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_toolbar_center_back:
                finish();
                break;
        }
    }

    private void getOrderLists() {
        httpHelper.cancel(ParamUtils.api);
        HashMap<String, String> params= ParamUtils.getSignParams("app.xiulichang.advancelist", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("status", getIntent().getExtras().getString("senior_status"));
        if (getIntent().getExtras().getString("senior_product")!=null) {
            params.put("title", getIntent().getExtras().getString("senior_product"));
        }
        if (getIntent().getExtras().getString("senior_productid")!=null) {
            params.put("tid", getIntent().getExtras().getString("senior_productid"));
        }
        if (getIntent().getExtras().getString("senior_time1")!=null && getIntent().getExtras().getString("senior_time2")!=null) {
            params.put("created_time_start", getIntent().getExtras().getString("senior_time1"));
            params.put("created_time_end", getIntent().getExtras().getString("senior_time2"));
        }
        if (getIntent().getExtras().getString("senior_price1")!=null && getIntent().getExtras().getString("senior_price2")!=null) {
            params.put("fee_start", getIntent().getExtras().getString("senior_price1"));
            params.put("fee_end", getIntent().getExtras().getString("senior_price2"));
        }
        params.put("user_id", ""+userModel.getUser_id());
        httpHelper.commonPostRequest(ParamUtils.api, params, new OKHttpHelper.StartListener() {
            @Override
            public void onStart() {
                showDialog("提示", "正在搜索");
            }
        }, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                dismissDialog();

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
                dismissDialog();

                ordercenter_swipy.setRefreshing(false);
                if (page_no==1) {
                    models.clear();
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void returnSales(OrderModel model, int position) {
        Intent intent = new Intent(OrderCenterSearchResultActivity.this, OrderCenterDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("tid", "" + model.getTid());
        bundle.putString("status", model.getStatus());
        bundle.putBoolean("isEdit", true);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void cancelSales(OrderModel model) {
        HashMap<String, String> params = ParamUtils.getSignParams("app.xiulichang.order.cancel", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("user_id", "" + userModel.getUser_id());
        params.put("tid", "" + model.getTid());
        httpHelper.commonPostRequest(ParamUtils.api, params, new OKHttpHelper.StartListener() {
            @Override
            public void onStart() {
                showDialog("提示", "正在提交");
            }
        }, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                dismissDialog();

                if (JsonParse.getResultValue(string) != null) {
                    showToast(JsonParse.getResultValue(string));
                    if (JsonParse.getResultInt(string) == 0) {
                        ordercenter_swipy.setRefreshing(true);
                        page_no = 1;
                        getOrderLists();

                        EventBus.getDefault().post(new OrderModel());
                    }
                }
            }

            @Override
            public void onError() {
                dismissDialog();
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
                        getOrderLists();

                        EventBus.getDefault().post(new OrderModel());
                    }
                }
            }

            @Override
            public void onError() {
                dismissDialog();
            }
        });
    }

    public void onEventMainThread(OrderModel model) {
        ordercenter_swipy.setRefreshing(true);
        page_no=1;
        getOrderLists();
    }
}
