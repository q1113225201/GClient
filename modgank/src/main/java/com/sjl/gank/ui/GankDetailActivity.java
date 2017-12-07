package com.sjl.gank.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sjl.gank.R;
import com.sjl.platform.base.BaseActivity;

public class GankDetailActivity extends BaseActivity {
    public static String TRANSFORM = "transform";
    private static String DATE = "date";
    private static String IMAGE_URL = "image_url";
    private static String IMAGE = "image";
    private String date;
    private String imageUrl;

    public static Intent newIntent(Context context,String imageUrl, String date) {
        Intent intent = new Intent(context, GankDetailActivity.class);
        intent.putExtra(IMAGE_URL, imageUrl);
        intent.putExtra(DATE, date);
        return intent;
    }

    private ImageView ivGirl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank_detail);

        initView();
    }

    private void initView() {
        parseIntent();
        ivGirl = findViewById(R.id.ivGirl);
        ViewCompat.setTransitionName(ivGirl, TRANSFORM);
        Glide.with(mContext).load(imageUrl).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).into(ivGirl);
    }

    private void parseIntent() {
        date = getIntent().getStringExtra(DATE);
        imageUrl = getIntent().getStringExtra(IMAGE_URL);
    }
}
