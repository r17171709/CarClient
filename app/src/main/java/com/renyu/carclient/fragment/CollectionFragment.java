package com.renyu.carclient.fragment;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.adapter.CollectionAdapter;
import com.renyu.carclient.base.BaseFragment;
import com.renyu.carclient.commons.CollectionModel;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by renyu on 15/10/18.
 */
public class CollectionFragment extends BaseFragment {

    @Bind(R.id.view_toolbar_center_text_next)
    TextView view_toolbar_center_text_next;
    @Bind(R.id.collection_my)
    TextView collection_my;
    @Bind(R.id.collection_recommend)
    TextView collection_recommend;
    @Bind(R.id.cartype_rv)
    RecyclerView cartype_rv;
    CollectionAdapter adapter=null;
    @Bind(R.id.collection_layout)
    LinearLayout collection_layout;

    ArrayList<CollectionModel> models;

    boolean isChecked=false;

    PopupWindow popupWindow=null;

    @Override
    public int initContentView() {
        return R.layout.fragment_collection;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        models=new ArrayList<>();
        models.add(new CollectionModel());
        models.add(new CollectionModel());
        models.add(new CollectionModel());
        models.add(new CollectionModel());
        models.add(new CollectionModel());
        models.add(new CollectionModel());
        models.add(new CollectionModel());
        models.add(new CollectionModel());
        models.add(new CollectionModel());
        models.add(new CollectionModel());

        initViews();
    }

    private void initViews() {
        view_toolbar_center_text_next.setVisibility(View.VISIBLE);
        cartype_rv.setHasFixedSize(true);
        cartype_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new CollectionAdapter(getActivity(), models);
        cartype_rv.setAdapter(adapter);

        View view= LayoutInflater.from(getActivity()).inflate(R.layout.pop_collection, null, false);
        popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
    }

    @OnClick({R.id.collection_my, R.id.collection_recommend, R.id.view_toolbar_center_text_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.collection_my:
                collection_my.setTextColor(Color.WHITE);
                collection_recommend.setTextColor(Color.BLACK);

                popupWindow.showAsDropDown(collection_layout, 0, 0);

                break;
            case R.id.collection_recommend:
                collection_recommend.setTextColor(Color.WHITE);
                collection_my.setTextColor(Color.BLACK);
                break;
            case R.id.view_toolbar_center_text_next:
                isChecked=!isChecked;
                for (int i=0;i<models.size();i++) {
                    models.get(i).setFlag(isChecked);
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
