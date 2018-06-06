package com.sjl.gankapp.ui.activity;

import android.view.View;

import com.sjl.gankapp.R;
import com.sjl.gankapp.mvp.presenter.InfoPresenter;
import com.sjl.gankapp.mvp.view.InfoMvpView;
import com.sjl.platform.base.BaseActivity;

/**
 * 信息
 *
 * @author 林zero
 * @date 2018/6/6
 */
public class InfoActivity extends BaseActivity<InfoMvpView, InfoPresenter> implements InfoMvpView {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_info;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected InfoMvpView obtainMvpView() {
        return this;
    }

    @Override
    protected InfoPresenter obtainPresenter() {
        mPresenter = new InfoPresenter();
        return (InfoPresenter) mPresenter;
    }

    @Override
    public void onClick(View view) {

    }
}
