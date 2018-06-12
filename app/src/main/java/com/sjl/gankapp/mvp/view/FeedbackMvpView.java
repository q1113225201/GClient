package com.sjl.gankapp.mvp.view;

import com.sjl.platform.base.MvpView;

/**
 * 反馈
 *
 * @author 林zero
 * @date 2018/6/12
 */

public interface FeedbackMvpView extends MvpView {
    void onSubmitFeedback(boolean isSuccess,String msg);
}
