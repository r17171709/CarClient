package com.renyu.carclient.activity.my;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by renyu on 16/1/6.
 */
public class MyRepaymentActivity extends BaseActivity {

    @Bind(R.id.view_toolbar_center_title)
    TextView view_toolbar_center_title;
    @Bind(R.id.view_toolbar_center_back)
    ImageView view_toolbar_center_back;

    @Override
    public int initContentView() {
        return R.layout.activity_myrepayment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
    }

    private void initViews() {
        view_toolbar_center_title.setText("授信还款");
        view_toolbar_center_back.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.view_toolbar_center_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_toolbar_center_back:
                finish();
                break;
        }
    }
}
