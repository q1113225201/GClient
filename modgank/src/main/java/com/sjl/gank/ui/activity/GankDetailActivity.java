package com.sjl.gank.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sjl.gank.R;
import com.sjl.gank.bean.GankDataResult;
import com.sjl.gank.bean.GankDayData;
import com.sjl.gank.bean.GankDayDataResult;
import com.sjl.gank.http.ServiceClient;
import com.sjl.platform.base.BaseActivity;
import com.sjl.platform.base.adapter.CommonRVAdapter;
import com.sjl.platform.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 每日数据页面
 *
 * @author SJL
 * @date 2017/12/14
 */
public class GankDetailActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "GankDetailActivity";
    public static String TRANSFORM = "transform";
    private static String DATE = "date";
    private static String IMAGE_URL = "image_url";
    private String date;
    private String imageUrl;

    public static Intent newIntent(Context context, String imageUrl, String date) {
        Intent intent = new Intent(context, GankDetailActivity.class);
        intent.putExtra(IMAGE_URL, imageUrl);
        intent.putExtra(DATE, date);
        return intent;
    }

    private void parseIntent() {
        date = getIntent().getStringExtra(DATE);
        imageUrl = getIntent().getStringExtra(IMAGE_URL);
    }

    private Toolbar toolBar;
    private TextView tvDate;
    private ImageView ivGirl;
    private RecyclerView rvDayData;

    private CommonRVAdapter<GankDataResult> adapter;
    private List<GankDataResult> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank_detail);
        initView();
    }

    private void initView() {
        parseIntent();
        initToolBar();
        tvDate = findViewById(R.id.tvDate);
        ivGirl = findViewById(R.id.ivGirl);
        rvDayData = findViewById(R.id.rvDayData);

        ViewCompat.setTransitionName(tvDate, TRANSFORM);
        tvDate.setText(date);
        Glide.with(mContext).load(imageUrl).into(ivGirl);
        ivGirl.setOnClickListener(this);
        initDayData();
        getDayData(date);
    }

    private void initToolBar() {
        toolBar = findViewById(R.id.toolBar);
        toolBar.setTitle("");
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menuOpen) {
                    startActivity(WebActivity.newIntent(mContext, date, "http://gank.io/" + date));
                }
                return false;
            }
        });
    }

    private void initDayData() {
        list = new ArrayList<>();
        adapter = new CommonRVAdapter<GankDataResult>(mContext, list, R.layout.item_day_data, R.layout.item_day_data_empty) {
            @Override
            protected void onBindNullViewHolder(RecyclerView.Adapter adapter, RVViewHolder viewHolder, int position, GankDataResult item, List<GankDataResult> list) {

            }

            @Override
            protected void onBindViewHolder(RecyclerView.Adapter adapter, RVViewHolder viewHolder, int position, final GankDataResult item, List<GankDataResult> list) {
                if (position == 0 || !list.get(position).getType().equalsIgnoreCase(list.get(position - 1).getType())) {
                    viewHolder.findViewById(R.id.tvCategory).setVisibility(View.VISIBLE);
                    ((TextView) viewHolder.findViewById(R.id.tvCategory)).setText(item.getType());
                } else {
                    viewHolder.findViewById(R.id.tvCategory).setVisibility(View.GONE);
                }
                SpannableStringBuilder builder = new SpannableStringBuilder();
                SpannableString spannableString = new SpannableString(item.getDesc());
                spannableString.setSpan(new TextAppearanceSpan(mContext, R.style.TextLink), 0, spannableString.length(), 0);
                builder.append(spannableString);
                spannableString = new SpannableString(TextUtils.isEmpty(item.getWho()) ? "" : item.getWho());
                spannableString.setSpan(new TextAppearanceSpan(mContext, R.style.TextAuth), 0, spannableString.length(), 0);
                builder.append(spannableString);
                ((TextView) viewHolder.findViewById(R.id.tvContent)).setText(builder.subSequence(0, builder.length()));
                viewHolder.findViewById(R.id.tvContent).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(WebActivity.newIntent(mContext, item.getDesc(), item.getUrl()));
                    }
                });
            }
        };
        rvDayData.setAdapter(adapter);
        rvDayData.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * 获取指定日期数据
     *
     * @param date
     */
    private void getDayData(String date) {
        LogUtil.i(TAG, date);
        ServiceClient.getGankAPI().getDayData(date)
                .map(new Function<GankDayData, List<GankDataResult>>() {
                    @Override
                    public List<GankDataResult> apply(GankDayData gankDayData) throws Exception {
                        LogUtil.i(TAG, gankDayData);
                        //将获取到的数据添加到一个列表中
                        GankDayDataResult gankDayDataResult = gankDayData.getResults();
                        List<GankDataResult> results = new ArrayList<>();
                        if (gankDayDataResult.getAndroid() != null) {
                            results.addAll(gankDayDataResult.getAndroid());
                        }
                        if (gankDayDataResult.getiOS() != null) {
                            results.addAll(gankDayDataResult.getiOS());
                        }
                        if (gankDayDataResult.get前端() != null) {
                            results.addAll(gankDayDataResult.get前端());
                        }
                        if (gankDayDataResult.get拓展资源() != null) {
                            results.addAll(gankDayDataResult.get拓展资源());
                        }
                        if (gankDayDataResult.get瞎推荐() != null) {
                            results.addAll(gankDayDataResult.get瞎推荐());
                        }
                        if (gankDayDataResult.get休息视频() != null) {
                            results.addAll(gankDayDataResult.get休息视频());
                        }
                        return results;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<GankDataResult>>() {
                    @Override
                    public void accept(List<GankDataResult> list) throws Exception {
                        adapter.flush(list);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.e(TAG, throwable.getMessage());
                    }
                });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ivGirl) {
            startActivity(ImageActivity.newIntent(mContext, imageUrl));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }
}
