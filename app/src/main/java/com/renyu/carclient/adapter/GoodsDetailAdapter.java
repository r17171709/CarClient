package com.renyu.carclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.renyu.carclient.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by renyu on 15/10/18.
 */
public class GoodsDetailAdapter extends RecyclerView.Adapter<GoodsDetailAdapter.GoodsDetailHolder> {

    Context context=null;
    ArrayList<String> strings=null;

    public GoodsDetailAdapter(Context context, ArrayList<String> strings) {
        this.context=context;
        this.strings=strings;
    }

    @Override
    public GoodsDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.adapter_goodsdetail, parent, false);
        return new GoodsDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(GoodsDetailHolder holder, int position) {
        holder.goods_detail_title.setText(strings.get(position));
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    static class GoodsDetailHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.goods_detail_image)
        ImageView goods_detail_image;
        @Bind(R.id.goods_detail_title)
        TextView goods_detail_title;
        @Bind(R.id.goods_detail_code)
        TextView goods_detail_code;
        @Bind(R.id.goods_detail_discountdesp)
        TextView goods_detail_discountdesp;
        @Bind(R.id.goods_detail_price)
        TextView goods_detail_price;
        @Bind(R.id.goods_detail_salenum)
        TextView goods_detail_salenum;

        public GoodsDetailHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
