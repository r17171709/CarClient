package com.renyu.carclient.activity.my;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.adapter.MessageCenterAdapter;
import com.renyu.carclient.base.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by renyu on 15/11/12.
 */
public class MessageCenterActivity extends BaseActivity {

    @Bind(R.id.view_toolbar_center_layout)
    RelativeLayout view_toolbar_center_layout;
    @Bind(R.id.view_toolbar_center_title)
    TextView view_toolbar_center_title;
    @Bind(R.id.view_toolbar_center_back)
    ImageView view_toolbar_center_back;
    @Bind(R.id.view_toolbar_center_image)
    ImageView view_toolbar_center_image;
    @Bind(R.id.messagecenter_rv)
    RecyclerView messagecenter_rv;
    MessageCenterAdapter adapter;

    ArrayList<String> models=null;

    @Override
    public int initContentView() {
        return R.layout.activity_messagecenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        models=new ArrayList<>();
        models.add("1");
        models.add("1");
        models.add("1");
        models.add("1");
        models.add("1");
        models.add("1");
        models.add("1");
        models.add("1");

        initViews();
    }

    private void initViews() {
        messagecenter_rv.setHasFixedSize(true);
        messagecenter_rv.setLayoutManager(new LinearLayoutManager(this));
        adapter=new MessageCenterAdapter(this, models);
        messagecenter_rv.setAdapter(adapter);
    }

    @OnClick({R.id.view_toolbar_center_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_toolbar_center_back:
                finish();
        }
    }
}
