package com.sjl.platform.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * ShareUtil
 *
 * @author SJL
 * @date 2017/12/14 21:55
 */

public class ShareUtil {
    /**
     * 文字分享
     * @param context
     * @param msg
     */
    public static void shareMsg(Context context,String msg){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 文件分享
     * @param context
     * @param file
     */
    public static void shareFile(Context context,File file){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_STREAM, UriUtil.getFileUri(context,file));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 图片分享
     * @param context
     * @param file
     */
    public static void shareImage(Context context,File file){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, UriUtil.getFileUri(context,file));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 图片分享
     * @param context
     * @param path
     */
    public static void shareImage(Context context,String path){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
