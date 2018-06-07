package com.sjl.gankapp.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
    private static UpdateAPI updateAPI;
    private static String gankBaseUrl="http://gank.io/api/";
    private static String updateBaseUrl="https://raw.githubusercontent.com/q1113225201/GClient/";
    public static GankAPI getGankAPI(){
        if(gankAPI==null){
            synchronized (TAG){
                if(gankAPI==null){
                    initAPI();
                }
            }
        }
        return gankAPI;
    }
    public static UpdateAPI getUpdateAPI(){
        if(updateAPI==null){
            synchronized (TAG){
                if(updateAPI==null){
                    initAPI();
                }
            }
        }
        return updateAPI;
    }

    private static void initAPI() {
        //日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30000, TimeUnit.MILLISECONDS)
                .writeTimeout(30000, TimeUnit.MILLISECONDS)
                .readTimeout(30000, TimeUnit.MILLISECONDS)
                .addNetworkInterceptor(loggingInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(gankBaseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        Retrofit updateRetrofit = new Retrofit.Builder()
                .baseUrl(updateBaseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        gankAPI = retrofit.create(GankAPI.class);
        updateAPI = updateRetrofit.create(UpdateAPI.class);
    }
}
