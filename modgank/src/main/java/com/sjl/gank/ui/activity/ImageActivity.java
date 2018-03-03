package com.sjl.gank.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sjl.gank.R;
import com.sjl.gank.config.GankConfig;
import com.sjl.platform.base.BaseActivity;
import com.sjl.platform.util.BitmapUtil;
import com.sjl.platform.util.EncryptUtil;
import com.sjl.platform.util.ShareUtil;
import com.sjl.platform.widget.PinchImageView;

import java.io.File;
import java.io.IOException;

/**
 * 图片界面
 *
 * @author SJL
 * @date 2017/12/14
 */
public class ImageActivity extends BaseActivity {
    private static final String TAG = "ImageActivity";
    private static final String IMAGE_URL = "image_url";
    private String imageUrl;

    public static Intent newIntent(Context context, String imageUrl) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra(IMAGE_URL, imageUrl);
        return intent;
    }

    private String path;

    private void parseIntent() {
        imageUrl = getIntent().getStringExtra(IMAGE_URL);
        path = GankConfig.PATH_IMAGE + EncryptUtil.encryptMD5(imageUrl) + ".png";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        parseIntent();
        initView();
    }

    private Toolbar toolBar;
    private PinchImageView pivImage;

    private void initView() {
        initToolBar();
        pivImage = findViewById(R.id.pivImage);
        Glide.with(mContext).load(imageUrl).into(pivImage);
    }


    private void initToolBar() {
        toolBar = findViewById(R.id.toolBar);
        toolBar.setTitle(R.string.gank_girl_title);
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
                int id = item.getItemId();
                if (id == R.id.menuSave) {
                    toast(saveImage() ? getString(R.string.gank_save_success) + path : getString(R.string.gank_save_failure));
                } else if (id == R.id.menuShare) {
                    saveImage();
                    ShareUtil.shareImage(mContext, new File(path));
                }
                return false;
            }
        });
    }

    private boolean saveImage() {
        try {
            BitmapUtil.save(BitmapUtil.toBitmap(pivImage.getDrawable()), path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image, menu);
        return true;
    }
}
