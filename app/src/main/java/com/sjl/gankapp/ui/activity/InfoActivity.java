package com.sjl.gankapp.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sjl.gankapp.R;
import com.sjl.gankapp.mvp.presenter.InfoPresenter;
import com.sjl.gankapp.mvp.view.InfoMvpView;
import com.sjl.platform.base.BaseActivity;

import butterknife.BindView;

/**
 * 信息
 *
 * @author 林zero
 * @date 2018/6/6
 */
public class InfoActivity extends BaseActivity<InfoMvpView, InfoPresenter> implements InfoMvpView {

    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_info;
    }

    @Override
    protected void initView() {
        toolBar.setTitle(R.string.gank_info);
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
