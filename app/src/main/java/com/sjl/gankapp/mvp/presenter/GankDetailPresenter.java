package com.sjl.gankapp.mvp.presenter;

import com.sjl.gankapp.bean.GankDataResult;
import com.sjl.gankapp.bean.GankDayData;
import com.sjl.gankapp.bean.GankDayDataResult;
import com.sjl.gankapp.http.ServiceClient;
import com.sjl.gankapp.mvp.view.GankDetailMvpView;
import com.sjl.platform.base.BasePresenter;
import com.sjl.platform.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * GankDetailPresenter
 *
 * @author 林zero
 * @date 2018/3/4
 */

public class GankDetailPresenter extends BasePresenter<GankDetailMvpView> {
    private static final String TAG = "GankDetailPresenter";

    /**
     * 获取指定日期数据
     * @param date
     */
    public void getGankDetail(String date){
        ServiceClient.getGankAPI().getDayData(date)
                .map(new Function<GankDayData, List<GankDataResult>>() {
                    @Override
                    public List<GankDataResult> apply(GankDayData gankDayData) throws Exception {
                        LogUtil.i(TAG, gankDayData);
                        //将获取到的数据添加到一个列表中
                        GankDayDataResult gankDayDataResult = gankDayData.getResults();
                        List<GankDataResult> results = new ArrayList<>();
                        if (gankDayDataResult.getAndroid() != null) {
                            results.addAll(gankDayDataResult.getAndroid());
                        }
                        if (gankDayDataResult.getiOS() != null) {
                            results.addAll(gankDayDataResult.getiOS());
                        }
                        if (gankDayDataResult.get前端() != null) {
                            results.addAll(gankDayDataResult.get前端());
                        }
                        if (gankDayDataResult.get拓展资源() != null) {
                            results.addAll(gankDayDataResult.get拓展资源());
                        }
                        if (gankDayDataResult.get瞎推荐() != null) {
                            results.addAll(gankDayDataResult.get瞎推荐());
                        }
                        if (gankDayDataResult.get休息视频() != null) {
                            results.addAll(gankDayDataResult.get休息视频());
                        }
                        return results;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<GankDataResult>>() {
                    @Override
                    public void accept(List<GankDataResult> list) throws Exception {
                        getMvpView().setGankDetail(list);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.e(TAG, throwable.getMessage());
                    }
                });
    }
}
