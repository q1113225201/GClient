package com.sjl.gank.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sjl.gank.R;
import com.sjl.gank.bean.GankData;
import com.sjl.gank.bean.GankDataResult;
import com.sjl.gank.config.GankConfig;
import com.sjl.gank.service.ServiceClient;
import com.sjl.gank.util.GankUtil;
import com.sjl.gank.view.SortPopWindow;
import com.sjl.platform.base.BaseActivity;
import com.sjl.platform.base.adapter.CommonRVAdapter;
import com.sjl.platform.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SortListActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "SortListActivity";
    private static final String SORT = "sort";
    private String sort;

    public static Intent newIntent(Context context, String sort) {
        Intent intent = new Intent(context, SortListActivity.class);
        intent.putExtra(SORT, sort);
        return intent;
    }

    private void parseIntent() {
        sort = getIntent().getStringExtra(SORT);
    }

    private ImageView ivBack;
    private TextView tvSort;
    private ImageView ivDropdown;
    private LinearLayout llSort;
    private SwipeRefreshLayout srl;
    private RecyclerView rvSort;
    private CommonRVAdapter<GankDataResult> adapter;
    private List<GankDataResult> list;

    private int currentPage = 1;
    private final static int LOADING = 1;
    private final static int NOLOAD = 2;
    private final static int LOAD_NO_MORE = 3;
    private int loadState = NOLOAD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_list);

        parseIntent();
        initView();
    }


    private void initView() {
        ivBack = findViewById(R.id.ivBack);
        llSort = findViewById(R.id.llSort);
        tvSort = findViewById(R.id.tvSort);
        ivDropdown = findViewById(R.id.ivDropdown);
        srl = findViewById(R.id.srl);
        rvSort = findViewById(R.id.rvSort);

        srl.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LogUtil.i(TAG, "onRefresh");
                currentPage = 1;
                loadState = NOLOAD;
                getSortList(currentPage, sort);
            }
        });
        ivBack.setOnClickListener(this);
        llSort.setOnClickListener(this);

        tvSort.setText(sort);
        initSortList();
        getSortList(currentPage, sort);
        initPopWindow();
    }

    private SortPopWindow sortPopWindow;

    private void initPopWindow() {
        final List<String> list = new ArrayList<>();
        list.add("all");
        list.add("Android");
        list.add("iOS");
        list.add("休息视频");
        list.add("福利");
        list.add("拓展资源");
        list.add("前端");
        list.add("瞎推荐");
        list.add("App");
        sortPopWindow = new SortPopWindow(mContext, llSort, list, new SortPopWindow.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                tvSort.setText(list.get(position));
            }
        });
    }

    private void initSortList() {
        list = new ArrayList<>();
        adapter = new CommonRVAdapter<GankDataResult>(mContext, list, R.layout.item_sort, R.layout.item_sort_empty) {
            @Override
            protected void onBindNullViewHolder(RecyclerView.Adapter adapter, RVViewHolder viewHolder, int position, GankDataResult item, List<GankDataResult> list) {

            }

            @Override
            protected void onBindViewHolder(RecyclerView.Adapter adapter, RVViewHolder viewHolder, int position, GankDataResult item, List<GankDataResult> list) {
                if ("福利".equalsIgnoreCase(sort)) {
                    viewHolder.findViewById(R.id.llItemType).setVisibility(View.VISIBLE);
                    viewHolder.findViewById(R.id.llItemContent).setVisibility(View.GONE);
                    ((TextView) viewHolder.findViewById(R.id.tvItemType)).setText(GankUtil.parseDate(item.getPublishedAt()));
                    viewHolder.findViewById(R.id.llItemImg).setVisibility(View.VISIBLE);
                    Glide.with(mContext).load(item.getUrl()).into(((ImageView) viewHolder.findViewById(R.id.ivItemImg)));
                } else {
                    viewHolder.findViewById(R.id.llItemType).setVisibility("all".equalsIgnoreCase(sort) ? View.VISIBLE : View.GONE);
                    viewHolder.findViewById(R.id.llItemContent).setVisibility(View.VISIBLE);
                    ((TextView) viewHolder.findViewById(R.id.tvItemType)).setText(item.getType());
                    ((TextView) viewHolder.findViewById(R.id.tvItemDesc)).setText(item.getDesc());
                    ((TextView) viewHolder.findViewById(R.id.tvItemTimeAuth)).setText(String.format("%s\n%s", GankUtil.parseDate(item.getPublishedAt()), item.getWho()));
                    if (item.getImages() == null || item.getImages().size() == 0) {
                        viewHolder.findViewById(R.id.llItemImg).setVisibility(View.GONE);
                    } else {
                        viewHolder.findViewById(R.id.llItemImg).setVisibility(View.VISIBLE);
                        Glide.with(mContext).load(item.getImages().get(0)).placeholder(R.drawable.loading).placeholder(R.drawable.ic_launcher).into(((ImageView) viewHolder.findViewById(R.id.ivItemImg)));
                    }
                    if("福利".equalsIgnoreCase(item.getType())) {
                        viewHolder.findViewById(R.id.llItemContent).setVisibility(View.GONE);
                        viewHolder.findViewById(R.id.llItemImg).setVisibility(View.VISIBLE);
                        Glide.with(mContext).load(item.getUrl()).placeholder(R.drawable.loading).placeholder(R.drawable.ic_launcher).into(((ImageView) viewHolder.findViewById(R.id.ivItemImg)));
                    }
                }
            }
        };
        rvSort.setAdapter(adapter);
        rvSort.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getSortList(int page, String sort) {
        ServiceClient.getGankAPI().getSortDataByPages(sort, 10, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GankData>() {
                    @Override
                    public void accept(GankData gankData) throws Exception {
                        LogUtil.i(TAG, gankData.toString());
                        if (gankData.getResults().size() == GankConfig.PAGE_SIZE) {
                            currentPage++;
                            loadState = NOLOAD;
                        } else {
                            loadState = LOAD_NO_MORE;
                        }
                        adapter.addList(gankData.getResults());
                        srl.setRefreshing(false);
                    }
                },new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.e(TAG, "accept---:" + throwable.getMessage());
                        loadState = loadState == LOADING ? NOLOAD : loadState;
                        srl.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ivBack) {
            finish();
        } else if (id == R.id.llSort) {
            sortPopWindow.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sortPopWindow = null;
    }
}
