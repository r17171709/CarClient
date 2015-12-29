package com.renyu.carclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.renyu.carclient.R;
import com.renyu.carclient.activity.order.CartActivity;
import com.renyu.carclient.adapter.OrderAdapter;
import com.renyu.carclient.base.BaseFragment;
import com.renyu.carclient.commons.CommonUtils;
import com.renyu.carclient.commons.OKHttpHelper;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.JsonParse;
import com.renyu.carclient.model.OrderModel;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by renyu on 15/12/10.
 */
public class OrderFragment extends BaseFragment {

    @Bind(R.id.view_toolbar_center_next)
    ImageView view_toolbar_center_next;
    @Bind(R.id.ordercenter_swipy)
    SwipyRefreshLayout ordercenter_swipy;
    @Bind(R.id.ordercenter_lv)
    ListView ordercenter_lv;
    OrderAdapter adapter=null;

    ArrayList<View> views=null;

    ArrayList<TextView> numTextViews=null;

    ArrayList<OrderModel> models=null;

    int page_no=1;

    //当前选中的条件
    int current_choice=-1;

    @Override
    public int initContentView() {
        return R.layout.fragment_order;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        views=new ArrayList<>();
        numTextViews=new ArrayList<>();
        models=new ArrayList<>();

        initViews();
    }

    private void initViews() {
        view_toolbar_center_next.setVisibility(View.VISIBLE);
        View headview= LayoutInflater.from(getActivity()).inflate(R.layout.view_ordercenterheadview, null, false);
        GridLayout ordercenter_gridlayout= (GridLayout) headview.findViewById(R.id.ordercenter_gridlayout);
        int width= CommonUtils.getScreenWidth(getActivity())/5;
        for (int i=0;i<9;i++) {
            final int i_=i;
            View itemView=LayoutInflater.from(getActivity()).inflate(R.layout.view_ordercenter_item, null, false);
            views.add(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    page_no=1;
                    current_choice=i_;
                    switch (i_) {
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
                    ordercenter_swipy.setRefreshing(true);
                }
            });
            TextView view_ordercenter_item_num= (TextView) itemView.findViewById(R.id.view_ordercenter_item_num);
            numTextViews.add(view_ordercenter_item_num);
            TextView view_ordercenter_item_text= (TextView) itemView.findViewById(R.id.view_ordercenter_item_text);
            switch (i) {
                case 0:
                    view_ordercenter_item_text.setText("全部");
                    break;
                case 1:
                    view_ordercenter_item_text.setText("待确认");
                    break;
                case 2:
                    view_ordercenter_item_text.setText("待发货");
                    break;
                case 3:
                    view_ordercenter_item_text.setText("待收货");
                    break;
                case 4:
                    view_ordercenter_item_text.setText("待付款");
                    break;
                case 5:
                    view_ordercenter_item_text.setText("已完成");
                    break;
                case 6:
                    view_ordercenter_item_text.setText("已关闭");
                    break;
                case 7:
                    view_ordercenter_item_text.setText("已取消");
                    break;
                case 8:
                    view_ordercenter_item_text.setText("退货");
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
        adapter=new OrderAdapter(getActivity(), models, false);
        ordercenter_lv.setAdapter(adapter);
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
        HashMap<String, String> params= ParamUtils.getSignParams("app.xiulichang.order.list", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        //退货单独处理
        if (status.equals("aftersaleslist")) {
            params= ParamUtils.getSignParams("app.xiulichang.aftersaleslist", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        }
        params.put("user_id", "57");
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
            }
        });
    }
}
