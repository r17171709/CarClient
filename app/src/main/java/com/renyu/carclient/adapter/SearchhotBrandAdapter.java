package com.renyu.carclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.renyu.carclient.R;

import java.util.ArrayList;

/**
 * Created by renyu on 15/10/27.
 */
public class SearchhotBrandAdapter  extends BaseAdapter {

    Context context=null;
    ArrayList<String> models=null;

    public SearchhotBrandAdapter(Context context, ArrayList<String> models) {
        this.context=context;
        this.models=models;
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
        SearchhotBrandHolder holder=null;
        if (convertView==null) {
            holder=new SearchhotBrandHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.adapter_searchhotbrand, parent, false);
            convertView.setTag(holder);
        }
        else {
            holder= (SearchhotBrandHolder) convertView.getTag();
        }
        return convertView;
    }

    static class SearchhotBrandHolder {

    }
}
