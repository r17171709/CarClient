package com.renyu.carclient.activity.search;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.adapter.GoodsDetailAdapter;
import com.renyu.carclient.base.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by renyu on 15/10/18.
 */
public class GoodsListActivity extends BaseActivity {

    @Bind(R.id.view_toolbar_center_layout)
    RelativeLayout view_toolbar_center_layout;
    @Bind(R.id.view_toolbar_center_image)
    ImageView view_toolbar_center_image;
    @Bind(R.id.view_toolbar_center_title)
    TextView view_toolbar_center_title;
    @Bind(R.id.view_toolbar_center_back)
    ImageView view_toolbar_center_back;
    @Bind(R.id.goods_detail_rv)
    RecyclerView goods_detail_rv;

    GoodsDetailAdapter adapter=null;

    ArrayList<String> strings=null;

    @Override
    public int initContentView() {
        return R.layout.activity_goodsdetail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        strings=new ArrayList<>();
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");

        initViews();
    }

    private void initViews() {
        view_toolbar_center_layout.setBackgroundColor(Color.WHITE);
        view_toolbar_center_image.setImageResource(R.mipmap.ic_search_logo_null);
        view_toolbar_center_title.setText("商品列表");
        view_toolbar_center_title.setTextColor(Color.parseColor("#181818"));
        view_toolbar_center_back.setImageResource(R.mipmap.ic_back_red);
        goods_detail_rv.setHasFixedSize(true);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(this);
        goods_detail_rv.setLayoutManager(manager);
        adapter=new GoodsDetailAdapter(this, strings);
        goods_detail_rv.setAdapter(adapter);
    }

    @OnClick({R.id.goods_detail_multiple, R.id.goods_detail_sale, R.id.goods_detail_Screening})
    public void jumpClick(View view) {
        switch (view.getId()) {
            case R.id.goods_detail_multiple:
                break;
            case R.id.goods_detail_sale:
                break;
            case R.id.goods_detail_Screening:
                break;
        }
    }
}
