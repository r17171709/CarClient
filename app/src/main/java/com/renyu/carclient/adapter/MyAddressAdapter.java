package com.renyu.carclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renyu.carclient.R;

import java.util.ArrayList;

/**
 * Created by renyu on 15/12/14.
 */
public class MyAddressAdapter extends RecyclerView.Adapter<MyAddressAdapter.MyAddressHolder> {

    Context context;
    ArrayList<String> models;

    public MyAddressAdapter(Context context, ArrayList<String> models) {
        this.context = context;
        this.models = models;
    }

    @Override
    public MyAddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.adapter_myaddress, parent, false);
        return new MyAddressHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAddressHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class MyAddressHolder extends RecyclerView.ViewHolder {

        public MyAddressHolder(View itemView) {
            super(itemView);
        }
    }
}
