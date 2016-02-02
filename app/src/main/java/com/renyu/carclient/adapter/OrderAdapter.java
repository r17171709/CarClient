package com.renyu.carclient.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.renyu.carclient.R;
import com.renyu.carclient.activity.order.OrderCenterDetailActivity;
import com.renyu.carclient.commons.ACache;
import com.renyu.carclient.model.OrderModel;
import com.renyu.carclient.model.UserModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by renyu on 15/12/28.
 */
public class OrderAdapter extends BaseAdapter {

    Context context=null;
    ArrayList<OrderModel> models=null;
    boolean isDetail=false;
    boolean isEdit=false;

    UserModel userModel=null;

    DecimalFormat df = null;

    OnReturnListener listener;
    OnCancelListener cancelListener;
    OnReceiveListener receiveListener;
    OnPayListener payListener;

    public interface OnReturnListener {
        void returnValue(OrderModel model, int position);
    }

    public interface OnCancelListener {
        void cancelValue(int position);
    }

    public interface OnReceiveListener {
        void receiveValue(int position);
    }

    public interface OnPayListener {
        void payValue(int position);
    }

    public OrderAdapter(Context context, ArrayList<OrderModel> models, boolean isDetail, boolean isEdit, OnReturnListener listener, OnCancelListener cancelListener, OnReceiveListener receiveListener, OnPayListener payListener) {
        this.context = context;
        this.models = models;
        this.isDetail=isDetail;
        this.listener=listener;
        this.cancelListener=cancelListener;
        this.isEdit=isEdit;
        this.receiveListener=receiveListener;
        this.payListener=payListener;

        df = new DecimalFormat("###.00");

        userModel= ACache.get(context).getAsObject("user")!=null?(UserModel) ACache.get(context).getAsObject("user"):null;
    }

    public void setEdit(boolean isEdit) {
        this.isEdit=isEdit;
        notifyDataSetChanged();
    }

    public boolean isEdit() {
        return isEdit;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int position_=position;

        OrderHolder holder=null;
        if (convertView==null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.adapter_ordercenter_parent, parent, false);
            holder=new OrderHolder();
            holder.ordercenter_tid= (TextView) convertView.findViewById(R.id.ordercenter_tid);
            holder.ordercenter_copy= (TextView) convertView.findViewById(R.id.ordercenter_copy);
            holder.adapter_ordercenter_detail= (LinearLayout) convertView.findViewById(R.id.adapter_ordercenter_detail);
            holder.ordercenter_num= (TextView) convertView.findViewById(R.id.ordercenter_num);
            holder.ordercenter_price= (TextView) convertView.findViewById(R.id.ordercenter_price);
            holder.ordercenter_remarks= (TextView) convertView.findViewById(R.id.ordercenter_remarks);
            holder.adapter_ordercenter_cancel= (TextView) convertView.findViewById(R.id.adapter_ordercenter_cancel);
            holder.ordercenter_state= (TextView) convertView.findViewById(R.id.ordercenter_state);
            convertView.setTag(holder);
        }
        else {
            holder= (OrderHolder) convertView.getTag();
        }
        holder.ordercenter_tid.setText(""+models.get(position).getTid());
        holder.adapter_ordercenter_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (models.get(position_).getStatus().equals("WAIT_CONFRIM")) {
                    cancelListener.cancelValue(position_);
                }
                else if (models.get(position_).getStatus().equals("DELIVER_GOODS")) {
                    cancelListener.cancelValue(position_);
                }
                else if (models.get(position_).getStatus().equals("WAIT_GOODS")) {
                    receiveListener.receiveValue(position_);
                }
                else if (models.get(position_).getStatus().equals("RECEIVE_GOODS")) {
                    payListener.payValue(position_);
                }
                else if (models.get(position_).getStatus().equals("TRADE_FINISHED")) {

                }
                else if (models.get(position_).getStatus().equals("TRADE_CLOSED")) {

                }
                else if (models.get(position_).getStatus().equals("TRADE_CANCEL")) {

                }
                else if (models.get(position_).getStatus().equals("AFTERSALES")) {

                }
            }
        });
        if (isDetail) {
            holder.adapter_ordercenter_cancel.setVisibility(View.GONE);
        }
        else {
            if (models.get(position).getStatus().equals("WAIT_CONFRIM")) {
                holder.ordercenter_state.setText("待确认");
                holder.adapter_ordercenter_cancel.setVisibility(View.VISIBLE);
                holder.adapter_ordercenter_cancel.setText("取消订单");
            }
            else if (models.get(position).getStatus().equals("DELIVER_GOODS")) {
                holder.ordercenter_state.setText("待发货");
                holder.adapter_ordercenter_cancel.setVisibility(View.VISIBLE);
                holder.adapter_ordercenter_cancel.setText("取消订单");
            }
            else if (models.get(position).getStatus().equals("WAIT_GOODS")) {
                holder.ordercenter_state.setText("待收货");
                holder.adapter_ordercenter_cancel.setVisibility(View.VISIBLE);
                holder.adapter_ordercenter_cancel.setText("确认收货");
            }
            else if (models.get(position).getStatus().equals("RECEIVE_GOODS")) {
                holder.ordercenter_state.setText("待付款");
                holder.adapter_ordercenter_cancel.setVisibility(View.VISIBLE);
                holder.adapter_ordercenter_cancel.setText("付款");
            }
            else if (models.get(position).getStatus().equals("TRADE_FINISHED")) {
                holder.ordercenter_state.setText("已完成");
                holder.adapter_ordercenter_cancel.setVisibility(View.GONE);
            }
            else if (models.get(position).getStatus().equals("TRADE_CLOSED")) {
                holder.ordercenter_state.setText("已关闭");
                holder.adapter_ordercenter_cancel.setVisibility(View.GONE);
            }
            else if (models.get(position).getStatus().equals("TRADE_CANCEL")) {
                holder.ordercenter_state.setText("已取消");
                holder.adapter_ordercenter_cancel.setVisibility(View.GONE);
            }
            else if (models.get(position).getStatus().equals("AFTERSALES")) {
                holder.ordercenter_state.setText("退货");
                holder.adapter_ordercenter_cancel.setVisibility(View.GONE);
            }
        }
        holder.ordercenter_num.setText("共"+models.get(position).getItemnum()+"件商品");
        holder.ordercenter_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("value", ""+models.get(position_).getTid());
                cmb.setPrimaryClip(clip);
                Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
            }
        });
        if (Double.parseDouble(models.get(position).getTotal_fee())<1) {
            holder.ordercenter_price.setText(models.get(position).getTotal_fee());
        }
        else {
            holder.ordercenter_price.setText(""+df.format(Double.parseDouble(models.get(position).getTotal_fee())));
        }
        holder.adapter_ordercenter_detail.removeAllViews();
        for (int i=0;i<models.get(position).getOrder().size();i++) {
            final int i_=i;
            View view=LayoutInflater.from(context).inflate(R.layout.adapter_ordercenter_child, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isDetail) {
                        Intent intent=new Intent(context, OrderCenterDetailActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("tid", ""+models.get(position_).getTid());
                        bundle.putString("status", models.get(position_).getStatus());
                        bundle.putBoolean("isEdit", false);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                }
            });
            TextView ordercenter_child_state= (TextView) view.findViewById(R.id.ordercenter_child_state);
            if (models.get(position).getOrder().get(i).getAftersales_status().equals("WAIT_SELLER_AGREE")) {
                ordercenter_child_state.setText("退货中");
            }
            else if (models.get(position).getOrder().get(i).getAftersales_status().equals("WAIT_BUYER_RETURN_GOODS")) {
                ordercenter_child_state.setText("已退货");
            }
            else if (models.get(position).getOrder().get(i).getAftersales_status().equals("SELLER_REFUSE_BUYER")) {
                ordercenter_child_state.setText("已驳回");
            }
            ImageView ordercenter_child_image= (ImageView) view.findViewById(R.id.ordercenter_child_image);
            ImageLoader.getInstance().displayImage(models.get(position).getOrder().get(i).getPic_path(), ordercenter_child_image, getGoodsImageOptions());
            TextView ordercenter_child_title= (TextView) view.findViewById(R.id.ordercenter_child_title);
            ordercenter_child_title.setText(models.get(position).getOrder().get(i).getTitle());
            TextView ordercenter_child_sec_title= (TextView) view.findViewById(R.id.ordercenter_child_sec_title);
            ordercenter_child_sec_title.setText(models.get(position).getOrder().get(i).getSpec_nature_info());
            TextView ordercenter_child_num= (TextView) view.findViewById(R.id.ordercenter_child_num);
            ordercenter_child_num.setText("x" + models.get(position).getOrder().get(i).getNum());
            TextView ordercenter_child_normalprice= (TextView) view.findViewById(R.id.ordercenter_child_normalprice);
            Paint paint=ordercenter_child_normalprice.getPaint();
            paint.setColor(Color.RED);
            paint.setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
            ordercenter_child_normalprice.setPaintFlags(paint.getFlags());
            RelativeLayout ordercenter_child_finalprice_layout= (RelativeLayout) view.findViewById(R.id.ordercenter_child_finalprice_layout);
            if (Double.parseDouble(models.get(position).getOrder().get(i).getOld_price())<1) {
                ordercenter_child_normalprice.setText(models.get(position).getOrder().get(i).getOld_price());
            }
            else {
                ordercenter_child_normalprice.setText("" + df.format(Double.parseDouble(models.get(position).getOrder().get(i).getOld_price())));
            }
            final TextView ordercenter_child_finalprice= (TextView) view.findViewById(R.id.ordercenter_child_finalprice);
            if (Double.parseDouble(models.get(position).getOrder().get(i).getPrice())<1) {
                ordercenter_child_finalprice.setText(models.get(position).getOrder().get(i).getPrice());
            }
            else {
                ordercenter_child_finalprice.setText(""+df.format(Double.parseDouble(models.get(position).getOrder().get(i).getPrice())));
            }
            if (models.get(position).getOrder().get(i).getPrice().equals("-1")) {
                ordercenter_child_finalprice_layout.setVisibility(View.GONE);
            }
            else {
                ordercenter_child_finalprice_layout.setVisibility(View.VISIBLE);
            }
            TextView ordercenter_child_applyreturn= (TextView) view.findViewById(R.id.ordercenter_child_applyreturn);
            ordercenter_child_applyreturn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null) {
                        listener.returnValue(models.get(position_), i_);
                    }
                }
            });
            final CheckBox ordercenter_child_check= (CheckBox) view.findViewById(R.id.ordercenter_child_check);
            ordercenter_child_check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    models.get(position_).getOrder().get(i_).setChecked(ordercenter_child_check.isChecked());
                }
            });
            if (isDetail) {
                ordercenter_child_applyreturn.setVisibility(View.GONE);
                if (isEdit) {
                    ordercenter_child_check.setVisibility(View.VISIBLE);
                }
                else {
                    ordercenter_child_check.setVisibility(View.GONE);
                }
            }
            else {
                ordercenter_child_check.setVisibility(View.GONE);
                if (models.get(position).getStatus().equals("WAIT_CONFRIM")) {
                    ordercenter_child_applyreturn.setVisibility(View.GONE);
                }
                else if (models.get(position).getStatus().equals("DELIVER_GOODS")) {
                    ordercenter_child_applyreturn.setVisibility(View.GONE);
                }
                else if (models.get(position).getStatus().equals("WAIT_GOODS")) {
                    ordercenter_child_applyreturn.setVisibility(View.VISIBLE);
                    ordercenter_child_applyreturn.setText("申请退货");
                }
                else if (models.get(position).getStatus().equals("RECEIVE_GOODS")) {
                    ordercenter_child_applyreturn.setVisibility(View.GONE);
                    ordercenter_child_applyreturn.setVisibility(View.VISIBLE);
                    ordercenter_child_applyreturn.setText("申请退货");
                }
                else if (models.get(position).getStatus().equals("TRADE_FINISHED")) {
                    ordercenter_child_applyreturn.setVisibility(View.GONE);
                }
                else if (models.get(position).getStatus().equals("TRADE_CLOSED")) {
                    ordercenter_child_applyreturn.setVisibility(View.GONE);
                }
                else if (models.get(position).getStatus().equals("TRADE_CANCEL")) {
                    ordercenter_child_applyreturn.setVisibility(View.GONE);
                }
                else if (models.get(position).getStatus().equals("AFTERSALES")) {
                    ordercenter_child_applyreturn.setVisibility(View.GONE);
                }
            }
            holder.adapter_ordercenter_detail.addView(view);
        }
        return convertView;
    }

    public static class OrderHolder {
        TextView ordercenter_tid;
        TextView ordercenter_copy;
        LinearLayout adapter_ordercenter_detail;
        TextView ordercenter_num;
        TextView ordercenter_price;
        TextView ordercenter_remarks;
        TextView adapter_ordercenter_cancel;
        TextView ordercenter_state;
    }

    public DisplayImageOptions getGoodsImageOptions() {
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
