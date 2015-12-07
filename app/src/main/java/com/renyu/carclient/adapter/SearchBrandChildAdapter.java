package com.renyu.carclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.activity.search.GoodsListActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by renyu on 15/12/7.
 */
public class SearchBrandChildAdapter extends RecyclerView.Adapter<SearchBrandChildAdapter.SearchBrandChildViewHolder> {

    Context context=null;
    ArrayList<String> models=null;

    public SearchBrandChildAdapter(Context context, ArrayList<String> models) {
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
        holder.searchbrandchild_text.setText(models.get(position));
        holder.searchbrandchild_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, GoodsListActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
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
