package com.sjl.gankapp.mvp.view;

import com.sjl.platform.base.MvpView;

/**
 * GankMainMvpView
 *
 * @author 林zero
 * @date 2018/3/4
 */

public interface GankMainMvpView extends MvpView {
    void onCheckVersion(boolean isLatest, String msg);
}
