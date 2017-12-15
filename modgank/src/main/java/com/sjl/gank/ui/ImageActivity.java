package com.sjl.gank.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sjl.gank.R;
import com.sjl.gank.config.GankConfig;
import com.sjl.gank.view.MenuPopWindow;
import com.sjl.platform.base.BaseActivity;
import com.sjl.platform.util.BitmapUtil;
import com.sjl.platform.util.EncryptUtil;
import com.sjl.platform.util.ShareUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片界面
 *
 * @author SJL
 * @date 2017/12/14
 */
public class ImageActivity extends BaseActivity implements View.OnClickListener {
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

    private ImageView ivBack;
    private ImageView ivSetting;
    private ImageView ivImage;

    private void initView() {
        ivBack = findViewById(R.id.ivBack);
        ivSetting = findViewById(R.id.ivSetting);
        ivImage = findViewById(R.id.ivImage);
        ivBack.setOnClickListener(this);
        ivSetting.setOnClickListener(this);
        Glide.with(mContext).load(imageUrl).into(ivImage);
        initPopWindow();
    }

    private MenuPopWindow menuPopWindow;

    private void initPopWindow() {
        List<String> list = new ArrayList<>();
        list.add("保存");
        list.add("分享");
        menuPopWindow = new MenuPopWindow(mContext, ivSetting, list, new MenuPopWindow.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {
                    toast(saveImage()?"保存成功" + path:"保存失败");
                } else if (position == 1) {
                    saveImage();
                    ShareUtil.shareImage(mContext,new File(path));
//                    ShareUtil.shareImage(mContext,path);
                }
            }
        });
    }

    private boolean saveImage() {
        try {
            BitmapUtil.save(BitmapUtil.toBitmap(ivImage.getDrawable()), path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ivBack) {
            finish();
        } else if (id == R.id.ivSetting) {
            menuPopWindow.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        menuPopWindow = null;
    }
}
