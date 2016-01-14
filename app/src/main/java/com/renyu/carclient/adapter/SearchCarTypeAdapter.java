package com.renyu.carclient.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayout;
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
import com.renyu.carclient.commons.CommonUtils;
import com.renyu.carclient.model.SearchCarTypeHeadModel;
import com.renyu.carclient.model.SearchCarTypeModel;

import java.util.ArrayList;

/**
 * Created by renyu on 15/12/7.
 */
public class SearchCarTypeAdapter extends RecyclerView.Adapter<SearchCarTypeAdapter.SearchCarTypeViewHolder> {

    final static int TITLE=1;
    final static int ITEM_CHILD=2;
    final static int ITEM_HEAD=3;

    Context context=null;
    ArrayList<Object> models=null;
    OnOperationListener listener=null;

    public interface OnOperationListener {
        void positionChoice(String brand);
    }

    public SearchCarTypeAdapter(Context context, ArrayList<Object> models, OnOperationListener listener) {
        this.context = context;
        this.models = models;
        this.listener = listener;
    }

    @Override
    public SearchCarTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==TITLE) {
            View view= LayoutInflater.from(context).inflate(R.layout.adapter_searchbrand_parent, parent, false);
            return new SearchCarTypeViewHolder(view);
        }
        else if (viewType==ITEM_CHILD) {
            View view= LayoutInflater.from(context).inflate(R.layout.adapter_searchcartype, parent, false);
            return new SearchCarTypeViewHolder(view);
        }
        else if (viewType==ITEM_HEAD) {
            View view= LayoutInflater.from(context).inflate(R.layout.headview_searchcartype, parent, false);
            return new SearchCarTypeViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(SearchCarTypeViewHolder holder, final int position) {
        if (getItemViewType(position)==TITLE) {
            holder.searchbrand_parent_text.setText(models.get(position).toString().toUpperCase());
        }
        else if (getItemViewType(position)==ITEM_CHILD) {
            holder.adapter_searchcartype_text.setText(((SearchCarTypeModel) models.get(position)).getBrand());
            ImageLoader.getInstance().displayImage(((SearchCarTypeModel) models.get(position)).getBrand_img(), holder.adapter_searchcartype_image, getAvatarDisplayImageOptions());
            holder.adapter_searchcartype_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.positionChoice(((SearchCarTypeModel) models.get(position)).getBrand());
                }
            });
        }
        else if (getItemViewType(position)==ITEM_HEAD) {
            holder.headview_searchcartype_gl.removeAllViews();
            for (int i=0;i<((SearchCarTypeHeadModel) models.get(position)).getLists().size();i++) {
                final int i_=i;
                View view=LayoutInflater.from(context).inflate(R.layout.headview_searchcartype_item, null, false);
                LinearLayout headview_searchcartype_item_layout= (LinearLayout) view.findViewById(R.id.headview_searchcartype_item_layout);
                headview_searchcartype_item_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.positionChoice(((SearchCarTypeHeadModel) models.get(position)).getLists().get(i_).getBrand());
                    }
                });
                headview_searchcartype_item_layout.setLayoutParams(new ViewGroup.LayoutParams((CommonUtils.getScreenWidth(context)-CommonUtils.dip2px(context, 32))/4, CommonUtils.dip2px(context, 100)));
                ImageView headview_searchcartype_item_image= (ImageView) view.findViewById(R.id.headview_searchcartype_item_image);
                TextView headview_searchcartype_item_text= (TextView) view.findViewById(R.id.headview_searchcartype_item_text);
                headview_searchcartype_item_text.setText(((SearchCarTypeHeadModel) models.get(position)).getLists().get(i).getBrand());
                ImageLoader.getInstance().displayImage(((SearchCarTypeHeadModel) models.get(position)).getLists().get(i).getBrand_img(), headview_searchcartype_item_image, getAvatarDisplayImageOptions());
                holder.headview_searchcartype_gl.addView(view);
            }
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
        else if (models.get(position) instanceof SearchCarTypeModel) {
            return ITEM_CHILD;
        }
        else if (models.get(position) instanceof SearchCarTypeHeadModel) {
            return ITEM_HEAD;
        }
        return super.getItemViewType(position);
    }

    public static class SearchCarTypeViewHolder extends RecyclerView.ViewHolder {

        TextView searchbrand_parent_text=null;

        LinearLayout adapter_searchcartype_layout;
        ImageView adapter_searchcartype_image;
        TextView adapter_searchcartype_text;

        GridLayout headview_searchcartype_gl;

        public SearchCarTypeViewHolder(View itemView) {
            super(itemView);

            searchbrand_parent_text= (TextView) itemView.findViewById(R.id.searchbrand_parent_text);

            adapter_searchcartype_layout= (LinearLayout) itemView.findViewById(R.id.adapter_searchcartype_layout);
            adapter_searchcartype_image= (ImageView) itemView.findViewById(R.id.adapter_searchcartype_image);
            adapter_searchcartype_text= (TextView) itemView.findViewById(R.id.adapter_searchcartype_text);

            headview_searchcartype_gl= (GridLayout) itemView.findViewById(R.id.headview_searchcartype_gl);
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
