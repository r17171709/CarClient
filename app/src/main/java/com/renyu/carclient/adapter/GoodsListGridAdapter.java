package com.renyu.carclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.activity.search.GoodsDetailActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by renyu on 15/12/9.
 */
public class GoodsListGridAdapter extends RecyclerView.Adapter<GoodsListGridAdapter.GoodsDetailGridViewHolder> {

    Context context=null;
    ArrayList<String> strings=null;

    public GoodsListGridAdapter(Context context, ArrayList<String> strings) {
        this.context=context;
        this.strings=strings;
    }

    @Override
    public GoodsDetailGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.adapter_goodslist_grid, parent, false);
        return new GoodsDetailGridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GoodsDetailGridViewHolder holder, int position) {
        holder.adapter_goodslist_grid_title.setText(strings.get(position));
        holder.adapter_goodslist_grid_layout.setOnClickListener(new View.OnClickListener() {
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

    public static class GoodsDetailGridViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.adapter_goodslist_grid_image)
        ImageView adapter_goodslist_grid_image;
        @Bind(R.id.adapter_goodslist_grid_title)
        TextView adapter_goodslist_grid_title;
        @Bind(R.id.adapter_goodslist_grid_discountdesp)
        TextView adapter_goodslist_grid_discountdesp;
        @Bind(R.id.adapter_goodslist_grid_price)
        TextView adapter_goodslist_grid_price;
        @Bind(R.id.adapter_goodslist_grid_layout)
        LinearLayout adapter_goodslist_grid_layout;

        public GoodsDetailGridViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
