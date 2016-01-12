package com.renyu.carclient.activity.my;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.renyu.carclient.R;
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.commons.ACache;
import com.renyu.carclient.commons.OKHttpHelper;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.JsonParse;
import com.renyu.carclient.model.MyInfoModel;
import com.renyu.carclient.model.UserModel;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by renyu on 15/12/14.
 */
public class MyInfoActivity extends BaseActivity {

    @Bind(R.id.view_toolbar_center_back)
    ImageView view_toolbar_center_back;
    @Bind(R.id.view_toolbar_center_title)
    TextView view_toolbar_center_title;
    @Bind(R.id.myinfo_id)
    TextView myinfo_id;
    @Bind(R.id.myinfo_name)
    TextView myinfo_name;
    @Bind(R.id.myinfo_avatar)
    CircleImageView myinfo_avatar;
    @Bind(R.id.myinfo_contact)
    TextView myinfo_contact;
    @Bind(R.id.myinfo_phone)
    TextView myinfo_phone;
    @Bind(R.id.myinfo_email)
    TextView myinfo_email;
    @Bind(R.id.myinfo_company)
    TextView myinfo_company;
    @Bind(R.id.myinfo_revenues_code)
    TextView myinfo_revenues_code;
    @Bind(R.id.myinfo_contact_person)
    TextView myinfo_contact_person;
    @Bind(R.id.myinfo_contact_phone)
    TextView myinfo_contact_phone;
    @Bind(R.id.myinfo_bank)
    TextView myinfo_bank;
    @Bind(R.id.myinfo_bank_account)
    TextView myinfo_bank_account;
    @Bind(R.id.myinfo_business_encoding)
    TextView myinfo_business_encoding;
    @Bind(R.id.myinfo_corporation)
    TextView myinfo_corporation;
    @Bind(R.id.myinfo_address)
    TextView myinfo_address;
    @Bind(R.id.myinfo_bussiness_layout)
    LinearLayout myinfo_bussiness_layout;
    @Bind(R.id.myinfo_comp_layout)
    LinearLayout myinfo_comp_layout;

    UserModel userModel=null;
    MyInfoModel myInfoModel=null;

    @Override
    public int initContentView() {
        return R.layout.activity_myinfo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userModel= ACache.get(this).getAsObject("user")!=null?(UserModel) ACache.get(this).getAsObject("user"):null;

        view_toolbar_center_back.setVisibility(View.VISIBLE);
        view_toolbar_center_title.setText("基本信息");
        ImageLoader.getInstance().displayImage(userModel.getHead_photo(), myinfo_avatar, getAvatarDisplayImageOptions());

        getMyData();
    }

    private void getMyData() {
        HashMap<String, String> params= ParamUtils.getSignParams("app.user.member.info", "28062e40a8b27e26ba3be45330ebcb0133bc1d1cf03e17673872331e859d2cd4");
        params.put("user_id", "" + userModel.getUser_id());
        httpHelper.commonPostRequest(ParamUtils.api, params, new OKHttpHelper.StartListener() {
            @Override
            public void onStart() {
                showDialog("提示", "正在加载");
            }
        }, new OKHttpHelper.RequestListener() {
            @Override
            public void onSuccess(String string) {
                dismissDialog();
                myInfoModel = JsonParse.getMyInfo(string);
                myinfo_id.setText(myInfoModel.getLogin_account());
                myinfo_name.setText(myInfoModel.getAccount_name());
                myinfo_contact.setText(myInfoModel.getContact_person());
                myinfo_phone.setText(myInfoModel.getContact_tel());
                myinfo_email.setText(myInfoModel.getEmail());
                myinfo_company.setText(myInfoModel.getRepairdepot_name());
                myinfo_revenues_code.setText(myInfoModel.getRevenues_code());
                myinfo_contact_person.setText(myInfoModel.getContact_person());
                myinfo_contact_phone.setText(myInfoModel.getContact_phone());
                myinfo_bank.setText(myInfoModel.getBank_name());
                myinfo_bank_account.setText(myInfoModel.getBank_account());
                myinfo_business_encoding.setText(myInfoModel.getBusiness_encoding());
                myinfo_corporation.setText(myInfoModel.getCorporation());
                myinfo_address.setText(myInfoModel.getReg_address());
            }

            @Override
            public void onError() {
                dismissDialog();
            }
        });
    }

    @OnClick({R.id.myinfo_comp_text, R.id.myinfo_bussiness_text, R.id.myinfo_avatar, R.id.view_toolbar_center_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myinfo_comp_text:
                if (myinfo_comp_layout.getVisibility()==View.VISIBLE) {
                    myinfo_comp_layout.setVisibility(View.GONE);
                }
                else {
                    myinfo_comp_layout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.myinfo_bussiness_text:
                if (myinfo_bussiness_layout.getVisibility()==View.VISIBLE) {
                    myinfo_bussiness_layout.setVisibility(View.GONE);
                }
                else {
                    myinfo_bussiness_layout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.myinfo_avatar:
                break;
            case R.id.view_toolbar_center_back:
                finish();
                break;
        }
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
