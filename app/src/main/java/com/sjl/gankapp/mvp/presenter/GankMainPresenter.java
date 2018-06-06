package com.sjl.gankapp.mvp.presenter;

import com.sjl.gankapp.BuildConfig;
import com.sjl.gankapp.mvp.view.GankMainMvpView;
import com.sjl.platform.base.BasePresenter;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * GankMainPresenter
 *
 * @author 林zero
 * @date 2018/3/4
 */

public class GankMainPresenter extends BasePresenter<GankMainMvpView> {
    public void checkVersion() {
        try {
            getMvpView().autoProgress(true);
            Response response = new OkHttpClient()
                    .newCall(new Request.Builder().url("").build())
                    .execute();
            String result = response.body().toString();
            if (BuildConfig.VERSION_CODE < Integer.valueOf(result.split("|")[1])) {
                getMvpView().onCheckVersion(false, result.split("|")[0]);
            } else {
                getMvpView().onCheckVersion(true, "当前已是最新版本");
            }
        } catch (IOException e) {
            getMvpView().onCheckVersion(true, "服务异常");
            e.printStackTrace();
        } finally {
            getMvpView().autoProgress(false);
        }
    }
}
