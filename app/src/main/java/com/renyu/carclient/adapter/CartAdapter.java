package com.renyu.carclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.renyu.carclient.R;
import com.renyu.carclient.model.ProductModel;
import com.renyu.carclient.myview.PriceView;

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

    boolean onBind=true;

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
        onBind=true;
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
        ImageLoader.getInstance().displayImage(models.get(position).getImage_default_id(), holder.cart_image, getAvatarDisplayImageOptions());
        holder.cart_title.setText(models.get(position).getTitle());
        holder.cart_num.setText("x"+models.get(position).getQuantity());
        holder.price_layout.setCurrentNum(models.get(position).getQuantity());
        holder.price_layout.setOnTextChangeListener(new PriceView.TextChangeListener() {
            @Override
            public void onTextChange(int num) {
                if (onBind) {
                    return;
                }
                models.get(position).setQuantity(num);
                notifyDataSetChanged();
            }
        });
        holder.cart_normalprice.setText(models.get(position).getPrice());
        onBind=false;
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
        @Bind(R.id.cart_image)
        ImageView cart_image;
        @Bind(R.id.cart_title)
        TextView cart_title;
        @Bind(R.id.cart_num)
        TextView cart_num;
        @Bind(R.id.price_layout)
        PriceView price_layout;
        @Bind(R.id.cart_normalprice)
        TextView cart_normalprice;

        public CartHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
