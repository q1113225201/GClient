package com.sjl.platform.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

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
        removeSubscribe();
    }

    private CompositeDisposable compositeDisposable;

    protected void addSubscribe(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    private void removeSubscribe() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}
