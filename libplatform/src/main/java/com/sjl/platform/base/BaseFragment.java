package com.sjl.platform.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sjl.platform.PlatformInit;
import com.sjl.platform.util.ToastUtil;

/**
 * BaseFragment
 *
 * @author SJL
 * @date 2017/11/30
 */

public abstract class BaseFragment<V extends MvpView,P extends Presenter> extends Fragment implements MvpView {
    protected View view;
    protected Context mContext;
    protected Presenter mPresenter;

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
        mContext = getActivity();
        view = inflater.inflate(getContentViewId(),container,false);
        initView();
        return view;
    }

    @Override
    public void onDestroyView() {
        if (view != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        mContext = null;
        if(mPresenter!=null){
            mPresenter.detachView();
        }
        super.onDestroyView();
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
