package com.renyu.carclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by renyu on 15/10/20.
 */
public class RegistActivity extends BaseActivity {

    @Bind(R.id.view_toolbar_center_back)
    ImageView view_toolbar_center_back;
    @Bind(R.id.view_toolbar_center_title)
    TextView view_toolbar_center_title;

    @Override
    public int initContentView() {
        return R.layout.activity_regist;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
    }

    private void initViews() {
        view_toolbar_center_back.setVisibility(View.VISIBLE);
        view_toolbar_center_title.setText("立即注册");
    }
}
