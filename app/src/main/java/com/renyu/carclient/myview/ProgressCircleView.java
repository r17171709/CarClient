package com.renyu.carclient.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.renyu.carclient.R;

/**
 * Created by renyu on 15/12/1.
 */
public class ProgressCircleView extends View {

    int background_color;
    int foreground_color;
    int progress;
    int stoke_width;

    Paint paint_background=null;
    Paint paint_foreground=null;

    public ProgressCircleView(Context context) {
        this(context, null);
    }

    public ProgressCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.ProgressCircleViewStyle);
        background_color=array.getColor(R.styleable.ProgressCircleViewStyle_background_color, Color.parseColor("#737373"));
        foreground_color=array.getColor(R.styleable.ProgressCircleViewStyle_foreground_color, Color.parseColor("#d0101b"));
        progress=array.getInt(R.styleable.ProgressCircleViewStyle_progress, 0);
        stoke_width= (int) (context.getResources().getDisplayMetrics().scaledDensity*(array.getDimensionPixelSize(R.styleable.ProgressCircleViewStyle_stoke_width, 10)));
        array.recycle();

        paint_background=new Paint();
        paint_background.setAntiAlias(true);
        paint_background.setStrokeCap(Paint.Cap.ROUND);
        paint_background.setStrokeJoin(Paint.Join.ROUND);
        paint_background.setStrokeWidth(stoke_width);
        paint_background.setStyle(Paint.Style.STROKE);
        paint_background.setColor(background_color);

        paint_foreground=new Paint();
        paint_foreground.setAntiAlias(true);
        paint_foreground.setStrokeCap(Paint.Cap.ROUND);
        paint_foreground.setStrokeJoin(Paint.Join.ROUND);
        paint_foreground.setStrokeWidth(stoke_width);
        paint_foreground.setStyle(Paint.Style.STROKE);
        paint_foreground.setColor(foreground_color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(getMeasuredWidth()/2, getMeasuredHeight()/2, getMeasuredHeight()<getMeasuredWidth()?getMeasuredHeight()/2-stoke_width:getMeasuredWidth()/2-stoke_width, paint_background);
//        canvas.drawCircle(getMeasuredWidth()/2, getMeasuredHeight()/2, getMeasuredHeight()<getMeasuredWidth()?getMeasuredHeight()/2-stoke_width:getMeasuredWidth()/2-stoke_width, paint_foreground);
        int radius=getMeasuredHeight()<getMeasuredWidth()?getMeasuredHeight()/2-stoke_width:getMeasuredWidth()/2-stoke_width;
        RectF rectF=new RectF(getMeasuredWidth()/2-radius, getMeasuredHeight()/2-radius, getMeasuredWidth()/2+radius, getMeasuredHeight()/2+radius);
        canvas.drawArc(rectF, -90, (float) progress/100*360, false, paint_foreground);
    }
}
