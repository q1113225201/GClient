package com.sjl.gankapp.ui.activity;

import android.content.Context;
import android.content.Intent;
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
import com.sjl.gankapp.R;
import com.sjl.gankapp.bean.GankDataResult;
import com.sjl.gankapp.mvp.presenter.GankDetailPresenter;
import com.sjl.gankapp.mvp.view.GankDetailMvpView;
import com.sjl.platform.base.BaseActivity;
import com.sjl.platform.base.adapter.CommonRVAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 每日数据页面
 *
 * @author SJL
 * @date 2017/12/14
 */
public class GankDetailActivity extends BaseActivity<GankDetailMvpView, GankDetailPresenter> implements GankDetailMvpView {
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
    protected int getContentViewId() {
        return R.layout.activity_gank_detail;
    }

    @Override
    protected void initView() {
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
        ((GankDetailPresenter) mPresenter).getGankDetail(date);
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

    @Override
    protected GankDetailMvpView obtainMvpView() {
        return this;
    }

    @Override
    protected GankDetailPresenter obtainPresenter() {
        mPresenter = new GankDetailPresenter();
        return (GankDetailPresenter) mPresenter;
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

    @Override
    public void setGankDetail(List<GankDataResult> list) {
        adapter.flush(list);
    }
}
