package com.sjl.gank.service;

import com.sjl.gank.bean.GankData;
import com.sjl.gank.bean.GankDayData;
import com.sjl.gank.bean.GankWebData;
import com.sjl.gank.bean.HistoryDate;
import com.sjl.gank.bean.SearchData;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * GankAPI
 *
 * @author SJL
 * @date 2017/12/4
 */

public interface GankAPI {
    /**
     * 获取已发布日期列表
     *
     * @return
     */
    @GET("day/history")
    Call<HistoryDate> getHistory();
    /**
     * 获取已发布日期列表
     *
     * @return
     */
    @GET("day/history")
    Observable<HistoryDate> getHistoryDates();

    /**
     * 根据日期获取网页数据
     *
     * @param date
     * @return
     */
    @GET("history/content/day/{date}")
    Call<GankWebData> getWebDataByDate(@Path("date") String date);

    /**
     * 获取分页获取网页数据
     *
     * @param num
     * @param page
     * @return
     */
    @GET("history/content/{num}/{page}")
    Call<GankWebData> getWebDataByPage(@Path("num") int num, @Path("page") int page);

    /**
     * 根据类别分页获取数据
     *
     * @param type
     * @param num
     * @param page
     * @return
     */
    @GET("data/{type}/{num}/{page}")
    Call<GankData> getSortDataByPage(@Path("type") String type, @Path("num") int num, @Path("page") int page);
    /**
     * 根据类别分页获取数据
     *
     * @param type
     * @param num
     * @param page
     * @return
     */
    @GET("data/{type}/{num}/{page}")
    Observable<GankData> getSortDataByPages(@Path("type") String type, @Path("num") int num, @Path("page") int page);

    /**
     * 获取随机分类数据
     *
     * @param type
     * @param num
     * @return
     */
    @Deprecated
    @GET("random/data/{type}/{num}")
    Call<GankData> getRandomSortData(@Path("type") String type, @Path("num") int num);

    /**
     * 查询
     * @param type
     * @param num
     * @param page
     * @return
     */
    @GET("search/query/listview/category/{type}/count/{num}/page/{page}")
    Call<SearchData> getSearchData(@Path("type")String type, @Path("num") int num, @Path("page") int page);

    /**
     * 每日数据
     * @return
     */
    @GET("day/{date}")
    Observable<GankDayData> getDayData(@Path("date")String date);
}
