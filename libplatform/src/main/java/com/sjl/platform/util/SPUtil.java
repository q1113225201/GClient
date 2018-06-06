package com.sjl.platform.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * <h3>SharedPreferences工具类</h3>
 */
public final class SPUtil {

    private SPUtil() {

    }

    public static String PREFERENCE_NAME = "sharedpreference";
    private static Application application;

    public static void init(Application application) {
        SPUtil.application = application;
    }

    /**
     * 保存String字符串
     *
     * @param key
     * @param value
     * @return 判断是否保存成功，保存成功返回true，反之返回false
     */
    public static boolean putString(String key, String value) {
        SharedPreferences settings = application.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * 根据key获得保存的String字符串
     *
     * @return value（String类型，默认值为null，即当得不到值时返回null）
     */
    public static String getString(String key) {
        return getString(key, null);
    }

    /**
     * 根据key获得保存的String字符串
     *
     * @param key
     * @param defaultValue 当获取不到值时，用该值代替
     * @return String字符串
     */
    public static String getString(String key,
                                   String defaultValue) {
        SharedPreferences settings = application.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }

    /**
     * 保存int值
     *
     * @param key
     * @param value
     * @return 判断是否保存成功，保存成功返回true，反之返回false
     */
    public static boolean putInt(String key, int value) {
        SharedPreferences settings = application.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * 获得保存的int值
     *
     * @param key
     * @return 默认值为-1
     */
    public static int getInt(String key) {
        return getInt(key, -1);
    }

    /**
     * 获得保存的int值
     *
     * @param key
     * @param defaultValue
     * @return 获得保存的int值
     */
    public static int getInt(String key, int defaultValue) {
        SharedPreferences settings = application.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }

    /**
     * 保存long值
     *
     * @param key
     * @param value
     * @return 判断是否保存成功，保存成功返回true，反之返回false
     */
    public static boolean putLong(String key, long value) {
        SharedPreferences settings = application.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * 获得保存的long值
     *
     * @param key
     * @return 默认值为-1
     */
    public static long getLong(String key) {
        return getLong(key, -1);
    }

    /**
     * 获得保存的long值
     *
     * @param key
     * @param defaultValue
     * @return 获得保存的long值
     */
    public static long getLong(String key, long defaultValue) {
        SharedPreferences settings = application.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }

    /**
     * 保存float值
     *
     * @param key
     * @param value
     * @return 判断是否保存成功，保存成功返回true，反之返回false
     */
    public static boolean putFloat(String key, float value) {
        SharedPreferences settings = application.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * 获得保存的float值
     *
     * @param key
     * @return 默认值为-1.0f
     */
    public static float getFloat(String key) {
        return getFloat(key, -1.0f);
    }

    /**
     * 获得保存的float值
     *
     * @param key
     * @param defaultValue
     * @return 获得保存的float值
     */
    public static float getFloat(String key, float defaultValue) {
        SharedPreferences settings = application.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }

    /**
     * 保存boolean值
     *
     * @param key
     * @param value
     * @return 判断是否保存成功，保存成功返回true，反之返回false
     */
    public static boolean putBoolean(String key, boolean value) {
        SharedPreferences settings = application.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * 获得保存的int值
     *
     * @param key
     * @return 默认值为false
     */
    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * 获得保存的boolean值
     *
     * @param key
     * @param defaultValue
     * @return 获得保存的boolean值
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        SharedPreferences settings = application.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }
}
