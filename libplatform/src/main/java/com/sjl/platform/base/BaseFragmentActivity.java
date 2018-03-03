package com.sjl.platform.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Window;

import com.sjl.platform.PlatformInit;
import com.sjl.platform.util.PermisstionUtil;
import com.sjl.platform.util.ToastUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

/**
 * BaseActivity
 *
 * @author SJL
 * @date 2017/11/29
 */

public abstract class BaseFragmentActivity<V extends MvpView, P extends Presenter> extends FragmentActivity implements MvpView {
    protected Context mContext;
    protected Presenter presenter;
    /**
     * 布局文件
     */
    protected abstract int getContentViewId();

    /**
     * 初始化
     */
    protected abstract void initView();

    /**
     * 获取视图
     */
    protected abstract V obtainMvpView();

    /**
     * 获取Presenter
     */
    protected abstract P obtainPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getContentViewId());
        PlatformInit.pushActivity(this);
        obtainPresenter();
        if (presenter != null) {
            presenter.attachView(obtainMvpView());
        }
        mContext = this;
        //统计应用启动数据
        PushAgent.getInstance(mContext).onAppStart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermisstionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        mContext = null;
        if (presenter != null) {
            presenter.detachView();
        }
        PlatformInit.popActivity(this);
        super.onDestroy();
    }

    @Override
    public void autoProgress(boolean show) {

    }

    @Override
    public void showToast(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            ToastUtil.showToast(PlatformInit.application, msg);
        }
    }
}
