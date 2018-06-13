package com.sjl.gankapp.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sjl.gankapp.R;
import com.sjl.gankapp.mvp.presenter.AboutPresenter;
import com.sjl.gankapp.mvp.presenter.InfoPresenter;
import com.sjl.gankapp.mvp.view.AboutMvpView;
import com.sjl.gankapp.mvp.view.InfoMvpView;
import com.sjl.platform.base.BaseActivity;

import butterknife.BindView;

/**
 * 关于
 *
 * @author 林zero
 * @date 2018/6/6
 */
public class AboutActivity extends BaseActivity<AboutMvpView, AboutPresenter> implements AboutMvpView {

    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        toolBar.setTitle(R.string.gank_about);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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

    @Override
    public void onClick(View view) {

    }
}
