package com.sjl.platform.util;

import android.os.Environment;
import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 带日志文件输入的，又可控开关的日志调试
 *
 * @author dessmann developper
 * @version 1.0
 */
public class LogUtil {
    private static String tag = "LogUtil";
    public static Boolean LOG_SWITCH = true; // 日志控制总开关 true 在开发工具后台打印日志 false 不打印日志
    public static Boolean LOG_WRITE_TO_FILE = true;// 日志写入文件开关
    private static char LOG_TYPE = 'v';// 输入日志类型，w代表只输出告警信息等，v代表输出所有信息
    public static String LOG_FILEPATH = Environment.getExternalStorageDirectory() + "/LogUtil/";// 本类输出的日志文件名称
    public static String LOG_FILEPATH_RELEASE = "/data/data/com.sjl.lbox/cache/LogUtil/";// 本类输出的日志文件名称
    private static String LOG_FILENAME = "log.txt";// 本类输出的日志文件名称
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS", Locale.US);// 日志的输出格式
    private static SimpleDateFormat log_sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);// 日志文件的输出格式

    public static void w(Object msg) { // 警告信息  
        log(tag, msg.toString(), 'w');
    }

    public static void e(Object msg) { // 错误信息  
        log(tag, msg.toString(), 'e');
    }

    public static void d(Object msg) {// 调试信息  
        log(tag, msg.toString(), 'd');
    }

    public static void i(Object msg) {//  
        log(tag, msg.toString(), 'i');
    }

    public static void v(Object msg) {
        log(tag, msg.toString(), 'v');
    }

    public static void w(String text) {
        log(tag, text, 'w');
    }

    public static void e(String text) {
        log(tag, text, 'e');
    }

    public static void d(String text) {
        log(tag, text, 'd');
    }

    public static void i(String text) {
        log(tag, text, 'i');
    }

    public static void v(String text) {
        log(tag, text, 'v');
    }

    public static void w(String tag, Object msg) { // 警告信息  
        log(tag, msg.toString(), 'w');
    }

    public static void e(String tag, Object msg) { // 错误信息  
        log(tag, msg.toString(), 'e');
    }

    public static void d(String tag, Object msg) {// 调试信息  
        log(tag, msg.toString(), 'd');
    }

    public static void i(String tag, Object msg) {//  
        log(tag, msg.toString(), 'i');
    }

    public static void v(String tag, Object msg) {
        log(tag, msg.toString(), 'v');
    }

    public static void w(String tag, String text) {
        log(tag, text, 'w');
    }

    public static void e(String tag, String text) {
        log(tag, text, 'e');
    }

    public static void d(String tag, String text) {
        log(tag, text, 'd');
    }

    public static void i(String tag, String text) {
        log(tag, text, 'i');
    }

    public static void v(String tag, String text) {
        log(tag, text, 'v');
    }

    /**
     * 根据tag, msg和等级，输出日志
     *
     * @param tag
     * @param msg
     * @param level
     * @return void
     * @since v 1.0
     */
    private static void log(String tag, String msg, char level) {
        if (LOG_SWITCH) {
            for (int i = 0; i < msg.length(); i += 2000) {
                String str = msg.substring(i,i+2000>msg.length()?msg.length():i+2000);
                if ('e' == level && ('e' == LOG_TYPE || 'v' == LOG_TYPE)) { // 输出错误信息
                    Log.e(tag, str);
                } else if ('w' == level && ('w' == LOG_TYPE || 'v' == LOG_TYPE)) {
                    Log.w(tag, str);
                } else if ('d' == level && ('d' == LOG_TYPE || 'v' == LOG_TYPE)) {
                    Log.d(tag, str);
                } else if ('i' == level && ('d' == LOG_TYPE || 'v' == LOG_TYPE)) {
                    Log.i(tag, str);
                } else {
                    Log.v(tag, str);
                }
            }
        }
        writeLogtoFile(String.valueOf(level), tag, msg);
    }

    /**
     * 打开日志文件并写入日志
     *
     * @return
     **/
    private static void writeLogtoFile(String mylogtype, String tag, String text) {// 新建或打开日志文件
        Date nowtime = new Date();
        String msg = sdf.format(nowtime) + "    " + mylogtype + "    " + tag + "    " + text + "\n";
        try {
            if (LOG_WRITE_TO_FILE) {
                FileUtil.writeFile(LOG_FILEPATH + log_sdf.format(nowtime) + LOG_FILENAME, msg, true);
            } else {
                FileUtil.writeFile(LOG_FILEPATH_RELEASE + log_sdf.format(nowtime) + LOG_FILENAME, msg, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除制定的日志文件
     */
    public static void delFile() {// 删除日志文件
        //删除SD卡日志
        List<String> list = FileUtil.getFileNameList(LOG_FILEPATH);
        Date nowtime = new Date();
        if (list == null) {
            list = new ArrayList<String>();
        }
        for (int i = 0; i < list.size(); i++) {
            try {
                String filePath = LOG_FILEPATH + list.get(i);
                if (!list.get(i).equals(log_sdf.format(nowtime) + LOG_FILENAME)) {
                    FileUtil.deleteFile(filePath);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //删除data目录日志
        list = FileUtil.getFileNameList(LOG_FILEPATH_RELEASE);
        if (list == null)
            return;
        for (int i = 0; i < list.size(); i++) {
            try {
                String filePath = LOG_FILEPATH_RELEASE + list.get(i);
                if (!list.get(i).equals(log_sdf.format(nowtime) + LOG_FILENAME)) {
                    FileUtil.deleteFile(filePath);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}  