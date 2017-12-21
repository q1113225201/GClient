package com.sjl.platform.service;

import android.content.Context;
import android.content.Intent;

import com.sjl.platform.util.LogUtil;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageService;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

/**
 * PushIntentService
 *
 * @author SJL
 * @date 2017/12/21
 */

public class PushIntentService extends UmengMessageService {
    private static final String TAG = "PushIntentService";
    @Override
    public void onMessage(Context context, Intent intent) {
        try {
            //可以通过MESSAGE_BODY取得消息体
            String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
            UMessage msg = new UMessage(new JSONObject(message));
            LogUtil.i(TAG, "message=" + message);      //消息体

            // 对完全自定义消息的处理方式，点击或者忽略
            boolean isClickOrDismissed = true;
            if (isClickOrDismissed) {
                //完全自定义消息的点击统计
                UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
            } else {
                //完全自定义消息的忽略统计
                UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
            }

        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
    }
}
