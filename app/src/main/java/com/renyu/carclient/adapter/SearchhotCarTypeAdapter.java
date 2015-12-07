package com.renyu.carclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.renyu.carclient.R;

import java.util.ArrayList;

/**
 * Created by renyu on 15/12/1.
 */
public class SearchhotCarTypeAdapter extends BaseAdapter {

    Context context=null;
    ArrayList<String> models=null;

    public SearchhotCarTypeAdapter(Context context, ArrayList<String> models) {
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
        SearchhotCarTypeHolder holder=null;
        if (convertView==null) {
            holder=new SearchhotCarTypeHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.adapter_searchhotcategory, parent, false);
            convertView.setTag(holder);
        }
        else {
            holder= (SearchhotCarTypeHolder) convertView.getTag();
        }
        return convertView;
    }

    static class SearchhotCarTypeHolder {

    }
}
