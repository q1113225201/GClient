package com.sjl.platform.base;

/**
 * Presenter
 *
 * @author 林zero
 * @date 2018/3/3
 */

public interface Presenter<V extends MvpView> {
    void attachView(V mvpView);

    void detachView();
}
