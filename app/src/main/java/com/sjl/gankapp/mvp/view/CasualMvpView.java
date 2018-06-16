package com.sjl.gankapp.mvp.view;

import com.sjl.gankapp.model.pojo.CategoryResponse;
import com.sjl.gankapp.model.pojo.SmallCategoryResponse;
import com.sjl.platform.base.MvpView;

/**
 * CasualMvpView
 *
 * @author 林zero
 * @date 2018/6/16
 */

public interface CasualMvpView extends MvpView {
    void refreshProgress(boolean show);

    void onGetCategries(CategoryResponse categoryResponse);

    void onGetSmallCategories(SmallCategoryResponse smallCategoryResponse);
}
