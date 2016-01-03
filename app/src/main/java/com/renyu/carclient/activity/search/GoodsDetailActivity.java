package com.renyu.carclient.activity.search;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
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
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.renyu.carclient.R;
import com.renyu.carclient.activity.order.PayOrderActivity;
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.commons.CommonUtils;
import com.renyu.carclient.commons.OKHttpHelper;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.GoodsListModel;
import com.renyu.carclient.model.JsonParse;
import com.renyu.carclient.myview.PriceView;
import com.renyu.carclient.myview.ProductDetailView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import butterknife.Bind;
import butterknife.OnClick;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

/**
 * Created by renyu on 15/12/9.
 */
public class GoodsDetailActivity extends BaseActivity {

    @Bind(R.id.goodsdetailview)
    ProductDetailView goodsdetailview;
    @Bind(R.id.goodsdetil_vp)
    AutoScrollViewPager goodsdetil_vp;
    VPImageAdapter imageAdapter=null;
    @Bind(R.id.goodsdetail_tip_bglayout)
    RelativeLayout goodsdetail_tip_bglayout;
    @Bind(R.id.goodsdetail_tip_layout)
    LinearLayout goodsdetail_tip_layout;
    @Bind(R.id.goodsdetil_title)
    TextView goodsdetil_title;
    @Bind(R.id.goodsdetil_store)
    TextView goodsdetil_store;
    @Bind(R.id.price_layout)
    PriceView price_layout;

    ArrayList<ImageView> imageViews=null;
    ArrayList<String> urls=null;

    //是否正在执行动画
    boolean isDoAnimation=false;
    //当前展示的viewGroup
    ViewGroup viewGroup=null;

    String item_id="";

    GoodsListModel model=null;

    @Override
    public int initContentView() {
        return R.layout.activity_goodsdetail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        item_id=getIntent().getExtras().getString("item_id");

        imageViews=new ArrayList<>();
        urls=new ArrayList<>();

        initViews();

        getDetail();
    }

    private void initViews() {

    }

    @OnClick({R.id.goodsdetail_params, R.id.goodsdetail_cartype, R.id.goodsdetail_paytype, R.id.goodsdetail_tip_bglayout, R.id.goodsdetail_addcart, R.id.goodsdetail_paynow, R.id.goodsdetail_service})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goodsdetail_params:
                goodsdetail_tip_layout.removeAllViews();
                View view1=LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.view_goodsdetail_params, null, false);
                LinearLayout goodsdetail_params_layout= (LinearLayout) view1.findViewById(R.id.goodsdetail_params_layout);
                try {
                    JSONObject object=new JSONObject(model.getParams());
                    JSONObject data=object.getJSONObject("规格参数");
                    Iterator<String> iterable=data.keys();
                    while (iterable.hasNext()) {
                        String key=iterable.next();
                        String value=data.getString(key);
                        View view2=LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.view_goodsdetail_params_child, null, false);
                        TextView goodsdetail_params_child_left= (TextView) view2.findViewById(R.id.goodsdetail_params_child_left);
                        goodsdetail_params_child_left.setText(key);
                        TextView goodsdetail_params_child_right= (TextView) view2.findViewById(R.id.goodsdetail_params_child_right);
                        goodsdetail_params_child_right.setText(value);
                        goodsdetail_params_layout.addView(view2);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                TextView goodsdetail_params_close= (TextView) view1.findViewById(R.id.goodsdetail_params_close);
                goodsdetail_params_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeLayout(goodsdetail_tip_layout);
                    }
                });
                goodsdetail_tip_layout.addView(view1);
                openLayout(goodsdetail_tip_layout);
                break;
            case R.id.goodsdetail_cartype:
                goodsdetail_tip_layout.removeAllViews();
                View view4=LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.view_goodsdetail_cartype, null, false);
                goodsdetail_tip_layout.addView(view4);
                openLayout(goodsdetail_tip_layout);
                break;
            case R.id.goodsdetail_paytype:
                goodsdetail_tip_layout.removeAllViews();
                View view5=LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.view_goodsdetail_paytype, null, false);
                LinearLayout goodsdetail_paytype_layout= (LinearLayout) view5.findViewById(R.id.goodsdetail_paytype_layout);
                View view6=LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.view_goodsdetail_paytype_child, null, false);
                goodsdetail_paytype_layout.addView(view6);
                goodsdetail_tip_layout.addView(view5);
                openLayout(goodsdetail_tip_layout);
                break;
            case R.id.goodsdetail_tip_bglayout:
                if (viewGroup!=null) {
                    closeLayout(viewGroup);
                }
                break;
            case R.id.goodsdetail_addcart:
                addCart();
                break;
            case R.id.goodsdetail_paynow:
                ArrayList<GoodsListModel> listModels=new ArrayList<>();
                model.setQuantity(price_layout.getPriceNum());
                listModels.add(model);
                Intent intent=new Intent(GoodsDetailActivity.this, PayOrderActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("payorder", listModels);
                bundle.putBoolean("isCart", false);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.goodsdetail_service:
                goodsdetail_tip_layout.removeAllViews();
                View view2=LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.view_goodsdetail_service, null, false);
                LinearLayout goodsdetail_service_layout= (LinearLayout) view2.findViewById(R.id.goodsdetail_service_layout);
                for (int i=0;i<3;i++) {
                    View view3=LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.view_goodsdetail_service_child, null, false);
                    ImageView goodsdetail_params_child_image= (ImageView) view3.findViewById(R.id.goodsdetail_params_child_image);
                    TextView goodsdetail_params_child_text= (TextView) view3.findViewById(R.id.goodsdetail_params_child_text);
                    if (i==0) {
                        goodsdetail_params_child_text.setText("由当地服务商提供快速配送");
                    }
                    else if (i==1) {
                        goodsdetail_params_child_text.setText("专业产品团队保障货品精准交付");
                    }
                    else if (i==2) {
                        goodsdetail_params_child_text.setText("无运费、退货上门取件，随时退款");
                    }
                    goodsdetail_service_layout.addView(view3);
                }
                TextView goodsdetail_service_close= (TextView) view2.findViewById(R.id.goodsdetail_service_close);
                goodsdetail_service_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeLayout(goodsdetail_tip_layout);
                    }
                });
                goodsdetail_tip_layout.addView(view2);
                openLayout(goodsdetail_tip_layout);
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

    private void getDetail() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.item.list", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("item_id", item_id);
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                model= JsonParse.getGoodsDetailModel(string);
                //加载图片滚动
                if (model.getList_image().equals("")) {

                }
                else if (model.getList_image().indexOf(",")==-1) {
                    for (int i=0;i<3;i++) {
                        urls.add(model.getList_image());
                    }
                }
                else if (model.getList_image().split(",").length==2) {
                    for (int i=0;i<2;i++) {
                        for (int j=0;j<model.getList_image().split(",").length;j++) {
                            urls.add(model.getList_image().split(",")[j]);
                        }
                    }
                }
                for (int i=0;i<urls.size();i++) {
                    View view= LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.adapter_image, null, false);
                    ImageLoader.getInstance().displayImage(urls.get(i), (ImageView) view, getAvatarDisplayImageOptions());
                    imageViews.add((ImageView) view);
                }
                imageAdapter=new VPImageAdapter();
                goodsdetil_vp.setAdapter(imageAdapter);
                goodsdetil_vp.setInterval(2000);
                goodsdetil_vp.setCycle(true);
                goodsdetil_vp.startAutoScroll();

                price_layout.setMaxNum(model.getStore());

                goodsdetil_title.setText(model.getTitle());
                goodsdetil_store.setText("库存："+model.getStore());

                goodsdetailview.setUrl("http://www.baidu.com");
            }

            @Override
            public void onError() {

            }
        });
    }

    private void addCart() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.check.add", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("quantity", ""+price_layout.getPriceNum());
        params.put("item_id", ""+model.getItem_id());
        params.put("user_id", "57");
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                if (JsonParse.getResultValue(string)!=null) {
                    showToast(JsonParse.getResultValue(string));
                }
                else {
                    showToast("未知错误");
                }
            }

            @Override
            public void onError() {

            }
        });
    }
}
