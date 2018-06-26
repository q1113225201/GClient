package com.sjl.gankapp.mvp.view;

import com.sjl.gankapp.model.pojo.CasualDetailResponse;
import com.sjl.gankapp.model.pojo.CategoryResponse;
import com.sjl.gankapp.model.pojo.SmallCategoryResponse;
import com.sjl.libtreeview.bean.TreeNode;
import com.sjl.platform.base.MvpView;

/**
 * 闲读列表
 *
 * @author 林zero
 * @date 2018/6/21
 */

public interface CasualListMvpView extends MvpView {
    void onGetCasualList(CasualDetailResponse casualDetailResponse, int page);

    void refreshProgress(boolean show);

    void onGetCategries(CategoryResponse categoryResponse);

    void onGetSmallCategories(SmallCategoryResponse smallCategoryResponse, TreeNode treeNode);
}
