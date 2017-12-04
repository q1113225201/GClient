package com.sjl.gank.service;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ServiceClient
 *
 * @author SJL
 * @date 2017/12/4
 */

public class ServiceClient {
    private static final String TAG = "ServiceClient";
    private static GankAPI gankAPI;
    private static String gankBaseUrl="http://gank.io/api/";
    public static GankAPI getGankAPI(){
        if(gankAPI==null){
            synchronized (TAG){
                if(gankAPI==null){
                    initGankAPI(gankBaseUrl);
                }
            }
        }
        return gankAPI;
    }

    private static void initGankAPI(String gankBaseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(gankBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        gankAPI = retrofit.create(GankAPI.class);
    }
}
