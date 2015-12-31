package com.renyu.carclient.myview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Scroller;

import com.renyu.carclient.R;
import com.renyu.carclient.commons.CommonUtils;

/**
 * Created by renyu on 15/8/15.
 */
public class ProductDetailView extends ViewGroup {

    ProductDetailTopView topview=null;
    ProductDetailBottomView bottomview=null;
    ProductListener topProductListener=null;
    ProductListener bottomProductListener=null;

    Context context=null;

    int startY=0;
    int lastY=0;

    Scroller scroller=null;

    int currentPage=1;

    int touchSlop=0;

    public ProductDetailView(Context context) {
        this(context, null);
    }

    public ProductDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context=context;

        scroller=new Scroller(context);

        touchSlop= ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        topview= (ProductDetailTopView) findViewById(R.id.topview);
        bottomview= (ProductDetailBottomView) findViewById(R.id.bottomview);
        bottomview.setSaveEnabled(true);
        bottomview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        bottomview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);
                return false;
            }
        });
        bottomview.setWebChromeClient(new WebChromeClient());
        WebSettings settings=bottomview.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.setBlockNetworkLoads(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDatabaseEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);
//        bottomview.loadUrl("http://www.baidu.com");
        this.topProductListener=topview;
        this.bottomProductListener=bottomview;
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
        topview.layout(0, 0, getScreenWidth(context), getScreenHeight(context)- CommonUtils.dip2px(context, 53)- CommonUtils.dip2px(context, 50));
        bottomview.layout(0, getScreenHeight(context)- CommonUtils.dip2px(context, 53)- CommonUtils.dip2px(context, 50), getScreenWidth(context), getScreenHeight(context)*2- CommonUtils.dip2px(context, 53)*2- CommonUtils.dip2px(context, 50)*2);
    }

    /**
     * 得到屏幕宽度
     * @return 单位:px
     */
    public static int getScreenWidth(Context context) {
        int screenWidth;
        WindowManager wm=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        screenWidth=dm.widthPixels;
        return screenWidth;
    }

    /**
     * 得到屏幕高度
     * @return 单位:px
     */
    public static int getScreenHeight(Context context) {
        int screenHeight;
        WindowManager wm=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        screenHeight=dm.heightPixels;
        return screenHeight-getStataBar(context);
    }

    /**
     * 状态栏高度
     * @param context
     * @return
     */
    public static int getStataBar(Context context) {
        Activity act=(Activity) context;
        Rect frame=new Rect();
        act.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY= (int) ev.getY();
                startY=lastY;
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(lastY-ev.getY())<touchSlop) {
                    break;
                }
                //滑动到底部时候并且继续向上滑动
                if (topProductListener.isBottom()&&(lastY-ev.getY()>0)&&currentPage==1) {
                    System.out.println("isBottom");
                    return true;
                }
                else if (bottomProductListener.isTop()&&(lastY-ev.getY()<0)&&currentPage==2) {
                    System.out.println("isTop");
                    return true;
                }
                lastY= (int) ev.getY();
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                startY=lastY;
                return true;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(lastY-event.getY())<touchSlop) {
                    break;
                }
                scrollBy(0, lastY-(int) event.getY());
                lastY= (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (currentPage==1) {
                    scroller.startScroll(0, (startY-(int) event.getY()), 0, getScreenHeight(context)- CommonUtils.dip2px(context, 53)- CommonUtils.dip2px(context, 50)-(startY-(int) event.getY()), 1000);
                    currentPage=2;
                    postInvalidate();
                }
                else if (currentPage==2) {
                    scroller.startScroll(0, getScreenHeight(context)- CommonUtils.dip2px(context, 53)- CommonUtils.dip2px(context, 50)-((int) event.getY()-startY), 0, -(getScreenHeight(context)- CommonUtils.dip2px(context, 53)- CommonUtils.dip2px(context, 50)-((int) event.getY()-startY)), 1000);
                    currentPage=1;
                    postInvalidate();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.getCurrY());
        }
        postInvalidate();
    }

    public void setUrl(String url) {
        bottomview.loadUrl(url);
    }
}
