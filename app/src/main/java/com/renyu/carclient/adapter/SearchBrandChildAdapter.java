package com.renyu.carclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.activity.search.GoodsListActivity;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.SearchBrandModel;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by renyu on 15/12/7.
 */
public class SearchBrandChildAdapter extends RecyclerView.Adapter<SearchBrandChildAdapter.SearchBrandChildViewHolder> {

    Context context=null;
    ArrayList<SearchBrandModel> models=null;

    int brand_id;

    public SearchBrandChildAdapter(Context context, ArrayList<SearchBrandModel> models) {
        this.context=context;
        this.models=models;
    }

    @Override
    public SearchBrandChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.adapter_searchbrand_grandchild, parent, false);
        return new SearchBrandChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchBrandChildViewHolder holder, final int position) {
        holder.searchbrandchild_text.setText(models.get(position).getCat_name());
        holder.searchbrandchild_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, GoodsListActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("type", ParamUtils.BRAND);
                bundle.putInt("cat_id", Integer.parseInt(models.get(position).getCat_id()));
                bundle.putInt("brand_id", brand_id);
                intent.putExtras(bundle);
                context.startActivity(intent);

                for (int i=0;i<models.size();i++) {
                    models.get(i).setSelect(false);
                }
                models.get(position).setSelect(true);
                notifyDataSetChanged();
            }
        });
        if (models.get(position).isSelect()) {
            holder.searchbrandchild_layout.setBackgroundColor(Color.parseColor("#e3e3e3"));
        }
        else {
            holder.searchbrandchild_layout.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void setParentId(int brand_id) {
        this.brand_id=brand_id;
    }

    public static class SearchBrandChildViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.searchbrandchild_layout)
        LinearLayout searchbrandchild_layout;
        @Bind(R.id.searchbrandchild_text)
        TextView searchbrandchild_text;

        public SearchBrandChildViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
