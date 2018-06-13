package com.sjl.gankapp.ui.fragment;


import android.view.View;
import android.widget.TextView;

import com.sjl.gankapp.BuildConfig;
import com.sjl.gankapp.R;
import com.sjl.gankapp.model.event.EventClick;
import com.sjl.gankapp.mvp.presenter.AboutPresenter;
import com.sjl.gankapp.mvp.presenter.InfoPresenter;
import com.sjl.gankapp.mvp.view.AboutMvpView;
import com.sjl.gankapp.mvp.view.InfoMvpView;
import com.sjl.gankapp.ui.activity.AboutActivity;
import com.sjl.gankapp.ui.activity.FeedbackActivity;
import com.sjl.platform.PlatformInit;
import com.sjl.platform.base.BaseFragment;
import com.sjl.platform.util.CommonUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 信息Fragment
 *
 * @author SJL
 * @date 2017/11/30
 */
public class InfoFragment extends BaseFragment<InfoMvpView, InfoPresenter> implements InfoMvpView {
    private static final String TAG = "InfoFragment";
    @BindView(R.id.tvVersion)
    TextView tvVersion;
    @BindView(R.id.tvCheck)
    TextView tvCheck;
    @BindView(R.id.tvFeedback)
    TextView tvFeedback;
    @BindView(R.id.tvAbout)
    TextView tvAbout;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_info;
    }

    @Override
    protected void initView() {
        tvVersion.setText(BuildConfig.VERSION_NAME);
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

    @OnClick({R.id.tvCheck, R.id.tvFeedback, R.id.tvAbout})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCheck:
                PlatformInit.getEventBus().post(new EventClick("checkVersion", null));
                break;
            case R.id.tvFeedback:
                CommonUtil.startActivity(activity, v, FeedbackActivity.class, null);
                break;
            case R.id.tvAbout:
                CommonUtil.startActivity(activity, v, AboutActivity.class, null);
                break;
        }
    }
}
