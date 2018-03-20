package com.sjl.platform.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sjl.platform.PlatformInit;
import com.sjl.platform.util.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * BaseFragment
 *
 * @author SJL
 * @date 2017/11/30
 */

public abstract class BaseFragment<V extends MvpView,P extends Presenter> extends Fragment implements MvpView,View.OnClickListener {
    protected View view;
    protected Activity activity;
    protected Presenter mPresenter;
    private Unbinder unbinder;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        obtainPresenter();
        if(mPresenter!=null){
            mPresenter.attachView(obtainMvpView());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = getActivity();
        view = inflater.inflate(getContentViewId(),container,false);
        unbinder = ButterKnife.bind(this,view);
        initView();
        return view;
    }

    @Override
    public void onDestroyView() {
        if (view != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        activity = null;
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if(mPresenter!=null){
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    @Override
    public void showToast(String msg) {
        if(!TextUtils.isEmpty(msg)) {
            ToastUtil.showToast(PlatformInit.application, msg);
        }
    }

    @Override
    public void autoProgress(boolean show) {

    }
}
