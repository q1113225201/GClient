package com.sjl.platform.base;

/**
 * BasePresenter
 *
 * @author 林zero
 * @date 2018/3/3
 */

public class BasePresenter<T extends MvpView> implements Presenter<T> {
    private T mvpView;

    public T getMvpView() {
        return mvpView;
    }

    @Override
    public void attachView(T mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        mvpView = null;
    }
}
