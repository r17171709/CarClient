package com.renyu.carclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.base.BaseFragment;
import com.renyu.carclient.commons.CommonUtils;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by renyu on 15/12/10.
 */
public class OrderFragment extends BaseFragment {

    @Bind(R.id.ordercenter_lv)
    ListView ordercenter_lv;

    ArrayList<View> views=null;

    ArrayList<TextView> numTextViews=null;

    @Override
    public int initContentView() {
        return R.layout.fragment_order;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        views=new ArrayList<>();
        numTextViews=new ArrayList<>();

        initViews();
    }

    private void initViews() {
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
                    view_ordercenter_item_text.setText("待审核");
                    break;
                case 3:
                    view_ordercenter_item_text.setText("代发货");
                    break;
                case 4:
                    view_ordercenter_item_text.setText("已发货");
                    break;
                case 5:
                    view_ordercenter_item_text.setText("已收货");
                    break;
                case 6:
                    view_ordercenter_item_text.setText("已完成");
                    break;
                case 7:
                    view_ordercenter_item_text.setText("已取消");
                    break;
                case 8:
                    view_ordercenter_item_text.setText("退货");
                    break;
                case 9:
                    view_ordercenter_item_text.setText("已关闭");
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
        ordercenter_lv.setAdapter(null);
    }


}
