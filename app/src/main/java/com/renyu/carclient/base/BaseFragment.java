package com.renyu.carclient.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by renyu on 15/10/18.
 */
public abstract class BaseFragment extends Fragment {

    View contentView=null;

    public abstract int initContentView();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (contentView==null) {
            contentView=inflater.inflate(initContentView(), container, false);
            ButterKnife.bind(this, contentView);
        }
        ViewGroup parent= (ViewGroup) contentView.getParent();
        if (parent!=null) {
            parent.removeView(contentView);
        }
        return contentView;
    }
}
