package com.sjl.gankapp.model.treeview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sjl.gankapp.R;
import com.sjl.libtreeview.bean.TreeNode;
import com.sjl.libtreeview.bean.TreeViewBinder;

 /**
  * 叶布局
  *
  * @author 林zero
  * @date 2018/6/16
  */
public class LeafViewBinder extends TreeViewBinder<LeafViewBinder.ViewHolder> {
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
        return R.id.tvName;
    }

    @Override
    public ViewHolder creatViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindViewHolder(ViewHolder holder, int position, TreeNode treeNode) {
        ((TextView) holder.findViewById(R.id.tvName)).setText(((LeafNode) treeNode.getValue()).getName());
        holder.findViewById(R.id.llParent).setBackgroundColor(holder.itemView.getContext().getResources().getColor(treeNode.isChecked() ? R.color.gankGray : R.color.gankWhite));
    }

    class ViewHolder extends TreeViewBinder.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
