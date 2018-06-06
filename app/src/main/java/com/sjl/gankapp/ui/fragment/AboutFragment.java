package com.sjl.gankapp.ui.fragment;


import android.view.View;
import android.widget.TextView;

import com.sjl.gankapp.BuildConfig;
import com.sjl.gankapp.R;
import com.sjl.gankapp.model.event.EventClick;
import com.sjl.gankapp.mvp.presenter.AboutPresenter;
import com.sjl.gankapp.mvp.view.AboutMvpView;
import com.sjl.gankapp.ui.activity.InfoActivity;
import com.sjl.platform.PlatformInit;
import com.sjl.platform.base.BaseFragment;
import com.sjl.platform.util.CommonUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的Fragment
 *
 * @author SJL
 * @date 2017/11/30
 */
public class AboutFragment extends BaseFragment<AboutMvpView, AboutPresenter> implements AboutMvpView {
    private static final String TAG = "AboutFragment";
    @BindView(R.id.tvVersion)
    TextView tvVersion;
    @BindView(R.id.tvCheck)
    TextView tvCheck;
    @BindView(R.id.tvFeedback)
    TextView tvFeedback;
    @BindView(R.id.tvInfo)
    TextView tvInfo;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_about;
    }

    @Override
    protected void initView() {
        tvVersion.setText(BuildConfig.VERSION_NAME);
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

    @OnClick({R.id.tvCheck, R.id.tvFeedback, R.id.tvInfo})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCheck:
                PlatformInit.getEventBus().post(new EventClick("checkVersion",null));
                break;
            case R.id.tvFeedback:

                break;
            case R.id.tvInfo:
                CommonUtil.startActivity(activity, v, InfoActivity.class, null);
                break;
        }
    }
}
