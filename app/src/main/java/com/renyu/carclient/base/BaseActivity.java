package com.renyu.carclient.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.renyu.carclient.commons.OKHttpHelper;

import butterknife.ButterKnife;

/**
 * Created by renyu on 15/10/17.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public OKHttpHelper httpHelper=null;

    ProgressDialog pd=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (initContentView()!=0) {
            setContentView(initContentView());
            ButterKnife.bind(this);
        }
        httpHelper=new OKHttpHelper();
    }

    public abstract int initContentView();

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void showDialog(String title, String message) {
        pd= ProgressDialog.show(this, title, message);
    }

    public void dismissDialog() {
        if (pd!=null) {
            pd.dismiss();
        }
    }

}
