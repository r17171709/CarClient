package com.renyu.carclient.adapter;

import android.content.Context;
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
import com.renyu.carclient.model.CategoryModel;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by renyu on 15/10/18.
 */
public class SearchCategoryAdapter extends RecyclerView.Adapter<SearchCategoryAdapter.SearchCategoryHolder> {

    Context context=null;
    ArrayList<CategoryModel> models=null;
    OnCategoryChoiceListener listener=null;

    public SearchCategoryAdapter(Context context, ArrayList<CategoryModel> models, OnCategoryChoiceListener listener) {
        this.context=context;
        this.models=models;
        this.listener=listener;
    }

    @Override
    public SearchCategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.adapter_searchcategory, parent, false);
        return new SearchCategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchCategoryHolder holder, final int position) {
        holder.searchcategory_parent_text.setText(models.get(position).getCat_name());
        holder.searchcategory_parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.choice(position);
            }
        });
        ImageLoader.getInstance().displayImage(models.get(position).getCat_logo(), holder.searchcategory_parent_image, getCategoryImageOptions());
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


    public DisplayImageOptions getCategoryImageOptions() {
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
