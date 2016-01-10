package com.renyu.carclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.renyu.carclient.R;
import com.renyu.carclient.activity.search.GoodsDetailActivity;
import com.renyu.carclient.model.CollectionModel;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by renyu on 15/12/12.
 */
public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionHolder> {

    Context context=null;
    ArrayList<CollectionModel> models;

    public CollectionAdapter(Context context, ArrayList<CollectionModel> models) {
        this.context = context;
        this.models = models;
    }

    @Override
    public CollectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.adapter_collection, parent, false);
        return new CollectionHolder(view);
    }

    @Override
    public void onBindViewHolder(CollectionHolder holder, int position) {
        final int position_=position;
        if (models.get(position).isFlag()) {
            holder.adapter_collection_delete.setVisibility(View.VISIBLE);
        }
        else {
            holder.adapter_collection_delete.setVisibility(View.GONE);
        }
        ImageLoader.getInstance().displayImage(models.get(position).getImage_default_id(), holder.adapter_collection_image, getAvatarDisplayImageOptions());
        holder.adapter_collection_title.setText(models.get(position).getGoods_name());
        holder.adapter_collection_price.setText("￥"+models.get(position).getGoods_price());
        holder.adapter_collection_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.adapter_collection_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, GoodsDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("item_id", ""+models.get(position_).getItem_id());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class CollectionHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.adapter_collection_delete)
        TextView adapter_collection_delete;
        @Bind(R.id.adapter_collection_image)
        ImageView adapter_collection_image;
        @Bind(R.id.adapter_collection_title)
        TextView adapter_collection_title;
        @Bind(R.id.adapter_collection_price)
        TextView adapter_collection_price;
        @Bind(R.id.adapter_collection_layout)
        RelativeLayout adapter_collection_layout;

        public CollectionHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public DisplayImageOptions getAvatarDisplayImageOptions() {
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
