package com.renyu.carclient.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.renyu.carclient.R;
import com.renyu.carclient.adapter.MessageCenterAdapter;
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.commons.ACache;
import com.renyu.carclient.commons.OKHttpHelper;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.JsonParse;
import com.renyu.carclient.model.MessageModel;
import com.renyu.carclient.model.UserModel;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by renyu on 15/11/12.
 */
public class MessageCenterActivity extends BaseActivity {

    @Bind(R.id.view_toolbar_center_title)
    TextView view_toolbar_center_title;
    @Bind(R.id.view_toolbar_center_back)
    ImageView view_toolbar_center_back;
    @Bind(R.id.view_toolbar_center_next)
    ImageView view_toolbar_center_next;
    @Bind(R.id.messagecenter_swipy)
    SwipyRefreshLayout messagecenter_swipy;
    @Bind(R.id.messagecenter_rv)
    RecyclerView messagecenter_rv;
    MessageCenterAdapter adapter;

    ArrayList<MessageModel> models=null;

    UserModel userModel=null;

    int page_size=20;
    int page_no=1;

    @Override
    public int initContentView() {
        return R.layout.activity_messagecenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userModel= ACache.get(this).getAsObject("user")!=null?(UserModel) ACache.get(this).getAsObject("user"):null;
        models=new ArrayList<>();

        initViews();

        getMessages();
    }

    private void initViews() {
        view_toolbar_center_title.setText("站内信");
        view_toolbar_center_back.setVisibility(View.VISIBLE);
        view_toolbar_center_next.setVisibility(View.VISIBLE);
        view_toolbar_center_next.setImageResource(R.mipmap.ic_add);
        messagecenter_swipy.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction==SwipyRefreshLayoutDirection.BOTTOM) {

                }
                else if (direction==SwipyRefreshLayoutDirection.TOP) {
                    page_no=1;
                }
                getMessages();
            }
        });
        messagecenter_rv.setHasFixedSize(true);
        messagecenter_rv.setLayoutManager(new LinearLayoutManager(this));
        adapter=new MessageCenterAdapter(this, models);
        messagecenter_rv.setAdapter(adapter);
    }

    private void getMessages() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.message.list", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("user_id", ""+userModel.getUser_id());
        params.put("page_size", ""+page_size);
        params.put("page_no", ""+page_no);
        httpHelper.commonPostRequest(ParamUtils.api, params, null, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                if (page_no==1) {
                    models.clear();
                }
                models.addAll(JsonParse.getMessageLists(string));
                adapter.notifyDataSetChanged();
                page_no++;
                messagecenter_swipy.setRefreshing(false);
            }

            @Override
            public void onError() {

            }
        });
    }

    public void readMessage(int notice_id) {
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.message.edit", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("notice_id", ""+notice_id);
        httpHelper.commonPostRequest(ParamUtils.api, params, null, null);
    }

    public void deleteMessage(int notice_id, final int position) {
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.message.edit", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("notice_id", ""+notice_id);
        httpHelper.commonPostRequest(ParamUtils.api, params, new OKHttpHelper.StartListener() {
            @Override
            public void onStart() {
                showDialog("提示", "正在删除");
            }
        }, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                dismissDialog();
                Log.d("MessageCenterActivity", string);
                models.remove(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError() {
                dismissDialog();
                showToast("删除失败");
            }
        });
    }

    @OnClick({R.id.view_toolbar_center_back, R.id.view_toolbar_center_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_toolbar_center_back:
                finish();
                break;
            case R.id.view_toolbar_center_next:
                startActivity(new Intent(MessageCenterActivity.this, SendMessageActivity.class));
                break;
        }
    }
}
