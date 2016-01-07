package com.renyu.carclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.renyu.carclient.R;
import com.renyu.carclient.activity.my.MyAddressActivity;
import com.renyu.carclient.activity.my.MyFeedBackActivity;
import com.renyu.carclient.activity.my.MyInfoActivity;
import com.renyu.carclient.activity.my.MyRepaymentActivity;
import com.renyu.carclient.activity.my.MyStatementActivity;
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.base.BaseFragment;
import com.renyu.carclient.commons.ACache;
import com.renyu.carclient.commons.OKHttpHelper;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.JsonParse;
import com.renyu.carclient.model.UserModel;

import java.io.File;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by renyu on 15/10/18.
 */
public class MyFragment extends BaseFragment {

    @Bind(R.id.my_all_money)
    TextView my_all_money;
    @Bind(R.id.my_last_money)
    TextView my_last_money;
    @Bind(R.id.my_name)
    TextView my_name;
    @Bind(R.id.my_avatar)
    CircleImageView my_avatar;

    UserModel userModel=null;
    String lastAvatar="";

    @Override
    public int initContentView() {
        return R.layout.fragment_my;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userModel= ACache.get(getActivity()).getAsObject("user")!=null?(UserModel) ACache.get(getActivity()).getAsObject("user"):null;
        my_all_money.setText("平台授信："+userModel.getInit_amount());
        my_last_money.setText("余额："+userModel.getAmount());
        my_name.setText(userModel.getRepairdepot_name());
        ImageLoader.getInstance().displayImage(userModel.getHead_photo(), my_avatar, getAvatarDisplayImageOptions());
    }

    @OnClick({R.id.my_info, R.id.my_address, R.id.my_paylist, R.id.my_feedback, R.id.my_repayment, R.id.my_avatar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_info:
                Intent myinfo_intent=new Intent(getActivity(), MyInfoActivity.class);
                startActivity(myinfo_intent);
                break;
            case R.id.my_address:
                Intent myaddress_intent=new Intent(getActivity(), MyAddressActivity.class);
                startActivity(myaddress_intent);
                break;
            case R.id.my_paylist:
                Intent mystatement_intent=new Intent(getActivity(), MyStatementActivity.class);
                startActivity(mystatement_intent);
                break;
            case R.id.my_feedback:
                Intent myfeedback_intent=new Intent(getActivity(), MyFeedBackActivity.class);
                startActivity(myfeedback_intent);
                break;
            case R.id.my_repayment:
                Intent myrepayment_intent=new Intent(getActivity(), MyRepaymentActivity.class);
                startActivity(myrepayment_intent);
                break;
            case R.id.my_avatar:
                if (!lastAvatar.equals("")) {
                    File file=new File(lastAvatar);
                    if (file.exists()) {
                        file.delete();
                    }
                }
                ((BaseActivity) getActivity()).openChoiceImage();
                break;
        }
    }

    public void refreshAvatar(String filePath) {
        //刷新头像
        ImageLoader.getInstance().displayImage("file://"+filePath, my_avatar);
        lastAvatar=filePath;

        uploadAvatar();
    }

    private void uploadAvatar() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.member.update", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("user_id", ""+userModel.getUser_id());
        HashMap<String, File> filesMap=new HashMap<>();
        if (!lastAvatar.equals("")) {
            filesMap.put("head_photo", new File(lastAvatar));
        }
        httpHelper.asyncUpload(filesMap, ParamUtils.api, params, new OKHttpHelper.StartListener() {
            @Override
            public void onStart() {
                showDialog("提示", "正在上传");
            }
        }, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                dismissDialog();

                userModel.setHead_photo(JsonParse.getAvatar(string));
                ACache.get(getActivity()).put("user", userModel);
            }

            @Override
            public void onError() {
                dismissDialog();
            }
        });
    }

    public DisplayImageOptions getAvatarDisplayImageOptions() {
        return new DisplayImageOptions.Builder()
                .showImageOnFail(R.mipmap.ic_me_logo)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageForEmptyUri(R.mipmap.ic_me_logo)
                .showImageOnLoading(R.mipmap.ic_me_logo)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }
}
