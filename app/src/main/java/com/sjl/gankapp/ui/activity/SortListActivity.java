package com.sjl.gankapp.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sjl.gankapp.R;
import com.sjl.gankapp.model.Constant;
import com.sjl.gankapp.model.GankConfig;
import com.sjl.gankapp.model.pojo.GankDataResult;
import com.sjl.gankapp.mvp.presenter.SortListPresenter;
import com.sjl.gankapp.mvp.view.SortListMvpView;
import com.sjl.gankapp.util.GankUtil;
import com.sjl.platform.base.BaseActivity;
import com.sjl.platform.base.adapter.CommonRVAdapter;
import com.sjl.platform.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 分类列表页面
 *
 * @author SJL
 * @date 2017/12/14
 */
public class SortListActivity extends BaseActivity<SortListMvpView, SortListPresenter> implements SortListMvpView {
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.rvSort)
    RecyclerView rvSort;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    private String type;

    private CommonRVAdapter<GankDataResult> adapter;
    private List<GankDataResult> gankDataResultList;

    private int currentPage = 1;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_sort_list;
    }

    @Override
    protected void initView() {
        initToolBar();
        type = getIntent().getStringExtra(Constant.TYPE);
        srl = findViewById(R.id.srl);
        rvSort = findViewById(R.id.rvSort);

        srl.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                ((SortListPresenter) mPresenter).getSortList(type, currentPage);
            }
        });

        initSortList();
        ((SortListPresenter) mPresenter).getSortList(type, currentPage);
    }

    @Override
    protected SortListMvpView obtainMvpView() {
        return this;
    }

    @Override
    protected SortListPresenter obtainPresenter() {
        mPresenter = new SortListPresenter();
        return (SortListPresenter) mPresenter;
    }

    private void initToolBar() {
        toolBar.setTitle(type);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                type = item.getTitle().toString();
                toolBar.setTitle(type);
                currentPage = 1;
                adapter.removeAll();
                ((SortListPresenter) mPresenter).getSortList(type, currentPage);
                return false;
            }
        });
    }

    private void initSortList() {
        gankDataResultList = new ArrayList<>();
        adapter = new CommonRVAdapter<GankDataResult>(activity, gankDataResultList, R.layout.item_sort, R.layout.item_sort_empty) {
            @Override
            protected void onBindNullViewHolder(RecyclerView.Adapter adapter, RVViewHolder viewHolder, int position, GankDataResult item, List<GankDataResult> list) {

            }

            @Override
            protected void onBindViewHolder(RecyclerView.Adapter adapter, RVViewHolder viewHolder, int position, final GankDataResult item, List<GankDataResult> list) {
                String imgUrl = "";
                if (GankConfig.WELFARE.equalsIgnoreCase(type)) {
                    viewHolder.findViewById(R.id.llItemType).setVisibility(View.GONE);
                    viewHolder.findViewById(R.id.llItemContent).setVisibility(View.GONE);
                    viewHolder.findViewById(R.id.rlItemImg).setVisibility(View.VISIBLE);
                    imgUrl = item.getUrl();
                } else {
                    viewHolder.findViewById(R.id.llItemType).setVisibility(GankConfig.ALL.equalsIgnoreCase(type) ? View.VISIBLE : View.GONE);
                    viewHolder.findViewById(R.id.llItemContent).setVisibility(View.VISIBLE);
                    ((TextView) viewHolder.findViewById(R.id.tvItemType)).setText(item.getType());
                    ((TextView) viewHolder.findViewById(R.id.tvItemDesc)).setText(item.getDesc());
                    ((TextView) viewHolder.findViewById(R.id.tvItemTimeAuth)).setText(String.format("%s\n%s", GankUtil.parseDate(item.getPublishedAt()), item.getWho()));
                    if (item.getImages() != null && item.getImages().size() > 0) {
                        imgUrl = item.getImages().get(0);
                    }
                    if (GankConfig.WELFARE.equalsIgnoreCase(item.getType())) {
                        viewHolder.findViewById(R.id.llItemType).setVisibility(View.GONE);
                        viewHolder.findViewById(R.id.llItemContent).setVisibility(View.GONE);
                        imgUrl = item.getUrl();
                    }
                }
                viewHolder.findViewById(R.id.rlItemImg).setVisibility(TextUtils.isEmpty(imgUrl) ? View.GONE : View.VISIBLE);
                Glide.with(activity).load(imgUrl).error(R.mipmap.ic_logo).into(((ImageView) viewHolder.findViewById(R.id.ivItemImg)));
                viewHolder.findViewById(R.id.cvItemSort).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        if (GankConfig.WELFARE.equalsIgnoreCase(item.getType())) {
                            bundle.putString(Constant.IMAGE_URL, item.getUrl());
                            AppUtil.startActivity(activity, v, ImageActivity.class, bundle);
                        } else {
                            bundle.putString(Constant.TITLE, item.getDesc());
                            bundle.putString(Constant.URL, item.getUrl());
                            AppUtil.startActivity(activity, v, WebActivity.class, bundle);
                        }
                    }
                });
            }
        };
        rvSort.setAdapter(adapter);
        rvSort.setLayoutManager(new LinearLayoutManager(this));
        rvSort.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager.findLastVisibleItemPosition() >= adapter.getItemCount() - GankConfig.PAGE_SIZE / 2) {
                    ((SortListPresenter) mPresenter).getSortList(type, currentPage);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sort, menu);
        return true;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void setSortList(List<GankDataResult> list, int page) {
        if (page == 1) {
            adapter.flush(list);
        } else {
            adapter.addList(list);
        }
        currentPage++;
    }

    @Override
    public void autoProgress(final boolean show) {
//        super.autoProgress(show);
        srl.post(new Runnable() {
            @Override
            public void run() {
                if (show && srl.isRefreshing()) {
                    return;
                }
                srl.setRefreshing(show);
            }
        });
    }
}
