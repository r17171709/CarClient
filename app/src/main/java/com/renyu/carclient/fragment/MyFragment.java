package com.renyu.carclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.activity.my.MyAddressActivity;
import com.renyu.carclient.activity.my.MyFeedBackActivity;
import com.renyu.carclient.activity.my.MyInfoActivity;
import com.renyu.carclient.activity.my.MyRepaymentActivity;
import com.renyu.carclient.activity.my.MyStatementActivity;
import com.renyu.carclient.base.BaseFragment;
import com.renyu.carclient.commons.ACache;
import com.renyu.carclient.commons.OKHttpHelper;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.model.UserModel;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;

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

    UserModel userModel=null;

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
    }

    @OnClick({R.id.my_info, R.id.my_address, R.id.my_paylist, R.id.my_feedback, R.id.my_repayment})
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
        }
    }
}
