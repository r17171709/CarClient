package com.renyu.carclient.fragment;

import android.content.Intent;
import android.view.View;

import com.renyu.carclient.R;
import com.renyu.carclient.activity.my.MyAddressActivity;
import com.renyu.carclient.activity.my.MyFeedBackActivity;
import com.renyu.carclient.activity.my.MyInfoActivity;
import com.renyu.carclient.activity.my.MyStatementActivity;
import com.renyu.carclient.base.BaseFragment;

import butterknife.OnClick;

/**
 * Created by renyu on 15/10/18.
 */
public class MyFragment extends BaseFragment {
    @Override
    public int initContentView() {
        return R.layout.fragment_my;
    }

    @OnClick({R.id.my_info, R.id.my_address, R.id.my_paylist, R.id.my_feedback})
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
        }
    }
}
