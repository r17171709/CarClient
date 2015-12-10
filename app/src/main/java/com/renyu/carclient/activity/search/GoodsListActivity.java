package com.renyu.carclient.activity.search;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.adapter.GoodsListGridAdapter;
import com.renyu.carclient.adapter.GoodsListLinearAdapter;
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
    @Bind(R.id.goods_list_rv)
    RecyclerView goods_list_rv;
    @Bind(R.id.goods_list_screening)
    TextView goods_list_screening;
    @Bind(R.id.goods_list_changetype)
    ImageView goods_list_changetype;

    PopupWindow pop=null;

    GoodsListLinearAdapter linearAdapter=null;
    GoodsListGridAdapter gridAdapter=null;

    ArrayList<String> strings=null;

    boolean isLinearMode=true;

    @Override
    public int initContentView() {
        return R.layout.activity_goodslist;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        strings=new ArrayList<>();

        initViews();
    }

    private void initViews() {
        view_toolbar_center_layout.setBackgroundColor(Color.WHITE);
        view_toolbar_center_image.setImageResource(R.mipmap.ic_search_logo_null);
        view_toolbar_center_title.setText("商品列表");
        view_toolbar_center_title.setTextColor(Color.parseColor("#181818"));
        view_toolbar_center_back.setImageResource(R.mipmap.ic_back_red);

        goods_list_rv.setHasFixedSize(true);
        goods_list_rv.setLayoutManager(new LinearLayoutManager(this));
        linearAdapter=new GoodsListLinearAdapter(this, strings);
        gridAdapter=new GoodsListGridAdapter(this, strings);
        goods_list_screening.performClick();

        View popView= LayoutInflater.from(this).inflate(R.layout.pop_goodslist, null, false);
        TextView pop_goodslist_grid= (TextView) popView.findViewById(R.id.pop_goodslist_grid);
        pop_goodslist_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLinearMode) {
                    goods_list_rv.setLayoutManager(new GridLayoutManager(GoodsListActivity.this, 2));
                    goods_list_rv.setAdapter(gridAdapter);
                    isLinearMode=!isLinearMode;
                }
                pop.dismiss();
            }
        });
        TextView pop_goodslist_list= (TextView) popView.findViewById(R.id.pop_goodslist_list);
        pop_goodslist_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLinearMode) {
                    goods_list_rv.setLayoutManager(new LinearLayoutManager(GoodsListActivity.this));
                    goods_list_rv.setAdapter(linearAdapter);
                    isLinearMode=!isLinearMode;
                }
                pop.dismiss();
            }
        });
        pop=new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setTouchable(true);
        pop.setOutsideTouchable(true);
    }

    @OnClick({R.id.goods_list_price, R.id.goods_list_sale, R.id.goods_list_screening, R.id.goods_list_changetype})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goods_list_price:
                if (strings.size()==0) {
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
                    strings.add("");
                    strings.add("");
                    strings.add("");
                    if (isLinearMode) {
                        goods_list_rv.setAdapter(linearAdapter);
                    }
                    else {
                        goods_list_rv.setAdapter(gridAdapter);
                    }
                }
                else {
                    if (isLinearMode) {
                        linearAdapter.notifyDataSetChanged();
                    }
                    else {
                        gridAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case R.id.goods_list_sale:
                if (strings.size()==0) {
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
                    strings.add("");
                    strings.add("");
                    strings.add("");
                    if (isLinearMode) {
                        goods_list_rv.setAdapter(linearAdapter);
                    }
                    else {
                        goods_list_rv.setAdapter(gridAdapter);
                    }
                }
                else {
                    if (isLinearMode) {
                        linearAdapter.notifyDataSetChanged();
                    }
                    else {
                        gridAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case R.id.goods_list_screening:
                if (strings.size()==0) {
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
                    strings.add("");
                    strings.add("");
                    strings.add("");
                    if (isLinearMode) {
                        goods_list_rv.setAdapter(linearAdapter);
                    }
                    else {
                        goods_list_rv.setAdapter(gridAdapter);
                    }
                }
                else {
                    if (isLinearMode) {
                        linearAdapter.notifyDataSetChanged();
                    }
                    else {
                        gridAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case R.id.goods_list_changetype:
                if (pop.isShowing()) {
                    pop.dismiss();
                    return;
                }
                pop.showAsDropDown(goods_list_changetype, 0, 0);
        }
    }
}
