package com.renyu.carclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.activity.search.SearchCarTypeDetailActivity;
import com.renyu.carclient.commons.CommonUtils;
import com.renyu.carclient.model.SearchCarTypeChildModel;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by renyu on 15/12/7.
 */
public class SearchCarTypeChildAdapter extends RecyclerView.Adapter {

    final static int PARENT=1;
    final static int CHILD=2;

    Context context=null;
    ArrayList<Object> models=null;
    OnMenuChoiceListener listener=null;

    public interface OnMenuChoiceListener {
        void openClose(int position);
    }

    public SearchCarTypeChildAdapter(Context context, ArrayList<Object> models, OnMenuChoiceListener listener) {
        this.context = context;
        this.models = models;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == PARENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.adapter_searchcartypechild_1, parent, false);
            return new SearchCarTypeChild1ViewHolder(view);
        } else if (viewType == CHILD) {
            View view = LayoutInflater.from(context).inflate(R.layout.adapter_searchcartypechild_2, parent, false);
            return new SearchCarTypeChild2ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final int position_=position;
        if (getItemViewType(position)==PARENT) {
            ((SearchCarTypeChild1ViewHolder) holder).adapter_searchcartype_child_1_text.setText(models.get(position).toString());
            ((SearchCarTypeChild1ViewHolder) holder).adapter_searchcartype_child_1_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.openClose(position_);
                }
            });
            if (((SearchCarTypeChildModel) (models.get(position+1))).isOpen()) {
                ((SearchCarTypeChild1ViewHolder) holder).adapter_searchcartype_child_1_image.setImageResource(R.mipmap.ic_arr_down_red);
            }
            else {
                ((SearchCarTypeChild1ViewHolder) holder).adapter_searchcartype_child_1_image.setImageResource(R.mipmap.ic_arr_up_red);
            }
        }
        else if (getItemViewType(position)==CHILD) {
            ((SearchCarTypeChild2ViewHolder) holder).adapter_searchcartype_child_2_text.setText(((SearchCarTypeChildModel) (models.get(position))).getModels());
            if (((SearchCarTypeChildModel) (models.get(position))).isOpen()) {
                ((SearchCarTypeChild2ViewHolder) holder).adapter_searchcartype_child_2_layout.setVisibility(View.VISIBLE);
                ((SearchCarTypeChild2ViewHolder) holder).adapter_searchcartype_child_2_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, CommonUtils.dip2px(context, 44)));
            }
            else {
                ((SearchCarTypeChild2ViewHolder) holder).adapter_searchcartype_child_2_layout.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
            }
            ((SearchCarTypeChild2ViewHolder) holder).adapter_searchcartype_child_2_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, SearchCarTypeDetailActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("brand", ((SearchCarTypeChildModel) (models.get(position))).getBrand());
                    bundle.putString("models", ((SearchCarTypeChildModel) (models.get(position))).getModels());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
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
            return PARENT;
        }
        else {
            return CHILD;
        }
    }

    public static class SearchCarTypeChild1ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.adapter_searchcartype_child_1_layout)
        LinearLayout adapter_searchcartype_child_1_layout;
        @Bind(R.id.adapter_searchcartype_child_1_text)
        TextView adapter_searchcartype_child_1_text;
        @Bind(R.id.adapter_searchcartype_child_1_image)
        ImageView adapter_searchcartype_child_1_image;

        public SearchCarTypeChild1ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class SearchCarTypeChild2ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.adapter_searchcartype_child_2_layout)
        LinearLayout adapter_searchcartype_child_2_layout;
        @Bind(R.id.adapter_searchcartype_child_2_text)
        TextView adapter_searchcartype_child_2_text;

        public SearchCarTypeChild2ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
