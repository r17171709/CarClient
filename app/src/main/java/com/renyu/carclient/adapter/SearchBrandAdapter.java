package com.renyu.carclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.model.SearchBrandModel;

import java.util.ArrayList;

/**
 * Created by renyu on 15/10/18.
 */
public class SearchBrandAdapter extends RecyclerView.Adapter<SearchBrandAdapter.SearchBrandHolder> {

    final static int TITLE=1;
    final static int ITEM_CHILD=2;

    public interface OnOperationListener {
        void positionChoice(int position);
    }

    Context context=null;
    ArrayList<Object> models=null;
    OnOperationListener listener=null;

    public SearchBrandAdapter(Context context, ArrayList<Object> models, OnOperationListener listener) {
        this.context=context;
        this.models=models;
        this.listener=listener;
    }

    @Override
    public SearchBrandHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==TITLE) {
            View view= LayoutInflater.from(context).inflate(R.layout.adapter_searchbrand_parent, parent, false);
            return new SearchBrandHolder(view);
        }
        else if (viewType==ITEM_CHILD) {
            View view= LayoutInflater.from(context).inflate(R.layout.adapter_searchbrand_child, parent, false);
            return new SearchBrandHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(SearchBrandHolder holder, int position) {
        final int position_=position;
        if (getItemViewType(position)==TITLE) {
            holder.searchbrand_parent_text.setText(models.get(position).toString().toUpperCase());
        }
        else if (getItemViewType(position)==ITEM_CHILD) {
            holder.searchbrand_title.setText(((SearchBrandModel) models.get(position)).getTitle());
            holder.searchbrand_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.positionChoice(position_);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (models.get(position) instanceof String) {
            return TITLE;
        }
        else if (models.get(position) instanceof SearchBrandModel) {
            return ITEM_CHILD;
        }
        return super.getItemViewType(position);
    }

    static class SearchBrandHolder extends RecyclerView.ViewHolder {

        TextView searchbrand_parent_text=null;

        ImageView searchbrand_image=null;
        TextView searchbrand_title=null;
        LinearLayout searchbrand_layout=null;
        ImageView searchbrand_arror;

        public SearchBrandHolder(View itemView) {
            super(itemView);

            searchbrand_parent_text= (TextView) itemView.findViewById(R.id.searchbrand_parent_text);

            searchbrand_image= (ImageView) itemView.findViewById(R.id.searchbrand_image);
            searchbrand_title= (TextView) itemView.findViewById(R.id.searchbrand_title);
            searchbrand_layout= (LinearLayout) itemView.findViewById(R.id.searchbrand_layout);
            searchbrand_arror= (ImageView) itemView.findViewById(R.id.searchbrand_arror);
        }
    }
}
