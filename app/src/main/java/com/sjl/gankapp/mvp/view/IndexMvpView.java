package com.sjl.gankapp.mvp.view;

import com.sjl.gankapp.model.pojo.GankDataResult;
import com.sjl.platform.base.MvpView;

import java.util.List;

/**
 * IndexMvpView
 *
 * @author 林zero
 * @date 2018/3/4
 */

public interface IndexMvpView extends MvpView {
    /**
     * 设置数据
     */
    void setGirls(List<GankDataResult> list,int page);
}
