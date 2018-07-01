package com.sjl.gankapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sjl.gankapp.R;
import com.sjl.gankapp.config.GankConfig;
import com.sjl.gankapp.model.pojo.CasualDetailResponse;
import com.sjl.gankapp.model.pojo.CategoryResponse;
import com.sjl.gankapp.model.pojo.SmallCategoryResponse;
import com.sjl.gankapp.model.treeview.LeafNode;
import com.sjl.gankapp.model.treeview.LeafViewBinder;
import com.sjl.gankapp.model.treeview.RootNode;
import com.sjl.gankapp.model.treeview.RootViewBinder;
import com.sjl.gankapp.mvp.presenter.CasualListPresenter;
import com.sjl.gankapp.mvp.presenter.CasualPresenter;
import com.sjl.gankapp.mvp.view.CasualListMvpView;
import com.sjl.gankapp.util.GankUtil;
import com.sjl.gankapp.widget.GlideCircleTransform;
import com.sjl.libtreeview.TreeViewAdapter;
import com.sjl.libtreeview.bean.TreeNode;
import com.sjl.libtreeview.bean.TreeViewBinder;
import com.sjl.platform.base.BaseActivity;
import com.sjl.platform.base.adapter.CommonRVAdapter;
import com.sjl.platform.util.DateUtil;
import com.sjl.platform.util.JsonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 闲读列表
 *
 * @author 林zero
 * @date 2018/6/21
 */
public class CasualListActivity extends BaseActivity<CasualListMvpView, CasualListPresenter> implements CasualListMvpView, SwipeRefreshLayout.OnRefreshListener {
    private static final String CASUAL_TYPE = "casual_type";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.ivIcon)
    ImageView ivIcon;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.rvCasual)
    RecyclerView rvCasual;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.llBottom)
    LinearLayout llBottom;
    @BindView(R.id.rvCasualType)
    RecyclerView rvCasualType;

    private BottomSheetBehavior bottomSheetBehavior;

    private List<CasualDetailResponse.CasualDetailBean> list = new ArrayList<>();
    private CommonRVAdapter<CasualDetailResponse.CasualDetailBean> adapter;

    private SmallCategoryResponse.SmallCategory smallCategory;
    private int currentPage = 1;
    private String currentDate;

    public static Intent newIntent(Context context, SmallCategoryResponse.SmallCategory casualType) {
        Intent intent = new Intent(context, CasualListActivity.class);
        intent.putExtra(CASUAL_TYPE, JsonUtils.toJson(casualType));
        return intent;
    }

    private void parseIntent() {
        smallCategory = JsonUtils.toObject(getIntent().getStringExtra(CASUAL_TYPE), SmallCategoryResponse.SmallCategory.class);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_casual_list;
    }

    @Override
    protected void initView() {
        toolBar.setTitle("闲读");
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        parseIntent();
        srl.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        srl.setOnRefreshListener(this);
        adapter = new CommonRVAdapter<CasualDetailResponse.CasualDetailBean>(activity, list, R.layout.item_casual_detail, R.layout.item_casual_detail_empty) {
            @Override
            protected void onBindNullViewHolder(RecyclerView.Adapter adapter, RVViewHolder viewHolder, int position, CasualDetailResponse.CasualDetailBean item, List<CasualDetailResponse.CasualDetailBean> list) {

            }

            @Override
            protected void onBindViewHolder(RecyclerView.Adapter adapter, RVViewHolder viewHolder, int position, final CasualDetailResponse.CasualDetailBean item, List<CasualDetailResponse.CasualDetailBean> list) {
                ((TextView) viewHolder.findViewById(R.id.tvTitle)).setText(item.getTitle());
                ((TextView) viewHolder.findViewById(R.id.tvDate)).setText(GankUtil.caluateDate(GankUtil.parseDate(item.getPublished_at()), currentDate));
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(WebActivity.newIntent(activity, item.getTitle(), item.getUrl()));
                    }
                });
            }

        };
        rvCasual.setAdapter(adapter);
        rvCasual.setLayoutManager(new LinearLayoutManager(activity));
        rvCasual.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager.findLastVisibleItemPosition() >= adapter.getItemCount() - GankConfig.PAGE_SIZE / 2) {
                    ((CasualListPresenter) mPresenter).getCasualList(smallCategory.getId(), GankConfig.PAGE_SIZE, currentPage);
                }
            }
        });

        bottomSheetBehavior = BottomSheetBehavior.from(llBottom);
        initAdapter();
        currentDate = DateUtil.format(System.currentTimeMillis(), "yyyy/MM/dd");
        initSelected(smallCategory);
        getCasualTypeList();
    }

    private void getCasualTypeList() {
        ((CasualListPresenter) mPresenter).getCategories();
    }

    private TreeViewAdapter typeAdapter;
    private List<TreeNode> treeNodeList = new ArrayList<>();

    private void initAdapter() {
        typeAdapter = new TreeViewAdapter(treeNodeList, Arrays.asList(new RootViewBinder(), new LeafViewBinder())) {
            @Override
            public void toggleClick(TreeViewBinder.ViewHolder viewHolder, View view, boolean isOpen, TreeNode treeNode) {
                if (isOpen && treeNode.getChildNodes().size() == 0) {
                    ((CasualListPresenter) mPresenter).getSmallCategories(((RootNode) treeNode.getValue()).getCategoryBean().getEn_name(), treeNode);
                } else {
                    typeAdapter.lastToggleClickToggle();
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
                smallCategory = ((LeafNode) treeNode.getValue()).getSmallCategory();
                initSelected(smallCategory);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        };
        rvCasualType.setAdapter(typeAdapter);
        rvCasualType.setLayoutManager(new LinearLayoutManager(activity));
        rvCasualType.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
    }

    private void initSelected(SmallCategoryResponse.SmallCategory smallCategory) {
        tvName.setText(smallCategory.getTitle());
        Glide.with(activity)
                .load(smallCategory.getIcon())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .bitmapTransform(new GlideCircleTransform(activity))
                .into(ivIcon);
        onRefresh();
    }

    @Override
    protected CasualListMvpView obtainMvpView() {
        return this;
    }

    @Override
    protected CasualListPresenter obtainPresenter() {
        mPresenter = new CasualListPresenter();
        return (CasualListPresenter) mPresenter;
    }

    @OnClick({R.id.tvChoose})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvChoose:
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    if(treeNodeList.size()==0){
                        getCasualTypeList();
                    }
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                break;
        }
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        ((CasualListPresenter) mPresenter).getCasualList(smallCategory.getId(), GankConfig.PAGE_SIZE, currentPage);
    }

    @Override
    public void onGetCasualList(CasualDetailResponse casualDetailResponse, int page) {
        if (page <= 1) {
            rvCasual.scrollToPosition(0);
            adapter.flush(casualDetailResponse.getResults());
        } else {
            adapter.addList(casualDetailResponse.getResults());
        }
        currentPage = page + 1;
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
            treeNodeList.add(treeNode);
        }
        typeAdapter.notifyData(treeNodeList);
    }

    @Override
    public void onGetSmallCategories(SmallCategoryResponse smallCategoryResponse, TreeNode treeNode) {
        for (SmallCategoryResponse.SmallCategory smallCategory : smallCategoryResponse.getResults()) {
            LeafNode leafNode = new LeafNode(smallCategory.getTitle());
            leafNode.setSmallCategory(smallCategory);
            TreeNode<LeafNode> leafTreeNode = new TreeNode(leafNode);
            treeNode.addChild(leafTreeNode);
        }
        typeAdapter.lastToggleClickToggle();
    }
}
