package com.sjl.gankapp.ui.fragment;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sjl.gankapp.R;
import com.sjl.gankapp.model.pojo.GankDataResult;
import com.sjl.gankapp.config.GankConfig;
import com.sjl.gankapp.mvp.presenter.IndexPresenter;
import com.sjl.gankapp.mvp.view.IndexMvpView;
import com.sjl.gankapp.ui.activity.GankDetailActivity;
import com.sjl.gankapp.util.GankUtil;
import com.sjl.platform.base.BaseFragment;
import com.sjl.platform.base.adapter.CommonRVAdapter;
import com.sjl.platform.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页Fragment
 *
 * @author SJL
 * @date 2017/11/30
 */
public class IndexFragment extends BaseFragment<IndexMvpView, IndexPresenter> implements IndexMvpView {
    private static final String TAG = "IndexFragment";
    private static final int SPAN_COUNT = 2;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    private CommonRVAdapter<GankDataResult> adapter;
    private List<GankDataResult> gankDataResultList = new ArrayList<>();
    private int currentPage = 1;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_index;
    }

    @Override
    protected void initView() {
        srl.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                ((IndexPresenter) mPresenter).getNetGirls(currentPage);
            }
        });
        initList();
        srl.setRefreshing(true);
        ((IndexPresenter) mPresenter).getLocalGirls();
        ((IndexPresenter) mPresenter).getNetGirls(currentPage);
    }

    @Override
    protected IndexMvpView obtainMvpView() {
        return this;
    }

    @Override
    protected IndexPresenter obtainPresenter() {
        mPresenter = new IndexPresenter();
        return (IndexPresenter) mPresenter;
    }

    /**
     * 初始化列表
     */
    private void initList() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                int[] positions = layoutManager.findLastCompletelyVisibleItemPositions(new int[SPAN_COUNT]);
                if (positions[positions.length - 1] >= adapter.getItemCount() - GankConfig.PAGE_SIZE / 2) {
                    ((IndexPresenter) mPresenter).getNetGirls(currentPage);
                }
                LogUtil.i(TAG, "scroll1=" + positions[positions.length - 1]);
                LogUtil.i(TAG, "scroll2=" + adapter.getItemCount());
            }
        });
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL));
        adapter = new CommonRVAdapter<GankDataResult>(activity, gankDataResultList, R.layout.item_girls, R.layout.item_girls_empty) {
            @Override
            protected void onBindNullViewHolder(RecyclerView.Adapter adapter, RVViewHolder viewHolder, int position, GankDataResult item, List<GankDataResult> list) {

            }

            @Override
            protected void onBindViewHolder(RecyclerView.Adapter adapter, RVViewHolder viewHolder, int position, final GankDataResult item, List<GankDataResult> list) {
                final ImageView ivItemImg = (ImageView) viewHolder.findViewById(R.id.ivItemImg);
                final TextView tvItemTime = ((TextView) viewHolder.findViewById(R.id.tvItemTime));
                Glide.with(activity).load(item.getUrl()).error(R.drawable.error).into(ivItemImg);
                tvItemTime.setText(GankUtil.parseDate(item.getPublishedAt()));

                ivItemImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = GankDetailActivity.newIntent(getContext(), item.getUrl(), GankUtil.parseDate(item.getPublishedAt()));
                        try {
                            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                    getActivity(), tvItemTime, GankDetailActivity.TRANSFORM);
                            ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle());
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                            startActivity(intent);
                        }
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setGirls(List<GankDataResult> list, int page) {
        if (page <= 1) {
            adapter.flush(list);
        } else {
            adapter.addList(list);
        }
        if (page != -1) {
            currentPage++;
        }
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

    @Override
    public void onClick(View v) {

    }
}
