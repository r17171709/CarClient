package com.renyu.carclient.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.renyu.carclient.R;
import com.renyu.carclient.activity.login.LoginActivity;
import com.renyu.carclient.base.BaseActivity;
import com.renyu.carclient.commons.ACache;
import com.renyu.carclient.commons.CommonUtils;
import com.renyu.carclient.commons.ParamUtils;
import com.renyu.carclient.fragment.CollectionFragment;
import com.renyu.carclient.fragment.IndexFragment;
import com.renyu.carclient.fragment.MyFragment;
import com.renyu.carclient.fragment.OrderFragment;
import com.renyu.carclient.fragment.SearchFragment;
import com.renyu.carclient.model.UserModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by renyu on 15/10/17.
 */
public class MainActivity extends BaseActivity {

    @Bind(R.id.main_main_image)
    ImageView main_main_image;
    @Bind(R.id.main_main_text)
    TextView main_main_text;

    @Bind(R.id.main_search_image)
    ImageView main_search_image;
    @Bind(R.id.main_search_text)
    TextView main_search_text;

    @Bind(R.id.main_collection_image)
    ImageView main_collection_image;
    @Bind(R.id.main_collection_text)
    TextView main_collection_text;

    @Bind(R.id.main_order_image)
    ImageView main_order_image;
    @Bind(R.id.main_order_text)
    TextView main_order_text;

    @Bind(R.id.main_my_image)
    ImageView main_my_image;
    @Bind(R.id.main_my_text)
    TextView main_my_text;

    IndexFragment mainFragment=null;
    SearchFragment searchFragment=null;
    CollectionFragment collectionFragment=null;
    OrderFragment orderFragment=null;
    MyFragment myFragment=null;

    //当前选中的title
    String currentTitle="one";
    //当前选中的fragment副本
    Fragment currentFragment=null;

    UserModel userModel=null;

    @Override
    public int initContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        userModel= ACache.get(this).getAsObject("user")!=null?(UserModel) ACache.get(this).getAsObject("user"):null;

        initViews(savedInstanceState);

    }

    private void initViews(Bundle savedInstanceState) {
        //隐藏全部缓存fragment
        List<Fragment> fragmentList=getSupportFragmentManager().getFragments();
        if (fragmentList!=null) {
            for (int i=0;i<fragmentList.size();i++) {
                Fragment fragment=fragmentList.get(i);
                getSupportFragmentManager().beginTransaction().hide(fragment).commit();
            }
        }
        if (savedInstanceState!=null) {
            currentTitle=savedInstanceState.getString("title");
        }
        switchFragment(currentTitle);
    }

    private void switchFragment(String title) {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        if (currentFragment!=null) {
            transaction.hide(currentFragment);
        }
        main_main_image.setImageResource(R.mipmap.tab_main_main_nor);
        main_main_text.setTextColor(Color.parseColor("#fbfbfb"));
        main_search_image.setImageResource(R.mipmap.tab_main_search_nor);
        main_search_text.setTextColor(Color.parseColor("#fbfbfb"));
        main_collection_image.setImageResource(R.mipmap.tab_main_fav_nor);
        main_collection_text.setTextColor(Color.parseColor("#fbfbfb"));
        main_order_image.setImageResource(R.mipmap.tab_main_cart_nor);
        main_order_text.setTextColor(Color.parseColor("#fbfbfb"));
        main_my_image.setImageResource(R.mipmap.tab_main_my_nor);
        main_my_text.setTextColor(Color.parseColor("#fbfbfb"));
        Fragment toFragment = getSupportFragmentManager().findFragmentByTag(title);
        if (toFragment==null) {
            if (title.equals("one")) {
                mainFragment=new IndexFragment();
                transaction.add(R.id.main_fl, mainFragment, "one");
                currentFragment=mainFragment;
            }
            else if (title.equals("two")) {
                searchFragment=new SearchFragment();
                transaction.add(R.id.main_fl, searchFragment, "two");
                currentFragment=searchFragment;
            }
            else if (title.equals("three")) {
                collectionFragment=new CollectionFragment();
                transaction.add(R.id.main_fl, collectionFragment, "three");
                currentFragment=collectionFragment;
            }
            else if (title.equals("four")) {
                orderFragment=new OrderFragment();
                transaction.add(R.id.main_fl, orderFragment, "four");
                currentFragment=orderFragment;
            }
            else if (title.equals("five")) {
                myFragment=new MyFragment();
                transaction.add(R.id.main_fl, myFragment, "five");
                currentFragment=myFragment;
            }
        }
        else {
            transaction.show(toFragment);
            currentFragment=toFragment;
        }
        transaction.commit();
        currentTitle=title;
        if (title.equals("one")) {
            main_main_image.setImageResource(R.mipmap.tab_main_main_sel);
            main_main_text.setTextColor(Color.parseColor("#eb0201"));
        }
        else if (title.equals("two")) {
            main_search_image.setImageResource(R.mipmap.tab_main_search_sel);
            main_search_text.setTextColor(Color.parseColor("#eb0201"));
        }
        else if (title.equals("three")) {
            main_collection_image.setImageResource(R.mipmap.tab_main_fav_sel);
            main_collection_text.setTextColor(Color.parseColor("#eb0201"));
        }
        else if (title.equals("four")) {
            main_order_image.setImageResource(R.mipmap.tab_main_cart_sel);
            main_order_text.setTextColor(Color.parseColor("#eb0201"));
        }
        else if (title.equals("five")) {
            currentFragment=myFragment;
            main_my_image.setImageResource(R.mipmap.tab_main_my_sel);
            main_my_text.setTextColor(Color.parseColor("#eb0201"));
        }
    }

    @OnClick({R.id.main_main, R.id.main_search, R.id.main_collection, R.id.main_order, R.id.main_my})
    public void switchClick(View view) {
        switch (view.getId()) {
            case R.id.main_main:
                switchFragment("one");
                break;
            case R.id.main_search:
                switchFragment("two");
                break;
            case R.id.main_collection:
                if (userModel==null) {
                    Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, ParamUtils.RESULT_LOGIN);
                }
                else {
                    switchFragment("three");
                }
                break;
            case R.id.main_order:
                if (userModel==null) {
                    Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, ParamUtils.RESULT_LOGIN);
                }
                else {
                    switchFragment("four");
                }
                break;
            case R.id.main_my:
                if (userModel==null) {
                    Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, ParamUtils.RESULT_LOGIN);
                }
                else {
                    switchFragment("five");
                }
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("title", currentTitle);
    }

    @Override
    public void onBackPressed() {
        if (currentTitle.equals("one")) {
            boolean flag=((IndexFragment) currentFragment).checkBack();
            if (!flag) {
                finish();
            }
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            if (requestCode==ParamUtils.RESULT_LOGIN) {
                userModel= ACache.get(this).getAsObject("user")!=null?(UserModel) ACache.get(this).getAsObject("user"):null;
            }
            else if(requestCode==ParamUtils.takecamera_result) {
                String filePath=data.getExtras().getString("path");
                cropImage(filePath);
            }
            else if(requestCode==ParamUtils.choicePic_result) {
                Uri uri=data.getData();
                String filePath;
                if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.KITKAT) {
                    filePath= CommonUtils.getPath(uri, this);
                }
                else {
                    String[] projection={MediaStore.Images.Media.DATA};
                    Cursor cs=managedQuery(uri, projection, null, null, null);
                    int columnNumber=cs.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cs.moveToFirst();
                    filePath=cs.getString(columnNumber);
                    filePath.replaceAll("file:///", "/");
                }
                Observable.just(filePath).map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return CommonUtils.getScalePicturePathName(s);
                    }
                }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if(s.equals("")) {
                            showToast("图片过小，请重新选择");
                        }
                        else {
                            cropImage(s);
                        }
                    }
                });
            }
            else if(requestCode==ParamUtils.crop_result) {
                String filePath=data.getExtras().getString("path");
                if (myFragment==null) {
                    return;
                }
                myFragment.refreshAvatar(filePath);
            }
        }
    }
}
