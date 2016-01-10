package com.renyu.carclient.activity.my;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.adapter.MyStatementAdapter;
import com.renyu.carclient.base.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by renyu on 15/11/14.
 */
public class MyStatementActivity extends BaseActivity {

    @Bind(R.id.view_toolbar_center_title)
    TextView view_toolbar_center_title;
    @Bind(R.id.view_toolbar_center_back)
    ImageView view_toolbar_center_back;
    @Bind(R.id.myincomestatement_rv)
    RecyclerView myincomestatement_rv;

    MyStatementAdapter adapter=null;

    ArrayList<String> models=null;

    @Override
    public int initContentView() {
        return R.layout.activity_mystatement;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        models=new ArrayList<>();
        models.add("");
        models.add("");
        models.add("");
        models.add("");
        models.add("");
        models.add("");

        initViews();
    }

    private void initViews() {
        view_toolbar_center_title.setText("支付记录");
        view_toolbar_center_back.setVisibility(View.VISIBLE);
        myincomestatement_rv.setHasFixedSize(true);
        myincomestatement_rv.setLayoutManager(new LinearLayoutManager(this));
        adapter=new MyStatementAdapter(this, models);
        myincomestatement_rv.setAdapter(adapter);
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
