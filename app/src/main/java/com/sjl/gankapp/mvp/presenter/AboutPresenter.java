package com.sjl.gankapp.mvp.presenter;

import com.sjl.gankapp.http.ServiceClient;
import com.sjl.gankapp.model.pojo.AboutInfo;
import com.sjl.gankapp.mvp.view.AboutMvpView;
import com.sjl.platform.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * AboutPresenter
 *
 * @author 林zero
 * @date 2018/3/4
 */

public class AboutPresenter extends BasePresenter<AboutMvpView> {
    /**
     * 获取关于信息
     */
    public void getAboutInfo() {
        getMvpView().autoProgress(true);
        addSubscribe(ServiceClient.getUpdateAPI().getAboutInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AboutInfo>() {
                    @Override
                    public void accept(AboutInfo aboutInfo) throws Exception {
                        getMvpView().autoProgress(false);
                        getMvpView().onGetAboutInfo(true, aboutInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpView().autoProgress(false);
                        getMvpView().onGetAboutInfo(false, null);
                    }
                }));
    }
}
