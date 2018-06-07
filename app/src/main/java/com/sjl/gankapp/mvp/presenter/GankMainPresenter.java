package com.sjl.gankapp.mvp.presenter;

import com.sjl.gankapp.BuildConfig;
import com.sjl.gankapp.http.ServiceClient;
import com.sjl.gankapp.model.pojo.VersionInfo;
import com.sjl.gankapp.mvp.view.GankMainMvpView;
import com.sjl.platform.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * GankMainPresenter
 *
 * @author 林zero
 * @date 2018/3/4
 */

public class GankMainPresenter extends BasePresenter<GankMainMvpView> {
    /**
     * 检查版本
     */
    public void checkVersion() {
        addSubscribe(ServiceClient.getUpdateAPI().getVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<VersionInfo>() {
                    @Override
                    public void accept(VersionInfo versionInfo) throws Exception {
                        if (BuildConfig.VERSION_CODE < versionInfo.getVersionCode()) {
                            getMvpView().onCheckVersion(false, versionInfo.getVersionName());
                        } else {
                            getMvpView().onCheckVersion(true, "当前已是最新版本");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpView().autoProgress(false);
                        getMvpView().onCheckVersion(true, "服务异常");
                    }
                })
        );
    }
}
