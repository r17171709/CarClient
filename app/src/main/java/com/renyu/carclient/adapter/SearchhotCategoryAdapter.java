package com.renyu.carclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.renyu.carclient.R;

import java.util.ArrayList;

/**
 * Created by renyu on 15/10/21.
 */
public class SearchhotCategoryAdapter extends BaseAdapter {

    Context context=null;
    ArrayList<String> models=null;

    public SearchhotCategoryAdapter(Context context, ArrayList<String> models) {
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
        SearchhotCategoryHolder holder=null;
        if (convertView==null) {
            holder=new SearchhotCategoryHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.adapter_searchhotcategory, parent, false);
            convertView.setTag(holder);
        }
        else {
            holder= (SearchhotCategoryHolder) convertView.getTag();
        }
        return convertView;
    }

    static class SearchhotCategoryHolder {

    }
}
