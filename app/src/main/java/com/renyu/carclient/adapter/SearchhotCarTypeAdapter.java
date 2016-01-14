package com.renyu.carclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.renyu.carclient.R;
import com.renyu.carclient.activity.search.GoodsListSearchActivity;
import com.renyu.carclient.model.HotCarModel;

import java.util.ArrayList;

/**
 * Created by renyu on 15/12/1.
 */
public class SearchhotCarTypeAdapter extends BaseAdapter {

    Context context=null;
    ArrayList<HotCarModel> models=null;

    public SearchhotCarTypeAdapter(Context context, ArrayList<HotCarModel> models) {
        this.context=context;
        this.models=models;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SearchhotCarTypeHolder holder=null;
        if (convertView==null) {
            holder=new SearchhotCarTypeHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.adapter_searchhotcategory, parent, false);
            holder.searchhotcategory_image= (ImageView) convertView.findViewById(R.id.searchhotcategory_image);
            holder.searchhotcategory_text= (TextView) convertView.findViewById(R.id.searchhotcategory_text);
            holder.searchhotcategory_layout= (LinearLayout) convertView.findViewById(R.id.searchhotcategory_layout);
            convertView.setTag(holder);
        }
        else {
            holder= (SearchhotCarTypeHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(models.get(position).getCar_logo(), holder.searchhotcategory_image, getHotImageOptions());
        holder.searchhotcategory_text.setText(models.get(position).getCar_name());
        holder.searchhotcategory_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, GoodsListSearchActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("keyWords", models.get(position).getCar_name());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    static class SearchhotCarTypeHolder {
        ImageView searchhotcategory_image;
        TextView searchhotcategory_text;
        LinearLayout searchhotcategory_layout;
    }

    public DisplayImageOptions getHotImageOptions() {
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
