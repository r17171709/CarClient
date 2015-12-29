package com.renyu.carclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.renyu.carclient.R;
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
        if (models.get(position).isFlag()) {
            holder.adapter_collection_delete.setVisibility(View.VISIBLE);
        }
        else {
            holder.adapter_collection_delete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class CollectionHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.adapter_collection_delete)
        TextView adapter_collection_delete;

        public CollectionHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
