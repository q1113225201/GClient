package com.sjl.app;

import android.content.Intent;
import android.os.Bundle;

import com.sjl.gank.GankMainActivity;
import com.sjl.platform.BaseActivity;

public class LoadActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        startActivity(new Intent(mContext,GankMainActivity.class));
        finish();
    }
}
