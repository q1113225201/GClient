package com.sjl.platform;

import android.app.Application;

import com.sjl.platform.util.LogUtil;

/**
 * PlatformInit
 *
 * @author SJL
 * @date 2017/12/7
 */

public class PlatformInit {
    private static final String TAG = "PlatformInit";
    public static Application application;
    private static PlatformInit platformInit;

    public static PlatformInit init(Application application){
        if(platformInit==null){
            synchronized (TAG){
                if(platformInit==null){
                    platformInit = new PlatformInit(application);
                }
            }
        }
        return platformInit;
    }
    private PlatformInit(Application application) {
        PlatformInit.application = application;
        LogUtil.init(application,false);
    }
    private boolean isDebug = false;
    public PlatformInit setDebug(boolean debug){
        isDebug = debug;
        LogUtil.init(application,debug);
        return platformInit;
    }
}
