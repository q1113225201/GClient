package com.sjl.gankapp.ui.activity;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.sjl.gankapp.R;
import com.sjl.gankapp.mvp.presenter.FeedbackPresenter;
import com.sjl.gankapp.mvp.view.FeedbackMvpView;
import com.sjl.platform.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 反馈
 *
 * @author 林zero
 * @date 2018/6/12
 */
public class FeedbackActivity extends BaseActivity<FeedbackMvpView, FeedbackPresenter> implements FeedbackMvpView {

    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.etOpinion)
    EditText etOpinion;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView() {
        toolBar.setTitle(R.string.gank_info);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    protected FeedbackMvpView obtainMvpView() {
        return this;
    }

    @Override
    protected FeedbackPresenter obtainPresenter() {
        mPresenter = new FeedbackPresenter();
        return (FeedbackPresenter) mPresenter;
    }

    @OnClick({R.id.btnSubmit})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                String opinion = etOpinion.getText().toString().trim();
                if (TextUtils.isEmpty(opinion)) {
                    showToast("请输入意见反馈");
                    return;
                }
                ((FeedbackPresenter) mPresenter).submitFeedback(opinion);
                break;
        }
    }

    @Override
    public void onSubmitFeedback(boolean isSuccess, String msg) {
        showToast(msg);
        if (isSuccess) {
            onBackPressed();
        }
    }
}
