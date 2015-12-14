package com.renyu.carclient.activity.order;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.adapter.CartAdapter;
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.model.ProductModel;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by renyu on 15/12/13.
 */
public class CartActivity extends BaseActivity {

    @Bind(R.id.view_toolbar_center_text_next)
    TextView view_toolbar_center_text_next;
    @Bind(R.id.cart_content_rv)
    RecyclerView cart_content_rv;
    CartAdapter adapter=null;
    @Bind(R.id.cart_choice_all)
    CheckBox cart_choice_all;

    ArrayList<ProductModel> models=null;

    boolean isPermitChecked=false;

    @Override
    public int initContentView() {
        return R.layout.activity_cart;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        models=new ArrayList<>();
        models.add(new ProductModel());
        models.add(new ProductModel());
        models.add(new ProductModel());
        models.add(new ProductModel());
        models.add(new ProductModel());
        models.add(new ProductModel());
        models.add(new ProductModel());
        models.add(new ProductModel());
        models.add(new ProductModel());
        models.add(new ProductModel());

        initViews();
    }

    private void initViews() {
        view_toolbar_center_text_next.setVisibility(View.VISIBLE);
        view_toolbar_center_text_next.setText("编辑");
        cart_content_rv.setHasFixedSize(true);
        cart_content_rv.setLayoutManager(new LinearLayoutManager(this));
        adapter=new CartAdapter(this, models, new CartAdapter.OnCheckChangedListener() {
            @Override
            public void OnCheckChanged(int position, boolean flag) {
                int checkedCount=0;
                for (int i=0;i<models.size();i++) {
                    if (models.get(i).isChecked()) {
                        checkedCount++;
                    }
                }
                if (checkedCount==models.size() && !cart_choice_all.isChecked()) {
                    isPermitChecked=true;
                    cart_choice_all.setChecked(true);
                }
                else if (checkedCount!=models.size() && cart_choice_all.isChecked()) {
                    isPermitChecked=true;
                    cart_choice_all.setChecked(false);
                }
                else {
                    cart_choice_all.setChecked(false);
                }
            }
        });
        cart_content_rv.setAdapter(adapter);
    }

    @OnClick({R.id.view_toolbar_center_text_next, R.id.view_toolbar_center_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_toolbar_center_text_next:
                for (int i=0;i<models.size();i++) {
                    models.get(i).setEdit(!models.get(i).isEdit());
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.view_toolbar_center_image:
                break;
        }
    }

    @OnCheckedChanged(R.id.cart_choice_all)
    public void onCheckedChanged(boolean flag) {
        if (isPermitChecked) {
            isPermitChecked=false;
            return;
        }
        for (int i=0;i<models.size();i++) {
            models.get(i).setChecked(flag);
        }
        adapter.notifyDataSetChanged();
    }
}
