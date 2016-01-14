package com.renyu.carclient.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.adapter.CollectionAdapter;
import com.renyu.carclient.base.BaseFragment;
import com.renyu.carclient.commons.ACache;
import com.renyu.carclient.commons.OKHttpHelper;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.CollectionModel;
import com.renyu.carclient.model.JsonParse;
import com.renyu.carclient.model.UserModel;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by renyu on 15/10/18.
 */
public class CollectionFragment extends BaseFragment {

    @Bind(R.id.view_toolbar_center_title)
    TextView view_toolbar_center_title;
    @Bind(R.id.view_toolbar_center_text_next)
    TextView view_toolbar_center_text_next;
    @Bind(R.id.cartype_rv)
    RecyclerView cartype_rv;
    CollectionAdapter adapter=null;

    ArrayList<CollectionModel> leftModels;

    boolean isChecked=false;

    PopupWindow popupWindow=null;

    UserModel userModel=null;

    @Override
    public int initContentView() {
        return R.layout.fragment_collection;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        leftModels=new ArrayList<>();

        userModel= ACache.get(getActivity()).getAsObject("user")!=null?(UserModel) ACache.get(getActivity()).getAsObject("user"):null;

        initViews();
    }

    private void initViews() {
        view_toolbar_center_title.setText("收藏");
        view_toolbar_center_text_next.setVisibility(View.VISIBLE);
        view_toolbar_center_text_next.setText("编辑");
        cartype_rv.setHasFixedSize(true);
        cartype_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new CollectionAdapter(getActivity(), leftModels, new CollectionAdapter.OnDeleteListener() {
            @Override
            public void deletePosition(String item_id, int position) {
                deleteFav(item_id, position);
            }
        });
        cartype_rv.setAdapter(adapter);

        View view= LayoutInflater.from(getActivity()).inflate(R.layout.pop_collection, null, false);
        popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
    }

    @OnClick({R.id.view_toolbar_center_text_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_toolbar_center_text_next:
                isChecked=!isChecked;
                if (isChecked) {
                    view_toolbar_center_text_next.setText("完成");
                }
                else {
                    view_toolbar_center_text_next.setText("编辑");
                }
                for (int i=0;i<leftModels.size();i++) {
                    leftModels.get(i).setFlag(isChecked);
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }

    private void getCollectList() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.itemcollect.list", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("user_id", ""+userModel.getUser_id());
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                ArrayList<CollectionModel> temp=JsonParse.getCollectionModels(string);
                if (temp!=null) {
                    for (int i=0;i<temp.size();i++) {
                        temp.get(i).setFlag(isChecked);
                    }
                    leftModels.clear();
                    leftModels.addAll(temp);
                    adapter.notifyDataSetChanged();
                }
                else {
                    showToast("未知错误");
                }
            }

            @Override
            public void onError() {
                showToast(getResources().getString(R.string.network_error));
            }
        });
    }

    public void deleteFav(String item_id, final int position) {
        HashMap<String, String> params= ParamUtils.getSignParams("app.itemcollect.cancel", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("user_id", ""+userModel.getUser_id());
        params.put("item_id", item_id);
        httpHelper.commonPostRequest(ParamUtils.api, params, new OKHttpHelper.StartListener() {
            @Override
            public void onStart() {
                leftModels.remove(position);
                adapter.notifyDataSetChanged();
            }
        }, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {

            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        getCollectList();
    }
}
