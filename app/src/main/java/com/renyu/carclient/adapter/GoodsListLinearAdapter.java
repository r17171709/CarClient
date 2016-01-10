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
 * Created by renyu on 15/10/18.
 */
public class GoodsListLinearAdapter extends RecyclerView.Adapter<GoodsListLinearAdapter.GoodsLinearHolder> {

    Context context=null;
    ArrayList<GoodsListModel> models=null;

    UserModel userModel=null;

    public GoodsListLinearAdapter(Context context, ArrayList<GoodsListModel> models) {
        this.context=context;
        this.models=models;

        userModel= ACache.get(context).getAsObject("user")!=null?(UserModel) ACache.get(context).getAsObject("user"):null;
    }

    @Override
    public GoodsLinearHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.adapter_goodslist_linear, parent, false);
        return new GoodsLinearHolder(view);
    }

    @Override
    public void onBindViewHolder(GoodsLinearHolder holder, final int position) {
        holder.adapter_goodslist_linear_title.setText(models.get(position).getTitle());
        holder.adapter_goodslist_linear_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, GoodsDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("item_id", ""+models.get(position).getItem_id());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        ImageLoader.getInstance().displayImage(models.get(position).getImage_default_id(), holder.adapter_goodslist_linear_image, getGoodsImageOptions());
        holder.adapter_goodslist_linear_price.setText(""+models.get(position).getPrice());
        holder.adapter_goodslist_linear_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        if (userModel==null) {
            holder.adapter_goodslist_linear_discountdesp.setVisibility(View.VISIBLE);
            holder.adapter_goodslist_linear_discountdesp.setText("登录享受优惠");
        }
        else {
            holder.adapter_goodslist_linear_discountdesp.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
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
        LinearLayout adapter_goodslist_linear_layout;

        public GoodsLinearHolder(View itemView) {
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
