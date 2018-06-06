package com.sjl.platform;

import android.app.Activity;
import android.app.Application;

import com.sjl.platform.service.PushIntentService;
import com.sjl.platform.util.LogUtil;
import com.sjl.platform.util.SPUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.Stack;

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

    public static PlatformInit init(Application application) {
        if (platformInit == null) {
            synchronized (TAG) {
                if (platformInit == null) {
                    platformInit = new PlatformInit(application);
                }
            }
        }
        return platformInit;
    }

    private PlatformInit(Application application) {
        PlatformInit.application = application;
        SPUtil.init(application);
        //日志
        LogUtil.init(application, false);
        initUMeng(application);
    }

    private void initUMeng(final Application application) {
        //友盟统计
        MobclickAgent.setScenarioType(application, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.setDebugMode(false);
        //友盟推送
        PushAgent pushAgent = PushAgent.getInstance(application);
        pushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                LogUtil.i(TAG, deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
        pushAgent.setPushIntentServiceClass(PushIntentService.class);
    }

    private boolean isDebug = false;

    public PlatformInit setDebug(boolean debug) {
        isDebug = debug;
        LogUtil.init(application, debug);
        MobclickAgent.setDebugMode(debug);
        return platformInit;
    }

    private static EventBus eventBus;

    public static EventBus getEventBus() {
        if (eventBus == null) {
            eventBus = new EventBus();
        }
        return eventBus;
    }

    /**
     * Activity栈
     */
    private static Stack<Activity> activityStack = new Stack<>();

    public static Stack<Activity> getActivitys() {
        return activityStack;
    }

    public static void pushActivity(Activity activity) {
        activityStack.push(activity);
    }

    public static void popActivity(Activity activity) {
        if (activityStack.contains(activity)) {
            activityStack.remove(activity);
        }
    }

    public static void clearActivity() {
        for (Activity activity : activityStack) {
            if (activity != null) {
                activity.finish();
            }
        }
    }
}
