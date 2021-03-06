package com.renyu.carclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.activity.search.GoodsListActivity;
import com.renyu.carclient.commons.CommonUtils;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.CategoryModel;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by renyu on 15/12/6.
 */
public class SearchCategoryChildAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final static int PARENT=1;
    final static int CHILD=2;

    Context context=null;
    ArrayList<CategoryModel> models=null;
    OnMenuChoiceListener listener=null;

    int firstCatId=-1;

    public interface OnMenuChoiceListener {
        void openClose(int position);
        void choiceItem(int position);
    }

    public SearchCategoryChildAdapter(Context context, ArrayList<CategoryModel> models, OnMenuChoiceListener listener) {
        this.context=context;
        this.models=models;
        this.listener=listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==PARENT) {
            View view= LayoutInflater.from(context).inflate(R.layout.adapter_searchcategory_child_1, parent, false);
            return new SearchCategoryChild1ViewHolder(view);
        }
        else if (viewType==CHILD) {
            View view= LayoutInflater.from(context).inflate(R.layout.adapter_searchcategory_child_2, parent, false);
            return new SearchCategoryChild2ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position)==PARENT) {
            ((SearchCategoryChild1ViewHolder) holder).adapter_searchcategory_child_1_text.setText(models.get(position).getCat_name());
            ((SearchCategoryChild1ViewHolder) holder).adapter_searchcategory_child_1_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.openClose(position);
                }
            });
            if (models.get(position).isOpen()) {
                ((SearchCategoryChild1ViewHolder) holder).adapter_searchcategory_child_1_image.setImageResource(R.mipmap.ic_arr_down_red);
            }
            else {
                ((SearchCategoryChild1ViewHolder) holder).adapter_searchcategory_child_1_image.setImageResource(R.mipmap.ic_arr_up_red);
            }
        }
        else if (getItemViewType(position)==CHILD) {
            ((SearchCategoryChild2ViewHolder) holder).adapter_searchcategory_child_2_text.setText(models.get(position).getCat_name());
            if (models.get(position).isOpen()) {
                ((SearchCategoryChild2ViewHolder) holder).adapter_searchcategory_child_2_layout.setVisibility(View.VISIBLE);
                ((SearchCategoryChild2ViewHolder) holder).adapter_searchcategory_child_2_layout.setLayoutParams(new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, CommonUtils.dip2px(context, 45)));
            }
            else {
                ((SearchCategoryChild2ViewHolder) holder).adapter_searchcategory_child_2_layout.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
            }
            ((SearchCategoryChild2ViewHolder) holder).adapter_searchcategory_child_2_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, GoodsListActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("type", ParamUtils.CAT);
                    bundle.putInt("cat_id", models.get(position).getCat_id());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    listener.choiceItem(position);
                }
            });
            if (models.get(position).isSelect()) {
                ((SearchCategoryChild2ViewHolder) holder).adapter_searchcategory_child_2_layout.setBackgroundColor(Color.parseColor("#e3e3e3"));
            }
            else {
                ((SearchCategoryChild2ViewHolder) holder).adapter_searchcategory_child_2_layout.setBackgroundColor(Color.WHITE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (models.get(position).getParent_id()==-1) {
            return PARENT;
        }
        else {
            return CHILD;
        }
    }

    public static class SearchCategoryChild1ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.adapter_searchcategory_child_1_layout)
        LinearLayout adapter_searchcategory_child_1_layout;
        @Bind(R.id.adapter_searchcategory_child_1_text)
        TextView adapter_searchcategory_child_1_text;
        @Bind(R.id.adapter_searchcategory_child_1_image)
        ImageView adapter_searchcategory_child_1_image;

        public SearchCategoryChild1ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class SearchCategoryChild2ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.adapter_searchcategory_child_2_layout)
        LinearLayout adapter_searchcategory_child_2_layout;
        @Bind(R.id.adapter_searchcategory_child_2_text)
        TextView adapter_searchcategory_child_2_text;

        public SearchCategoryChild2ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setFirstChoiceCat(int firstCatId) {
        this.firstCatId=firstCatId;
    }
}
