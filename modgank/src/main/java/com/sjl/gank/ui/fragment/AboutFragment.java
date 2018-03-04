package com.sjl.gank.ui.fragment;


import com.sjl.gank.R;
import com.sjl.gank.mvp.presenter.AboutPresenter;
import com.sjl.gank.mvp.view.AboutMvpView;
import com.sjl.platform.base.BaseFragment;

/**
 * 我的Fragment
 *
 * @author SJL
 * @date 2017/11/30
 */
public class AboutFragment extends BaseFragment<AboutMvpView, AboutPresenter> implements AboutMvpView {
    private static final String TAG = "AboutFragment";

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_about;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected AboutMvpView obtainMvpView() {
        return this;
    }

    @Override
    protected AboutPresenter obtainPresenter() {
        mPresenter = new AboutPresenter();
        return (AboutPresenter) mPresenter;
    }
}
