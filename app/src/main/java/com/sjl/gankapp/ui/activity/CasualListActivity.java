package com.sjl.gankapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sjl.gankapp.R;
import com.sjl.gankapp.config.GankConfig;
import com.sjl.gankapp.model.pojo.CasualDetailResponse;
import com.sjl.gankapp.mvp.presenter.CasualListPresenter;
import com.sjl.gankapp.mvp.view.CasualListMvpView;
import com.sjl.platform.base.BaseActivity;
import com.sjl.platform.base.adapter.CommonRVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 闲读列表
 *
 * @author 林zero
 * @date 2018/6/21
 */
public class CasualListActivity extends BaseActivity<CasualListMvpView, CasualListPresenter> implements CasualListMvpView, SwipeRefreshLayout.OnRefreshListener {
    private static final String ID = "id";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.rvCasual)
    RecyclerView rvCasual;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    private List<CasualDetailResponse.CasualDetailBean> list = new ArrayList<>();
    private CommonRVAdapter<CasualDetailResponse.CasualDetailBean> adapter;

    private String id;
    private int currentPage = 1;

    public static Intent newIntent(Context context, String id) {
        Intent intent = new Intent(context, CasualListActivity.class);
        intent.putExtra(ID, id);
        return intent;
    }

    private void parseIntent() {
        id = getIntent().getStringExtra(ID);
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
            protected void onBindViewHolder(RecyclerView.Adapter adapter, RVViewHolder viewHolder, int position, CasualDetailResponse.CasualDetailBean item, List<CasualDetailResponse.CasualDetailBean> list) {
                ((TextView) viewHolder.findViewById(R.id.tvTitle)).setText(item.getTitle());
                ((TextView) viewHolder.findViewById(R.id.tvDate)).setText(item.getPublished_at());
            }
        };
        rvCasual.setAdapter(adapter);
        rvCasual.setLayoutManager(new LinearLayoutManager(activity));
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

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        ((CasualListPresenter) mPresenter).getCasualList(id, GankConfig.PAGE_SIZE, currentPage);
    }

    @Override
    public void onGetCasualList(CasualDetailResponse casualDetailResponse, int page) {
        if (page <= 1) {
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
}
