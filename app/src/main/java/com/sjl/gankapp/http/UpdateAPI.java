package com.sjl.gankapp.http;

import com.sjl.gankapp.model.pojo.VersionInfo;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * UpdateAPI
 *
 * @author 林zero
 * @date 2018/6/7
 */

public interface UpdateAPI {

    @GET("master/version")
    Call<VersionInfo> getVersion();
    @GET("master/version")
    Observable<VersionInfo> getVersion1();
}
