package com.sjl.gankapp;

import android.content.Intent;
import android.os.Bundle;

import com.sjl.gank.ui.GankMainActivity;
import com.sjl.platform.base.BaseActivity;
import com.sjl.platform.util.PermisstionUtil;

public class LoadActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        PermisstionUtil.requestPermissions(mContext, PermisstionUtil.STORAGE, 100, "正在请求读写权限", new PermisstionUtil.OnPermissionResult() {
            @Override
            public void granted(int requestCode) {
                startActivity(new Intent(mContext,GankMainActivity.class));
                finish();
            }

            @Override
            public void denied(int requestCode) {

            }
        });
    }
}
