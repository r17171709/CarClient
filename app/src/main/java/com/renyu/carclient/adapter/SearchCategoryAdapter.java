package com.renyu.carclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.renyu.carclient.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by renyu on 15/10/18.
 */
public class SearchCategoryAdapter extends RecyclerView.Adapter<SearchCategoryAdapter.SearchCategoryHolder> {

    Context context=null;
    ArrayList<String> models=null;
    OnCategoryChoiceListener listener=null;

    public SearchCategoryAdapter(Context context, ArrayList<String> strings, OnCategoryChoiceListener listener) {
        this.context=context;
        this.models=strings;
        this.listener=listener;
    }

    @Override
    public SearchCategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.adapter_searchcategory, parent, false);
        return new SearchCategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchCategoryHolder holder, final int position) {
        holder.searchcategory_parent_text.setText("parent2222");
        holder.searchcategory_parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.choice(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    static class SearchCategoryHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.searchcategory_parent_layout)
        RelativeLayout searchcategory_parent_layout;
        @Bind(R.id.searchcategory_parent_image)
        ImageView searchcategory_parent_image;
        @Bind(R.id.searchcategory_parent_text)
        TextView searchcategory_parent_text;
        @Bind(R.id.searchcategory_parent_arror)
        ImageView searchcategory_parent_arror;

        public SearchCategoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnCategoryChoiceListener {
        void choice(int position);
    }
}
