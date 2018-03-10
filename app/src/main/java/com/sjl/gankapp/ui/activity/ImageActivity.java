package com.sjl.gankapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.sjl.gankapp.R;
import com.sjl.gankapp.config.GankConfig;
import com.sjl.gankapp.mvp.presenter.ImagePresenter;
import com.sjl.gankapp.mvp.view.ImageMvpView;
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
public class ImageActivity extends BaseActivity<ImageMvpView,ImagePresenter> implements ImageMvpView {
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

    private Toolbar toolBar;
    private PinchImageView pivImage;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_image;
    }

    @Override
    protected void initView() {
        parseIntent();
        initToolBar();
        pivImage = findViewById(R.id.pivImage);
        Glide.with(mContext).load(imageUrl).into(pivImage);
    }

    @Override
    protected ImageMvpView obtainMvpView() {
        return this;
    }

    @Override
    protected ImagePresenter obtainPresenter() {
        mPresenter = new ImagePresenter();
        return (ImagePresenter) mPresenter;
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
                    showToast(saveImage() ? getString(R.string.gank_save_success) + path : getString(R.string.gank_save_failure));
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

    @Override
    public void onClick(View v) {

    }
}
