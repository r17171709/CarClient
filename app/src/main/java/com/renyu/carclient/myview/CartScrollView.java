package com.renyu.carclient.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by renyu on 15/10/19.
 */
public class CartScrollView extends ScrollView {
    public CartScrollView(Context context) {
        super(context);
    }

    public CartScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    OnScrollChanedListener listener=null;

    public void setOnScrollChanedListener(OnScrollChanedListener listener) {
        this.listener=listener;
    }

    public interface OnScrollChanedListener {
        void onScrollChanged(int t);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        listener.onScrollChanged(t);
    }
}
