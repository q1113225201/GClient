package com.sjl.gank.ui.tab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sjl.gank.R;
import com.sjl.gank.bean.GankData;
import com.sjl.gank.bean.GankDataResult;
import com.sjl.gank.config.GankConfig;
import com.sjl.gank.service.ServiceClient;
import com.sjl.gank.ui.GankDetailActivity;
import com.sjl.platform.base.BaseFragment;
import com.sjl.platform.base.adapter.CommonRVAdapter;
import com.sjl.platform.base.db.DBManager;
import com.sjl.platform.util.LogUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 首页Fragment
 *
 * @author SJL
 * @date 2017/11/30
 */
public class IndexFragment extends BaseFragment {
    private static final String TAG = "IndexFragment";

    SwipeRefreshLayout srl;
    RecyclerView rv;
    private static final int SPAN_COUNT = 2;
    private CommonRVAdapter<GankDataResult> adapter;
    private List<GankDataResult> gankDataResultList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_index, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.i(TAG, "IndexFragment onViewCreated");

        initView();
    }

    private void initView() {
        srl = view.findViewById(R.id.srl);
        rv = view.findViewById(R.id.rv);
        srl.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LogUtil.i(TAG,"onRefresh");
                currentPage = 1;
                loadState = NOLOAD;
                getGirls(currentPage);
            }
        });
        initList();
        srl.setRefreshing(true);
        getLocalGirls();
        getGirls(currentPage);
    }

    /**
     * 初始化列表
     */
    private void initList() {
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                int[] positions = layoutManager.findLastCompletelyVisibleItemPositions(new int[SPAN_COUNT]);
                if(positions[positions.length-1]>=adapter.getItemCount()-GankConfig.PAGE_SIZE/2&&loadState==NOLOAD){
                    getGirls(currentPage);
                }
            }
        });
        rv.setLayoutManager(new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL));
        adapter = new CommonRVAdapter<GankDataResult>(getActivity(), gankDataResultList, R.layout.item_girls, R.layout.item_girls_empty) {
            @Override
            protected void onBindNullViewHolder(RecyclerView.Adapter adapter, RVViewHolder viewHolder, int position, GankDataResult item, List<GankDataResult> list) {

            }

            @Override
            protected void onBindViewHolder(RecyclerView.Adapter adapter, RVViewHolder viewHolder, int position, final GankDataResult item, List<GankDataResult> list) {
                try {
                    final ImageView ivItemImg = (ImageView) viewHolder.findViewById(R.id.ivItemImg);
                    final TextView tvItemTime = ((TextView) viewHolder.findViewById(R.id.tvItemTime));
                    Glide.with(getActivity()).load(item.getUrl()).into(ivItemImg);
                    final Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(item.getPublishedAt());
                    tvItemTime.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));

                    ivItemImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = GankDetailActivity.newIntent(getContext(),item.getUrl(), new SimpleDateFormat("yyyy-MM-dd").format(date));
                            try {
                                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                        getActivity(), tvItemTime, GankDetailActivity.TRANSFORM);
                                ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());
                            } catch (IllegalArgumentException e) {
                                e.printStackTrace();
                                startActivity(intent);
                            }
                        }
                    });
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        };
        rv.setAdapter(adapter);
    }

    private void getLocalGirls() {
        List<GankDataResult> list = DBManager.getInstance().getList(GankDataResult.class, "", String.format("publishedAt desc limit %d,%d", 0, GankConfig.PAGE_SIZE));
        adapter.flush(list);
    }

    private int currentPage = 1;
    private final static int LOADING = 1;
    private final static int NOLOAD = 2;
    private final static int LOAD_NO_MORE = 3;
    private int loadState = NOLOAD;

    private void getGirls(final int page) {
        if (loadState == LOAD_NO_MORE) {
            LogUtil.i(TAG, "没有更多数据");
            return;
        }
        if (loadState == NOLOAD) {
            loadState = LOADING;
        }else{
            return;
        }
        ServiceClient.getGankAPI().getSortDataByPages("福利", GankConfig.PAGE_SIZE, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GankData>() {
                    @Override
                    public void accept(GankData gankData) throws Exception {
                        LogUtil.i(TAG,gankData.toString());
                        if (gankData.getResults().size() == GankConfig.PAGE_SIZE) {
                            currentPage++;
                            loadState = NOLOAD;
                        } else {
                            loadState = LOAD_NO_MORE;
                        }
                        if(page==1){
                            if (DBManager.getInstance().getList(GankDataResult.class,String.format("_id='%s'",gankData.getResults().get(0).get_id()),"publishedAt desc").size()==0) {
                                adapter.flush(gankData.getResults());
                                saveGirls(gankData.getResults());
                            }
                        }else{
                            adapter.addList(gankData.getResults());
                        }
                        srl.setRefreshing(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.e(TAG, "accept---:" + throwable.getMessage());
                        loadState =loadState==LOADING?NOLOAD:loadState;
                        srl.setRefreshing(false);
                    }
                });
    }

    private void saveGirls(List<GankDataResult> results) {
        for (GankDataResult item:results){
            DBManager.getInstance().merger(item,String.format("_id='%s'",item.get_id()));
        }
    }

}
