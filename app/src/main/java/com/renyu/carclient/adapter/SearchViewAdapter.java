package com.renyu.carclient.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.model.CategoryModel;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by renyu on 15/12/28.
 */
public class SearchViewAdapter extends RecyclerView.Adapter<SearchViewAdapter.SearchViewHolder> {

    Context context=null;
    ArrayList<CategoryModel> models=null;
    OnItemClickListener listener=null;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public SearchViewAdapter(Context context, ArrayList<CategoryModel> models, OnItemClickListener listener) {
        this.context = context;
        this.models = models;
        this.listener=listener;
    }

    @Override
    public SearchViewAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.adapter_searchview, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewAdapter.SearchViewHolder holder, final int position) {
        holder.adapter_searchview_title.setText(models.get(position).getCat_name());
        holder.adapter_searchview_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
                for (int i=0;i<models.size();i++) {
                    models.get(i).setOpen(false);
                }
                models.get(position).setOpen(true);
                notifyDataSetChanged();
            }
        });
        if (models.get(position).isOpen()) {
            holder.adapter_searchview_layout.setBackgroundColor(Color.parseColor("#ececec"));
        }
        else {
            holder.adapter_searchview_layout.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.adapter_searchview_layout)
        RelativeLayout adapter_searchview_layout;
        @Bind(R.id.adapter_searchview_title)
        TextView adapter_searchview_title;

        public SearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
