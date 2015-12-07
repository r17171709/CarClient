package com.renyu.carclient.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.renyu.carclient.commons.CommonUtils;

import java.util.ArrayList;

/**
 * Created by renyu on 15/10/18.
 */
public class LetterIndicatorView extends View {

    Context context=null;

    ArrayList<String> letters=null;

    //矩阵集合
    ArrayList<Rect> rects=null;

    OnLetterChangedListener listener=null;

    public interface OnLetterChangedListener {
        void LetterChanged(int position);
    }

    public void setOnLetterChangedListener(OnLetterChangedListener listener) {
        this.listener = listener;
    }

    public LetterIndicatorView(Context context) {
        this(context, null);
    }

    public LetterIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context=context;
        letters=new ArrayList<>();
        rects=new ArrayList<>();
    }

    /**
     * 设置显示的首字母指示
     * @param letters
     */
    public void setLetters(ArrayList<String> letters) {
        this.letters = letters;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        for (int i=0;i<rects.size();i++) {
            if (rects.get(i).contains((int) event.getX(), (int) event.getY())&&listener!=null) {
                listener.LetterChanged(i);
            }
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);

        int width=getMeasuredWidth();
        int height=getMeasuredHeight();

        rects.clear();
        for (int i=0;i<letters.size();i++) {
            Rect rect=new Rect(0, height*i/letters.size(), width, height*(i+1)/letters.size());
            rects.add(rect);
        }

        for (int i=0;i<rects.size();i++) {
            Rect targetRect=rects.get(i);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(3);
            paint.setTextSize(CommonUtils.sp2px(context, 15));
            String testString = letters.get(i);
            paint.setColor(Color.WHITE);
            Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
            int baseline = targetRect.top + (targetRect.bottom - targetRect.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
            // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(testString.toUpperCase(), targetRect.centerX(), baseline, paint);
        }
    }
}
