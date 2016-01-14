package com.renyu.carclient.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.renyu.carclient.model.SearchCarDetalModel;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by renyu on 15/12/7.
 */
public class SearchCarTypeDetailAdapter extends RecyclerView.Adapter<SearchCarTypeDetailAdapter.SearchCarTypeDetailViewHolder> {

    Context context=null;
    ArrayList<SearchCarDetalModel> models=null;

    public SearchCarTypeDetailAdapter(Context context, ArrayList<SearchCarDetalModel> models) {
        this.context = context;
        this.models = models;
    }

    @Override
    public SearchCarTypeDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.adapter_searchcartypedetail, parent, false);
        return new SearchCarTypeDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchCarTypeDetailViewHolder holder, final int position) {
        holder.adpter_searchcartypedetail_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, GoodsListActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("type", ParamUtils.CAR);
                bundle.putString("nlevelid", models.get(position).getNlevelid());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        holder.adpter_searchcartypedetail_text.setText(models.get(position).getSales_name());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class SearchCarTypeDetailViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.adpter_searchcartypedetail_layout)
        LinearLayout adpter_searchcartypedetail_layout;
        @Bind(R.id.adpter_searchcartypedetail_text)
        TextView adpter_searchcartypedetail_text;

        public SearchCarTypeDetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
