package com.sjl.gankapp.mvp.presenter;

import android.os.Build;

import com.sjl.gankapp.BuildConfig;
import com.sjl.gankapp.model.pojo.Feedback;
import com.sjl.gankapp.mvp.view.FeedbackMvpView;
import com.sjl.platform.base.BasePresenter;
import com.sjl.platform.util.LogUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 反馈
 *
 * @author 林zero
 * @date 2018/6/12
 */

public class FeedbackPresenter extends BasePresenter<FeedbackMvpView> {
    private static final String TAG = "FeedbackPresenter";

    public void submitFeedback(String opinion) {
        getMvpView().autoProgress(true);
        Feedback feedback = new Feedback();
        feedback.setOpinion(opinion);
        feedback.setPhone(Build.MODEL);
        feedback.setSdk(Build.VERSION.SDK_INT);
        feedback.setVersion(BuildConfig.VERSION_NAME);
        feedback.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                getMvpView().autoProgress(false);
                getMvpView().onSubmitFeedback(e == null, e == null ? "提交成功" : "提交失败，" + e.getMessage());
                LogUtil.i(TAG, s);
            }
        });
    }
}
