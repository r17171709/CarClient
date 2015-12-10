package com.renyu.carclient.activity.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.renyu.carclient.R;
import com.renyu.carclient.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by renyu on 15/12/10.
 */
public class PayOrderActivity extends BaseActivity {

    @Bind(R.id.payorder_lists)
    LinearLayout payorder_lists;

    @Override
    public int initContentView() {
        return R.layout.activity_payorder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
    }

    private void initViews() {
        for (int i=0;i<3;i++) {
            View view= LayoutInflater.from(this).inflate(R.layout.adapter_ordercenterpaybefore, null, false);
            payorder_lists.addView(view);
        }
    }
}
