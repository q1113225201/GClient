package com.sjl.platform.util;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by sjl on 2015/8/11.
 */
public class ToastUtil {
    private static Context mContext;
    private static Toast toast;
    private static final int SHOW = 0;
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW:
                    showToast(mContext, msg.getData().getString("TEXT"));
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 显示提示文本
     *
     * @param context
     * @param text
     */
    public static void showToast(Context context, String text) {
        if(TextUtils.isEmpty(text)){
            return;
        }
        initToast(context);
        toast.setText(text);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    private static void initToast(Context context) {
        if (toast==null) {
            toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
    }

    public static void showToastLong(Context context, String text) {
        if(TextUtils.isEmpty(text)){
            return;
        }
        initToast(context);
        toast.setText(text);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void showToast(Context context, int resId) {
        showToast(context, context.getResources().getText(resId) + "");
    }

    public static void showToastInThread(Context context, String text) {
        mContext = context;
        Message msg = handler.obtainMessage(SHOW);
        Bundle bundle = new Bundle();
        bundle.putString("TEXT", text);
        msg.setData(bundle);
        handler.sendMessage(msg);
    }

    public static void showToastInThread(Context context, int resId) {
        showToastInThread(context, context.getResources().getText(resId) + "");
    }
}
