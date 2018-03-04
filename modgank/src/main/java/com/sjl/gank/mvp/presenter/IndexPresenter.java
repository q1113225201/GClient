package com.sjl.gank.mvp.presenter;

import com.sjl.gank.bean.GankData;
import com.sjl.gank.bean.GankDataResult;
import com.sjl.gank.config.GankConfig;
import com.sjl.gank.http.ServiceClient;
import com.sjl.gank.mvp.view.IndexMvpView;
import com.sjl.platform.base.BasePresenter;
import com.sjl.platform.base.db.DBManager;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * IndexPresenter
 *
 * @author 林zero
 * @date 2018/3/4
 */

public class IndexPresenter extends BasePresenter<IndexMvpView> {
    private static final String TAG = "IndexPresenter";

    public void getLocalGirls() {
        List<GankDataResult> list = DBManager.getInstance().getList(GankDataResult.class, "", String.format("publishedAt desc limit %d,%d", 0, GankConfig.PAGE_SIZE));
        getMvpView().setGirls(list, -1);
    }

    public void getNetGirls(final int page) {
        if (page == 1) {
            getMvpView().autoProgress(true);
        }
        ServiceClient.getGankAPI().getSortDataByPages(GankConfig.WELFARE, GankConfig.PAGE_SIZE, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GankData>() {
                    @Override
                    public void accept(GankData gankData) throws Exception {
                        if (page == 1) {
                            saveGirls(gankData.getResults());
                        }
                        getMvpView().setGirls(gankData.getResults(), page);
                        getMvpView().autoProgress(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpView().autoProgress(false);
                    }
                });
    }

    private void saveGirls(List<GankDataResult> results) {
        for (GankDataResult item : results) {
            DBManager.getInstance().merger(item, String.format("_id='%s'", item.get_id()));
        }
    }
}