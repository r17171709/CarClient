package com.renyu.carclient.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.commons.CommonUtils;
import com.renyu.carclient.model.CouponModel;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by renyu on 16/3/28.
 */
public class MyCouponAdapter extends RecyclerView.Adapter<MyCouponAdapter.MyCouponHolder> {

    Context context;
    ArrayList<CouponModel> models;

    public MyCouponAdapter(Context context, ArrayList<CouponModel> models) {
        this.context=context;
        this.models=models;
    }

    @Override
    public MyCouponHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.adapter_mycoupon, parent, false);
        return new MyCouponHolder(view);
    }

    @Override
    public void onBindViewHolder(MyCouponHolder holder, int position) {
        holder.mycoupon_money.setText(""+((int) Double.parseDouble(models.get(position).getDeduct_money())));
        holder.mycoupon_title.setText(models.get(position).getCoupon_desc());
        holder.mycoupon_desp.setText("满"+((int) Double.parseDouble(models.get(position).getLimit_money()))+"减"
                +((int) Double.parseDouble(models.get(position).getDeduct_money())));
        holder.mycoupon_time.setText("有效期："+ CommonUtils.getTimeFormat(models.get(position).getCanuse_start_time())+"至"+CommonUtils.getTimeFormat(models.get(position).getCanuse_end_time()));
        if (models.get(position).getIs_valid().equals("1")) {
            holder.mycoupon_state.setText("有效");
            holder.mycoupon_state.setTextColor(Color.RED);
        }
        else {
            holder.mycoupon_state.setText("无效");
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class MyCouponHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.mycoupon_money)
        TextView mycoupon_money;
        @Bind(R.id.mycoupon_title)
        TextView mycoupon_title;
        @Bind(R.id.mycoupon_desp)
        TextView mycoupon_desp;
        @Bind(R.id.mycoupon_time)
        TextView mycoupon_time;
        @Bind(R.id.mycoupon_state)
        TextView mycoupon_state;

        public MyCouponHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
