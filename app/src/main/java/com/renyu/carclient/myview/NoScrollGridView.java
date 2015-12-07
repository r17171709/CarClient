package com.renyu.carclient.myview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;
/**
 */
public class NoScrollGridView extends GridView {

    public NoScrollGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public NoScrollGridView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public NoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if(ev.getAction() == MotionEvent.ACTION_MOVE){
            return true;//禁止Gridview进行滑动
        }
        return super.dispatchTouchEvent(ev);
    }
}
