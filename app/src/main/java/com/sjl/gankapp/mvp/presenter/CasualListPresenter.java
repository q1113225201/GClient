package com.sjl.gankapp.mvp.presenter;

import com.sjl.gankapp.http.ServiceClient;
import com.sjl.gankapp.model.pojo.CasualDetailResponse;
import com.sjl.gankapp.model.pojo.CategoryResponse;
import com.sjl.gankapp.model.pojo.SmallCategoryResponse;
import com.sjl.gankapp.mvp.view.CasualListMvpView;
import com.sjl.libtreeview.bean.TreeNode;
import com.sjl.platform.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 闲读列表
 *
 * @author 林zero
 * @date 2018/6/21
 */

public class CasualListPresenter extends BasePresenter<CasualListMvpView> {
    /**
     * 获取闲读数据
     */
    public void getCasualList(String id, int pageSize, final int currentPage) {
        if(currentPage==1){
            getMvpView().refreshProgress(true);
        }else {
            getMvpView().autoProgress(true);
        }
        addSubscribe(ServiceClient.getGankAPI().getCasualList(id, pageSize, currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CasualDetailResponse>() {
                    @Override
                    public void accept(CasualDetailResponse casualDetailResponse) throws Exception {
                        if(currentPage==1){
                            getMvpView().refreshProgress(false);
                        }else {
                            getMvpView().autoProgress(false);
                        }
                        getMvpView().onGetCasualList(casualDetailResponse,currentPage);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if(currentPage==1){
                            getMvpView().refreshProgress(false);
                        }else {
                            getMvpView().autoProgress(false);
                        }
                        getMvpView().showToast("网络异常，" + throwable.getMessage());
                    }
                }));
    }

    /**
     * 获取闲读大类
     */
    public void getCategories() {
        addSubscribe(ServiceClient.getGankAPI().getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CategoryResponse>() {
                    @Override
                    public void accept(CategoryResponse categoryResponse) throws Exception {
                        getMvpView().onGetCategries(categoryResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }
    /**
     * 获取闲读小类
     */
    public void getSmallCategories(String category, final TreeNode treeNode) {
        getMvpView().autoProgress(true);
        addSubscribe(ServiceClient.getGankAPI().getSmallCategories(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SmallCategoryResponse>() {
                    @Override
                    public void accept(SmallCategoryResponse smallCategoryResponse) throws Exception {
                        getMvpView().autoProgress(false);
                        getMvpView().onGetSmallCategories(smallCategoryResponse,treeNode);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpView().autoProgress(false);
                        getMvpView().showToast("网络异常，" + throwable.getMessage());
                    }
                }));
    }
}
