package com.renyu.carclient.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.renyu.carclient.R;
import com.renyu.carclient.adapter.SearchBrandAdapter;
import com.renyu.carclient.adapter.SearchCategoryAdapter;
import com.renyu.carclient.base.BaseFragment;
import com.renyu.carclient.commons.CommonUtils;
import com.renyu.carclient.model.SearchBrandModel;
import com.renyu.carclient.myview.LetterIndicatorView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by renyu on 15/10/18.
 */
public class CartypeFragment extends BaseFragment {

    @Override
    public int initContentView() {
        return 0;
    }
}
