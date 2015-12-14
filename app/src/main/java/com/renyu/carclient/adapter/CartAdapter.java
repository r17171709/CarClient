package com.renyu.carclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.renyu.carclient.R;
import com.renyu.carclient.model.ProductModel;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by renyu on 15/12/13.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {

    Context context=null;
    ArrayList<ProductModel> models=null;
    OnCheckChangedListener listener=null;

    public interface OnCheckChangedListener {
        void OnCheckChanged(int position, boolean flag);
    }

    public CartAdapter(Context context, ArrayList<ProductModel> models, OnCheckChangedListener listener) {
        this.context = context;
        this.models = models;
        this.listener=listener;
    }

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.adapter_cart, parent, false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartHolder holder, final int position) {
        holder.cart_checkbox.setChecked(models.get(position).isChecked());
        if (models.get(position).isEdit()) {
            holder.cart_productnum_layout.setVisibility(View.VISIBLE);
            holder.cart_price_layout.setVisibility(View.GONE);
            holder.cart_goods_layout.setVisibility(View.GONE);
        }
        else {
            holder.cart_productnum_layout.setVisibility(View.GONE);
            holder.cart_price_layout.setVisibility(View.VISIBLE);
            holder.cart_goods_layout.setVisibility(View.VISIBLE);
        }
        if (models.get(position).isChecked()) {
            holder.cart_checkbox.setChecked(true);
        }
        else {
            holder.cart_checkbox.setChecked(false);
        }
        holder.cart_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.cart_checkbox.setChecked(!models.get(position).isChecked());
                models.get(position).setChecked(!models.get(position).isChecked());
                listener.OnCheckChanged(position, !models.get(position).isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.cart_checkbox)
        CheckBox cart_checkbox;
        @Bind(R.id.cart_productnum_layout)
        RelativeLayout cart_productnum_layout;
        @Bind(R.id.cart_price_layout)
        LinearLayout cart_price_layout;
        @Bind(R.id.cart_goods_layout)
        LinearLayout cart_goods_layout;

        public CartHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
