package com.sjl.gankapp.ui.fragment;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sjl.gankapp.R;
import com.sjl.gankapp.model.pojo.CategoryResponse;
import com.sjl.gankapp.model.pojo.SmallCategoryResponse;
import com.sjl.gankapp.model.treeview.LeafNode;
import com.sjl.gankapp.model.treeview.LeafViewBinder;
import com.sjl.gankapp.model.treeview.RootNode;
import com.sjl.gankapp.model.treeview.RootViewBinder;
import com.sjl.gankapp.mvp.presenter.CasualPresenter;
import com.sjl.gankapp.mvp.view.CasualMvpView;
import com.sjl.gankapp.ui.activity.CasualListActivity;
import com.sjl.libtreeview.TreeViewAdapter;
import com.sjl.libtreeview.bean.TreeNode;
import com.sjl.libtreeview.bean.TreeViewBinder;
import com.sjl.platform.base.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;


/**
 * 闲读
 *
 * @author 林zero
 * @date 2018/6/16
 */
public class CasualFragment extends BaseFragment<CasualMvpView, CasualPresenter> implements CasualMvpView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_casual;
    }

    @Override
    protected void initView() {
        srl.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        initAdapter();
        onRefresh();
    }

    private TreeViewAdapter adapter;
    private List<TreeNode> list = new ArrayList<>();

    private void initAdapter() {
        adapter = new TreeViewAdapter(list, Arrays.asList(new RootViewBinder(), new LeafViewBinder())) {
            @Override
            public void toggleClick(TreeViewBinder.ViewHolder viewHolder, View view, boolean isOpen, TreeNode treeNode) {
                if (isOpen && treeNode.getChildNodes().size() == 0) {
                    ((CasualPresenter) mPresenter).getSmallCategories(((RootNode) treeNode.getValue()).getCategoryBean().getEn_name(), treeNode);
                } else {
                    adapter.lastToggleClickToggle();
                }
            }

            @Override
            public void toggled(TreeViewBinder.ViewHolder viewHolder, View view, boolean isOpen, TreeNode treeNode) {
                view.findViewById(R.id.ivNode).setRotation(isOpen ? 90 : 0);
            }

            @Override
            public void checked(TreeViewBinder.ViewHolder viewHolder, View view, boolean b, TreeNode treeNode) {

            }

            @Override
            public void itemClick(TreeViewBinder.ViewHolder viewHolder, View view, TreeNode treeNode) {
                //跳转闲读列表详情
                startActivity(CasualListActivity.newIntent(activity,((LeafNode)treeNode.getValue()).getSmallCategory().getId()));
            }
        };
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
    }

    @Override
    protected CasualMvpView obtainMvpView() {
        return this;
    }

    @Override
    protected CasualPresenter obtainPresenter() {
        mPresenter = new CasualPresenter();
        return (CasualPresenter) mPresenter;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void refreshProgress(boolean show) {
        if (show && srl.isRefreshing()) {
            return;
        }
        srl.setRefreshing(show);
    }

    @Override
    public void onGetCategries(CategoryResponse categoryResponse) {
        for (CategoryResponse.CategoryBean categoryBean : categoryResponse.getResults()) {
            RootNode rootNode = new RootNode(categoryBean.getName());
            rootNode.setCategoryBean(categoryBean);
            TreeNode<RootNode> treeNode = new TreeNode(rootNode);
            list.add(treeNode);
        }
        adapter.notifyData(list);
    }

    @Override
    public void onGetSmallCategories(SmallCategoryResponse smallCategoryResponse, TreeNode treeNode) {
        for (SmallCategoryResponse.SmallCategory smallCategory : smallCategoryResponse.getResults()) {
            LeafNode leafNode = new LeafNode(smallCategory.getTitle());
            leafNode.setSmallCategory(smallCategory);
            TreeNode<LeafNode> leafTreeNode = new TreeNode(leafNode);
            treeNode.addChild(leafTreeNode);
        }
        adapter.lastToggleClickToggle();
    }

    @Override
    public void onRefresh() {
        ((CasualPresenter) mPresenter).getCategories();
    }
}
