package com.renyu.carclient.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.base.BaseFragment;
import com.renyu.carclient.model.ProductModel;
import com.renyu.carclient.model.ShopModel;
import com.renyu.carclient.myview.CartScrollView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by renyu on 15/10/18.
 */
public class CartFragment extends BaseFragment {

    @Bind(R.id.view_toolbar_center_title)
    TextView view_toolbar_center_title;
    @Bind(R.id.cart_content_layout)
    LinearLayout cart_content_layout;
    @Bind(R.id.cart_content_sv)
    CartScrollView cart_content_sv;
    @Bind(R.id.cart_cal)
    Button cart_cal;
    @Bind(R.id.cart_choice_all)
    CheckBox cart_choice_all;

    ArrayList<ShopModel> shopModels=null;

    //滑动距离top
    int scrollTop=0;

    @Override
    public int initContentView() {
        return R.layout.fragment_cart;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shopModels=new ArrayList<>();

        initViews();

        initData();

        refreshViews();
    }

    @OnClick({R.id.cart_cal, R.id.cart_choice_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cart_cal:
                //用户选中的下单商家集合
                ArrayList<ShopModel> tempShopModels=new ArrayList<>();
                for (int i=0;i<shopModels.size();i++) {
                    ShopModel model=shopModels.get(i);
                    ArrayList<ProductModel> models=model.getModels();
                    //用户选中的下单商品集合
                    ArrayList<ProductModel> tempProductModels=new ArrayList<>();
                    for (int j=0;j<models.size();j++) {
                        if (models.get(j).isChecked()) {
                            tempProductModels.add(models.get(j));
                        }
                    }
                    if (tempProductModels.size()>0) {
                        //用户选中的下单商家
                        ShopModel tempShopModel=new ShopModel();
                        tempShopModel.setId(model.getId());
                        tempShopModel.setShopname(model.getShopname());
                        tempShopModel.setModels(tempProductModels);
                    }
                }
                break;
            case R.id.cart_choice_all:
                for (int i=0;i<shopModels.size();i++) {
                    ShopModel model=shopModels.get(i);
                    ArrayList<ProductModel> models=model.getModels();
                    for (int j=0;j<models.size();j++) {
                        models.get(j).setIsChecked(cart_choice_all.isChecked());
                    }
                }
                for (int i=0;i<cart_content_layout.getChildCount();i++) {
                    View viewTitle=cart_content_layout.getChildAt(i);
                    CheckBox cart_title_checkbox= (CheckBox) viewTitle.findViewById(R.id.cart_title_checkbox);
                    cart_title_checkbox.setChecked(cart_choice_all.isChecked());
                    LinearLayout cart_title_contentlayout= (LinearLayout) viewTitle.findViewById(R.id.cart_title_contentlayout);
                    for (int j=0;j<cart_title_contentlayout.getChildCount();j++) {
                        View viewChild=cart_title_contentlayout.getChildAt(j);
                        CheckBox cart_content_checkbox= (CheckBox) viewChild.findViewById(R.id.cart_content_checkbox);
                        cart_content_checkbox.setChecked(cart_choice_all.isChecked());
                    }
                }
                break;
        }
    }

    private void initViews() {
        view_toolbar_center_title.setText("购物车");
        cart_content_sv.setOnScrollChanedListener(new CartScrollView.OnScrollChanedListener() {
            @Override
            public void onScrollChanged(int t) {
                scrollTop=t;
            }
        });
    }

    private void initData() {
        for (int i=0;i<5;i++) {
            ShopModel shopModel=new ShopModel();
            shopModel.setId(1);
            shopModel.setIsEdit(false);
            ArrayList<ProductModel> productModels=new ArrayList<>();
            ProductModel model=new ProductModel();
            model.setTitle("123");
            model.setId(11);
            model.setChoice("abc");
            model.setIsChecked(false);
            model.setNum(1);
            model.setPrice(1.11);
            productModels.add(model);
            ProductModel mode2=new ProductModel();
            mode2.setTitle("123");
            mode2.setId(12);
            mode2.setChoice("abc");
            mode2.setIsChecked(false);
            mode2.setNum(2);
            mode2.setPrice(1.11);
            productModels.add(mode2);
            shopModel.setModels(productModels);
            shopModels.add(shopModel);
        }

        view_toolbar_center_title.setText("购物车("+getTotalNum()+")");
    }

    private int getTotalNum() {
        int totalNum=0;
        for (int i=0;i<shopModels.size();i++) {
            totalNum+=shopModels.get(i).getModels().size();
        }
        return totalNum;
    }

    private void refreshViews() {
        cart_content_layout.removeAllViews();
        for (int i=0;i<shopModels.size();i++) {
            //商品填充视图数量
            final ArrayList<View> contentlayouts=new ArrayList<>();
            final int i_=i;
            View cart_title= LayoutInflater.from(getActivity()).inflate(R.layout.adapter_cart_title, null, false);
            final CheckBox cart_title_checkbox= (CheckBox) cart_title.findViewById(R.id.cart_title_checkbox);
            boolean finalOutCheckValue=shopModels.get(i).getModels().get(0).isChecked();
            if (finalOutCheckValue) {
                for (int m=0;m<shopModels.get(i).getModels().size();m++) {
                    if (shopModels.get(i).getModels().get(m).isChecked()==false) {
                        finalOutCheckValue=false;
                        break;
                    }
                }
                cart_title_checkbox.setChecked(finalOutCheckValue);
            }
            else {
                cart_title_checkbox.setChecked(false);
            }
            cart_title_checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked=cart_title_checkbox.isChecked();
                    for (int i=0;i<contentlayouts.size();i++) {
                        View view=contentlayouts.get(i);
                        CheckBox cart_content_checkbox= (CheckBox) view.findViewById(R.id.cart_content_checkbox);
                        cart_content_checkbox.setChecked(isChecked);
                    }
                    for (int k=0;k<shopModels.get(i_).getModels().size();k++) {
                        shopModels.get(i_).getModels().get(k).setIsChecked(isChecked);
                    }

                    if (isChecked) {
                        cart_choice_all.setChecked(true);
                        outer:
                        for (int i=0;i<shopModels.size();i++) {
                            ShopModel model=shopModels.get(i);
                            ArrayList<ProductModel> models=model.getModels();
                            for (int j=0;j<models.size();j++) {
                                if (!models.get(j).isChecked()) {
                                    cart_choice_all.setChecked(false);
                                    break outer;
                                }
                            }
                        }
                    }
                    else {
                        cart_choice_all.setChecked(false);
                    }
                }
            });
            TextView cart_title_edit= (TextView) cart_title.findViewById(R.id.cart_title_edit);
            cart_title_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (shopModels.get(i_).isEdit()) {
                        for (int i=0;i<contentlayouts.size();i++) {
                            View view=contentlayouts.get(i);
                            RelativeLayout cart_content_finishlayout= (RelativeLayout) view.findViewById(R.id.cart_content_finishlayout);
                            cart_content_finishlayout.setVisibility(View.VISIBLE);
                            RelativeLayout cart_content_editlayout= (RelativeLayout) view.findViewById(R.id.cart_content_editlayout);
                            cart_content_editlayout.setVisibility(View.GONE);
                        }
                    }
                    else {
                        for (int i=0;i<contentlayouts.size();i++) {
                            View view=contentlayouts.get(i);
                            RelativeLayout cart_content_finishlayout= (RelativeLayout) view.findViewById(R.id.cart_content_finishlayout);
                            cart_content_finishlayout.setVisibility(View.GONE);
                            RelativeLayout cart_content_editlayout= (RelativeLayout) view.findViewById(R.id.cart_content_editlayout);
                            cart_content_editlayout.setVisibility(View.VISIBLE);
                        }
                    }
                    shopModels.get(i_).setIsEdit(!shopModels.get(i_).isEdit());
                }
            });
            final LinearLayout cart_title_contentlayout= (LinearLayout) cart_title.findViewById(R.id.cart_title_contentlayout);
            for (int j=0;j<shopModels.get(i).getModels().size();j++) {
                final int j_=j;
                View cart_content=LayoutInflater.from(getActivity()).inflate(R.layout.adapter_cart_content, null, false);
                RelativeLayout cart_content_finishlayout= (RelativeLayout) cart_content.findViewById(R.id.cart_content_finishlayout);
                RelativeLayout cart_content_editlayout= (RelativeLayout) cart_content.findViewById(R.id.cart_content_editlayout);
                if (shopModels.get(i).isEdit()) {
                    cart_content_finishlayout.setVisibility(View.GONE);
                    cart_content_editlayout.setVisibility(View.VISIBLE);
                }
                else {
                    cart_content_finishlayout.setVisibility(View.VISIBLE);
                    cart_content_editlayout.setVisibility(View.GONE);
                }
                TextView cart_content_delete= (TextView) cart_content.findViewById(R.id.cart_content_delete);
                cart_content_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shopModels.get(i_).getModels().remove(j_);
                        if (shopModels.get(i_).getModels().size() == 0) {
                            shopModels.remove(i_);
                        }
                        refreshViews();
                    }
                });
                final EditText cart_content_productnum= (EditText) cart_content.findViewById(R.id.cart_content_productnum);
                cart_content_productnum.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        Log.d("CartFragment", s.toString());
                    }
                });
                TextView cart_content_productnum_delete= (TextView) cart_content.findViewById(R.id.cart_content_productnum_delete);
                cart_content_productnum_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cart_content_productnum.setText("" + (Integer.parseInt(cart_content_productnum.getText().toString()) - 1));
                    }
                });
                TextView cart_content_productnum_add= (TextView) cart_content.findViewById(R.id.cart_content_productnum_add);
                cart_content_productnum_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cart_content_productnum.setText("" + (Integer.parseInt(cart_content_productnum.getText().toString()) + 1));
                    }
                });
                final CheckBox cart_content_checkbox= (CheckBox) cart_content.findViewById(R.id.cart_content_checkbox);
                cart_content_checkbox.setChecked(shopModels.get(i).getModels().get(j).isChecked());
                cart_content_checkbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isChecked = cart_content_checkbox.isChecked();
                        shopModels.get(i_).getModels().get(j_).setIsChecked(isChecked);
                        if (isChecked) {
                            boolean finalValue = true;
                            for (int k = 0; k < shopModels.get(i_).getModels().size(); k++) {
                                if (shopModels.get(i_).getModels().get(k).isChecked() != isChecked) {
                                    finalValue = false;
                                    break;
                                }
                            }
                            if (finalValue) {
                                cart_title_checkbox.setChecked(isChecked);
                            } else {
                                cart_title_checkbox.setChecked(false);
                            }
                        } else {
                            cart_title_checkbox.setChecked(false);
                        }

                        if (isChecked) {
                            cart_choice_all.setChecked(true);
                            outer:
                            for (int i=0;i<shopModels.size();i++) {
                                ShopModel model=shopModels.get(i);
                                ArrayList<ProductModel> models=model.getModels();
                                for (int j=0;j<models.size();j++) {
                                    if (!models.get(j).isChecked()) {
                                        cart_choice_all.setChecked(false);
                                        break outer;
                                    }
                                }
                            }
                        }
                        else {
                            cart_choice_all.setChecked(false);
                        }
                    }
                });
                cart_title_contentlayout.addView(cart_content);
                contentlayouts.add(cart_content);
            }
            cart_content_layout.addView(cart_title);
        }
        cart_content_sv.scrollTo(0, scrollTop);
        view_toolbar_center_title.setText("购物车("+getTotalNum()+")");
    }
}
