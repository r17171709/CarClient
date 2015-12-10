package com.renyu.carclient.activity.search;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.renyu.carclient.R;
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.commons.CommonUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

/**
 * Created by renyu on 15/12/9.
 */
public class GoodsDetailActivity extends BaseActivity {

    @Bind(R.id.goodsdetil_vp)
    AutoScrollViewPager goodsdetil_vp;
    VPImageAdapter imageAdapter=null;
    @Bind(R.id.goodsdetail_tip_bglayout)
    RelativeLayout goodsdetail_tip_bglayout;
    @Bind(R.id.goodsdetail_tip_layout)
    LinearLayout goodsdetail_tip_layout;

    ArrayList<ImageView> imageViews=null;
    ArrayList<String> urls=null;

    //是否正在执行动画
    boolean isDoAnimation=false;
    //当前展示的viewGroup
    ViewGroup viewGroup=null;

    @Override
    public int initContentView() {
        return R.layout.activity_goodsdetail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageViews=new ArrayList<>();
        urls=new ArrayList<>();
        urls.add("http://g.hiphotos.baidu.com/image/h%3D200/sign=efebe5305b6034a836e2bf81fb1249d9/d31b0ef41bd5ad6eab0a08db86cb39dbb6fd3c0b.jpg");
        urls.add("http://g.hiphotos.baidu.com/image/h%3D300/sign=1f38efb6d7a20cf45990f8df46084b0c/9d82d158ccbf6c81c6b044d9bb3eb13533fa407f.jpg");
        urls.add("http://a.hiphotos.baidu.com/image/h%3D200/sign=d68bcb59d609b3def4bfe368fcbe6cd3/d1160924ab18972b54c26cfbe1cd7b899e510a02.jpg");
        urls.add("http://img0.imgtn.bdimg.com/it/u=3710103736,733712610&fm=21&gp=0.jpg");
        urls.add("http://f.hiphotos.baidu.com/image/h%3D200/sign=e424d8ca3edbb6fd3a5be2263925aba6/b21c8701a18b87d69281c72e000828381e30fdc4.jpg");
        urls.add("http://d.hiphotos.baidu.com/image/h%3D200/sign=14a745168d82b90122adc433438da97e/21a4462309f790524379290f0bf3d7ca7bcbd599.jpg");
        for (int i=0;i<urls.size();i++) {
            View view= LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.adapter_image, null, false);
            ImageLoader.getInstance().displayImage(urls.get(i), (ImageView) view, getAvatarDisplayImageOptions());
            imageViews.add((ImageView) view);
        }

        initViews();
    }

    private void initViews() {
        imageAdapter=new VPImageAdapter();
        goodsdetil_vp.setAdapter(imageAdapter);
        goodsdetil_vp.setInterval(2000);
        goodsdetil_vp.setCycle(true);
        goodsdetil_vp.startAutoScroll();
    }

    @OnClick({R.id.goodsdetail_params, R.id.goodsdetail_cartype, R.id.goodsdetail_paytype, R.id.goodsdetail_tip_bglayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goodsdetail_params:
                openLayout(goodsdetail_tip_layout);
                break;
            case R.id.goodsdetail_cartype:
                openLayout(goodsdetail_tip_layout);
                break;
            case R.id.goodsdetail_paytype:
                openLayout(goodsdetail_tip_layout);
                break;
            case R.id.goodsdetail_tip_bglayout:
                if (viewGroup!=null) {
                    closeLayout(viewGroup);
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        goodsdetil_vp.stopAutoScroll();
    }

    @Override
    protected void onResume() {
        super.onResume();
        goodsdetil_vp.startAutoScroll();
    }

    public class VPImageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViews.get(position));
            return imageViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViews.get(position));
        }
    }

    public DisplayImageOptions getAvatarDisplayImageOptions() {
        return new DisplayImageOptions.Builder()
                .showImageOnFail(R.mipmap.ic_launcher)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    @Override
    public void onBackPressed() {
        if (isDoAnimation) {
            return;
        }
        if (goodsdetail_tip_bglayout.getVisibility()==View.VISIBLE) {
            closeLayout(goodsdetail_tip_layout);
        }
        else {
            super.onBackPressed();
        }
    }

    private void closeLayout(final ViewGroup layout) {
        ValueAnimator animator0=ValueAnimator.ofFloat(0, 1f);
        animator0.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ArgbEvaluator argbEvaluator = new ArgbEvaluator();
                goodsdetail_tip_bglayout.setBackgroundColor((Integer) argbEvaluator.evaluate(animation.getAnimatedFraction(), Color.parseColor("#70000000"), Color.parseColor("#00000000")));
            }
        });
        ObjectAnimator animator1=ObjectAnimator.ofFloat(layout, "translationY", 0, CommonUtils.dip2px(GoodsDetailActivity.this, 300));
        animator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isDoAnimation=false;
                goodsdetail_tip_bglayout.setVisibility(View.GONE);
                layout.setVisibility(View.GONE);
                viewGroup=null;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isDoAnimation=true;
            }
        });
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.setDuration(300);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(animator0, animator1);
        animatorSet.start();
    }

    private void openLayout(final ViewGroup layout) {
        ValueAnimator animator0=ValueAnimator.ofFloat(0, 1f);
        animator0.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ArgbEvaluator argbEvaluator = new ArgbEvaluator();
                goodsdetail_tip_bglayout.setBackgroundColor((Integer) argbEvaluator.evaluate(animation.getAnimatedFraction(), Color.parseColor("#00000000"), Color.parseColor("#70000000")));
            }
        });
        ObjectAnimator animator1=ObjectAnimator.ofFloat(layout, "translationY", CommonUtils.dip2px(GoodsDetailActivity.this, 300), 0);
        animator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isDoAnimation=false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isDoAnimation=true;
                goodsdetail_tip_bglayout.setVisibility(View.VISIBLE);
                layout.setVisibility(View.VISIBLE);
                viewGroup=layout;
            }
        });
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.setDuration(300);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(animator0, animator1);
        animatorSet.start();
    }
}
