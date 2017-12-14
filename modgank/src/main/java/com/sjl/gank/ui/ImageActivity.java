package com.sjl.gank.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sjl.gank.R;
import com.sjl.gank.view.MenuPopWindow;
import com.sjl.platform.base.BaseActivity;
import com.sjl.platform.util.BitmapUtil;
import com.sjl.platform.util.EncryptUtil;

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

    private void parseIntent() {
        imageUrl = getIntent().getStringExtra(IMAGE_URL);
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
                if(position==0){
                    String path = Environment.getExternalStorageDirectory()+"/GankClient/image/"+ EncryptUtil.encryptMD5(imageUrl)+".png";
                    try {
                        BitmapUtil.save(ivImage.getDrawingCache(),path);
                        toast("保存成功"+path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if(position==1){

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.ivBack){
            finish();
        }else if(id==R.id.ivSetting){
            menuPopWindow.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        menuPopWindow = null;
    }
}
