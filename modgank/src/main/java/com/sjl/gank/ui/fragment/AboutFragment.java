package com.sjl.gank.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sjl.gank.R;
import com.sjl.platform.base.BaseFragment;
import com.sjl.platform.base.MvpView;
import com.sjl.platform.base.Presenter;

/**
 * 我的Fragment
 *
 * @author SJL
 * @date 2017/11/30
 */
public class AboutFragment extends BaseFragment {
    private static final String TAG = "AboutFragment";

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_about;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected MvpView obtainMvpView() {
        return this;
    }

    @Override
    protected Presenter obtainPresenter() {
        return null;
    }
}
