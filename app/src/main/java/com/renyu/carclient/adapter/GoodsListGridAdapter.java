package com.renyu.carclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.renyu.carclient.R;
import com.renyu.carclient.activity.search.GoodsDetailActivity;
import com.renyu.carclient.commons.ACache;
import com.renyu.carclient.model.GoodsListModel;
import com.renyu.carclient.model.UserModel;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by renyu on 15/12/9.
 */
public class GoodsListGridAdapter extends RecyclerView.Adapter<GoodsListGridAdapter.GoodsDetailGridViewHolder> {

    Context context=null;
    ArrayList<GoodsListModel> models=null;

    UserModel userModel=null;

    public GoodsListGridAdapter(Context context, ArrayList<GoodsListModel> models) {
        this.context=context;
        this.models=models;

        userModel= ACache.get(context).getAsObject("user")!=null?(UserModel) ACache.get(context).getAsObject("user"):null;
    }

    @Override
    public GoodsDetailGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.adapter_goodslist_grid, parent, false);
        return new GoodsDetailGridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GoodsDetailGridViewHolder holder, final int position) {
        holder.adapter_goodslist_grid_title.setText(models.get(position).getTitle());
        holder.adapter_goodslist_grid_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, GoodsDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("item_id", ""+models.get(position).getItem_id());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        ImageLoader.getInstance().displayImage(models.get(position).getImage_default_id(), holder.adapter_goodslist_grid_image, getGoodsImageOptions());
        holder.adapter_goodslist_grid_price.setText(""+models.get(position).getPrice());
        holder.adapter_goodslist_grid_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        if (userModel==null) {
            holder.adapter_goodslist_grid_discountdesp.setText("登录享受优惠");
            holder.adapter_goodslist_grid_discountdesp.setVisibility(View.VISIBLE);
        }
        else {
            holder.adapter_goodslist_grid_discountdesp.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
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

    public DisplayImageOptions getGoodsImageOptions() {
        return new DisplayImageOptions.Builder()
                .showImageOnFail(R.mipmap.ic_launcher)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }
}
