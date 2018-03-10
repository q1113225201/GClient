package com.sjl.gankapp.config;

import android.content.Context;
import android.os.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * GankConfig
 *
 * @author SJL
 * @date 2017/12/6
 */

public class GankConfig {
    /**
     * 路径
     */
    public static String PATH = Environment.getExternalStorageDirectory() + "/com.sjl.gank/";
    public static String PATH_IMAGE = PATH + "images/";

    public static void init(Context context) {
        PATH = Environment.getExternalStorageDirectory() + "/" + context.getPackageName() + "/";
        PATH_IMAGE = PATH + "images/";
    }

    /**
     * 分类
     */
    public static final String ALL = "all";
    public static final String ANDROID = "Android";
    public static final String IOS = "iOS";
    public static final String REST = "休息视频";
    public static final String WELFARE = "福利";
    public static final String EXTRA = "拓展资源";
    public static final String FRONT = "前端";
    public static final String RECOMMAND = "瞎推荐";
    public static final String APP = "App";
    public static List<String> sortList;
    static {
        sortList = new ArrayList<>();
        sortList.add(ALL);
        sortList.add(ANDROID);
        sortList.add(IOS);
        sortList.add(REST);
        sortList.add(WELFARE);
        sortList.add(EXTRA);
        sortList.add(FRONT);
        sortList.add(RECOMMAND);
        sortList.add(APP);
    }

    /**
     * 每页条数
     */
    public static final int PAGE_SIZE = 10;
}
