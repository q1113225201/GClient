package com.sjl.gank;

import android.app.Application;

import com.sjl.gank.config.GankConfig;

/**
 * GankInit
 *
 * @author SJL
 * @date 2017/12/14 21:51
 */

public class GankInit {
    private static final String TAG = "GankInit";
    public static Application application;
    private static GankInit gankInit;

    public static GankInit init(Application application) {
        if (gankInit == null) {
            synchronized (TAG) {
                if (gankInit == null) {
                    gankInit = new GankInit(application);
                }
            }
        }
        return gankInit;
    }

    private GankInit(Application application) {
        GankInit.application = application;
        GankConfig.init(application);
    }
}
