package com.renyu.carclient.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by renyu on 15/8/15.
 */
public class ProductDetailBottomView extends WebView implements ProductListener {

    Context context=null;
    ViewGroup innerView=null;

    public ProductDetailBottomView(Context context) {
        this(context, null);
    }

    public ProductDetailBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context=context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        innerView= (ViewGroup) getChildAt(0);
    }

    @Override
    public boolean isBottom() {
        return false;
    }

    @Override
    public boolean isTop() {
        if (getScrollY()==0) {
            return true;
        }
        return false;
    }
}
