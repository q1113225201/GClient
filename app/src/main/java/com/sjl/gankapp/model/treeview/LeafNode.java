package com.sjl.gankapp.model.treeview;

import com.sjl.gankapp.R;
import com.sjl.gankapp.model.pojo.SmallCategoryResponse;
import com.sjl.libtreeview.bean.LayoutItem;

/**
 * 叶节点数据
 *
 * @author 林zero
 * @date 2018/6/16
 */
public class LeafNode implements LayoutItem {
    private String name;
    private SmallCategoryResponse.SmallCategory smallCategory;

    public LeafNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SmallCategoryResponse.SmallCategory getSmallCategory() {
        return smallCategory;
    }

    public void setSmallCategory(SmallCategoryResponse.SmallCategory smallCategory) {
        this.smallCategory = smallCategory;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_leaf;
    }

    @Override
    public int getToggleId() {
        return 0;
    }

    @Override
    public int getCheckedId() {
        return 0;
    }

    @Override
    public int getClickId() {
        return R.id.rlParent;
    }
}
