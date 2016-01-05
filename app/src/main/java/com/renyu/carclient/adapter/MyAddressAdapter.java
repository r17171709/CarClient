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
import com.renyu.carclient.activity.my.MyAddressAddActivity;
import com.renyu.carclient.commons.CommonUtils;
import com.renyu.carclient.model.AddressModel;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by renyu on 15/12/14.
 */
public class MyAddressAdapter extends RecyclerView.Adapter<MyAddressAdapter.MyAddressHolder> {

    Context context;
    ArrayList<AddressModel> models;
    OnItemClickListener listener;

    public interface OnItemClickListener {
        void itemClick(int position);
    }

    public MyAddressAdapter(Context context, ArrayList<AddressModel> models, OnItemClickListener listener) {
        this.context = context;
        this.models = models;
        this.listener = listener;
    }

    @Override
    public MyAddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.adapter_myaddress, parent, false);
        return new MyAddressHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAddressHolder holder, final int position) {
        holder.myaddress_name.setText(models.get(position).getName());
        holder.myaddress_mobile.setText(models.get(position).getMobile());
        if (models.get(position).getDef_addr()==1) {
            holder.myaddress_default.setVisibility(View.VISIBLE);
        }
        else {
            holder.myaddress_default.setVisibility(View.INVISIBLE);
        }
        String address="";
        for (int i=0;i<models.get(position).getArea().split("/").length;i++) {
            address+= CommonUtils.getCityInfo(models.get(position).getArea().split("/")[i])+" ";
        }
        address+=models.get(position).getAddr();
        holder.myaddress_address.setText(address);
        holder.myaddress_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class MyAddressHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.myaddress_name)
        TextView myaddress_name;
        @Bind(R.id.myaddress_mobile)
        TextView myaddress_mobile;
        @Bind(R.id.myaddress_default)
        TextView myaddress_default;
        @Bind(R.id.myaddress_address)
        TextView myaddress_address;
        @Bind(R.id.myaddress_layout)
        LinearLayout myaddress_layout;

        public MyAddressHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
