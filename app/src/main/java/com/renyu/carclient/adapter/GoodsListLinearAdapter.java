package com.renyu.carclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.activity.search.GoodsDetailActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by renyu on 15/10/18.
 */
public class GoodsListLinearAdapter extends RecyclerView.Adapter<GoodsListLinearAdapter.GoodsLinearHolder> {

    Context context=null;
    ArrayList<String> strings=null;

    public GoodsListLinearAdapter(Context context, ArrayList<String> strings) {
        this.context=context;
        this.strings=strings;
    }

    @Override
    public GoodsLinearHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.adapter_goodslist_linear, parent, false);
        return new GoodsLinearHolder(view);
    }

    @Override
    public void onBindViewHolder(GoodsLinearHolder holder, int position) {
        holder.adapter_goodslist_linear_title.setText(strings.get(position));
        holder.adapter_goodslist_linear_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, GoodsDetailActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    static class GoodsLinearHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.adapter_goodslist_linear_image)
        ImageView adapter_goodslist_linear_image;
        @Bind(R.id.adapter_goodslist_linear_title)
        TextView adapter_goodslist_linear_title;
        @Bind(R.id.adapter_goodslist_linear_discountdesp)
        TextView adapter_goodslist_linear_discountdesp;
        @Bind(R.id.adapter_goodslist_linear_price)
        TextView adapter_goodslist_linear_price;
        @Bind(R.id.adapter_goodslist_linear_layout)
        RelativeLayout adapter_goodslist_linear_layout;

        public GoodsLinearHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
