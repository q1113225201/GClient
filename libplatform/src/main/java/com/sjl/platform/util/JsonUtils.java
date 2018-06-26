package com.sjl.platform.util;

import android.support.annotation.NonNull;
import android.util.Base64;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Json解析
 *
 * @author SJL
 * @date 2018/4/14
 */
public class JsonUtils {
    private static Gson gson = new Gson();

    /**
     * Object转换为Json
     */
    public static String toJson(Object obj) {
        String ret = gson.toJson(obj);
        return ret;
    }

    /**
     * Object转换为Base64
     */
    public static String toBase64(Object obj) {
        String ret = toJson(obj);
        ret = Base64.encodeToString(ret.getBytes(Charset.forName("UTF-8")), Base64.DEFAULT);
        return ret;
    }

    /**
     * Json转换为Object对象
     */
    public static  <T> T toObject(String json, Class<T> clazz) {
        T obj = gson.fromJson(json, clazz);
        return obj;
    }

    /**
     * Json转换为集合
     */
    @NonNull
    public static <T> List<T> toObjectArray(String json, Class<T[]> clazz) {
       /* java.lang.reflect.Type type=new TypeToken<T[]>(){}.getType();*/
        T[] arr = new Gson().fromJson(json, clazz);
        return Arrays.asList(arr);
    }

    /**
     * Json 转成 Map<>
     * @param jsonStr
     * @return
     */
    public static Map<String, Object> getMapForJson(String jsonStr){
        JSONObject jsonObject ;
        try {
            jsonObject = new JSONObject(jsonStr);
            Iterator<String> keyIter= jsonObject.keys();
            String key;
            Object value ;
            Map<String, Object> valueMap = new HashMap<String, Object>();
            while (keyIter.hasNext()) {
                key = keyIter.next();
                value = jsonObject.get(key);
                valueMap.put(key, value);
            }
            return valueMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}