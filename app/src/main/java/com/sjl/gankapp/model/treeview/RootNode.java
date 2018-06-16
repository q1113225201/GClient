package com.sjl.gankapp.model.treeview;

import com.sjl.gankapp.R;
import com.sjl.libtreeview.bean.LayoutItem;

 /**
  * 根节点数据
  *
  * @author 林zero
  * @date 2018/6/16
  */
public class RootNode implements LayoutItem {
    private String name;

    public RootNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_root;
    }

    @Override
    public int getToggleId() {
        return R.id.tvName;
    }

    @Override
    public int getCheckedId() {
        return 0;
    }

    @Override
    public int getClickId() {
        return 0;
    }
}
