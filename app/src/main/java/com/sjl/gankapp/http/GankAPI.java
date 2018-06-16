package com.sjl.gankapp.http;

import com.sjl.gankapp.model.pojo.CategoryResponse;
import com.sjl.gankapp.model.pojo.GankData;
import com.sjl.gankapp.model.pojo.GankDayData;
import com.sjl.gankapp.model.pojo.GankWebData;
import com.sjl.gankapp.model.pojo.HistoryDate;
import com.sjl.gankapp.model.pojo.SearchData;
import com.sjl.gankapp.model.pojo.SmallCategoryResponse;

import io.reactivex.Flowable;
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
     */
    @GET("day/history")
    Call<HistoryDate> getHistory();

    /**
     * 获取已发布日期列表
     */
    @GET("day/history")
    Observable<HistoryDate> getHistoryDates();

    /**
     * 根据日期获取网页数据
     */
    @GET("history/content/day/{date}")
    Call<GankWebData> getWebDataByDate(@Path("date") String date);

    /**
     * 获取分页获取网页数据
     */
    @GET("history/content/{num}/{page}")
    Call<GankWebData> getWebDataByPage(@Path("num") int num, @Path("page") int page);

    /**
     * 根据类别分页获取数据
     */
    @GET("data/{type}/{num}/{page}")
    Call<GankData> getSortDataByPage(@Path("type") String type, @Path("num") int num, @Path("page") int page);

    /**
     * 根据类别分页获取数据
     */
    @GET("data/{type}/{num}/{page}")
    Observable<GankData> getSortDataByPages(@Path("type") String type, @Path("num") int num, @Path("page") int page);

    /**
     * 获取随机分类数据
     */
    @Deprecated
    @GET("random/data/{type}/{num}")
    Call<GankData> getRandomSortData(@Path("type") String type, @Path("num") int num);

    /**
     * 查询
     */
    @GET("search/query/listview/category/{type}/count/{num}/page/{page}")
    Call<SearchData> getSearchData(@Path("type") String type, @Path("num") int num, @Path("page") int page);

    /**
     * 每日数据
     */
    @GET("day/{date}")
    Observable<GankDayData> getDayData(@Path("date") String date);

    /**
     * 闲读大类
     */
    @GET("xiandu/categories")
    Flowable<CategoryResponse> getCategories();

    /**
     * 闲读大类
     */
    @GET("xiandu/category/{category}")
    Flowable<SmallCategoryResponse> getSmallCategories(@Path("category") String category);

}
