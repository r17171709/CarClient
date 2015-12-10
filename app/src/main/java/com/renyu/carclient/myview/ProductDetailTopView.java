package com.renyu.carclient.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * Created by renyu on 15/8/15.
 */
public class ProductDetailTopView extends ScrollView implements ProductListener {

    ViewGroup innerView=null;
    Context context=null;

    public ProductDetailTopView(Context context) {
        this(context, null);
    }

    public ProductDetailTopView(Context context, AttributeSet attrs) {
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int count=getChildCount();
        for (int i=0;i<count;i++) {
            View view=getChildAt(i);
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        innerView.layout(0, 0, innerView.getMeasuredWidth(), innerView.getMeasuredHeight());
    }

    @Override
    public boolean isBottom() {
        System.out.println(getMeasuredHeight()+" "+getScrollY()+" "+innerView.getMeasuredHeight());
        if (getMeasuredHeight()+getScrollY()==innerView.getMeasuredHeight()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isTop() {
        return false;
    }
}
