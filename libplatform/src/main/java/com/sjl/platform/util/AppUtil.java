package com.sjl.platform.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.view.WindowManager;

/**
 * AppUtil
 *
 * @author 林zero
 * @date 2018/3/15
 */

public class AppUtil {

    public static void startActivity(Activity activity, View view, Class clazz, Bundle bundle) {
        ActivityOptionsCompat options = null;
        if (view != null) {
            options = ActivityOptionsCompat.makeScaleUpAnimation(view, //The View that the new activity is animating from
                    (int) view.getWidth() / 2, (int) view.getHeight() / 2, //拉伸开始的坐标
                    0, 0);//拉伸开始的区域大小，这里用（0，0）表示从无到全屏
        }
        startActivity(activity, clazz, bundle, options);
    }

    public static void startActivity(Activity activity, Class clazz, Bundle bundle, ActivityOptionsCompat options) {
        Intent intent = new Intent(activity, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        ActivityCompat.startActivity(activity, intent, options == null ? null : options.toBundle());
    }

    public static void startActivityForResult(Activity activity, View view, Class clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(activity, clazz);
        if (null != bundle)
            intent.putExtras(bundle);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeScaleUpAnimation(view, //The View that the new activity is animating from
                        (int) view.getWidth() / 2, (int) view.getHeight() / 2, //拉伸开始的坐标
                        0, 0);//拉伸开始的区域大小，这里用（0，0）表示从无到全屏
        ActivityCompat.startActivityForResult(activity, intent, requestCode, options.toBundle());
    }

    /**
     * 改变activity透明度
     *
     * @param activity
     * @param alpha
     */
    public static void changeAlpha(Activity activity, float alpha) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = alpha;
        activity.getWindow().setAttributes(params);
    }

    /**
     * 获取屏幕宽高
     *
     * @param context
     * @return
     */
    public static Point getScreen(Context context) {
        Point point = new Point();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getSize(point);
        return point;
    }
}
