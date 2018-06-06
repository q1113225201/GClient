package com.sjl.gankapp.model.pojo;

import java.util.List;

/**
 * GankDayData
 *
 * @author SJL
 * @date 2017/12/11
 */

public class GankDayDataResult{
    private List<GankDataResult> Android;
    private List<GankDataResult> iOS;
    private List<GankDataResult> 休息视频;
    private List<GankDataResult> 前端;
    private List<GankDataResult> 拓展资源;
    private List<GankDataResult> 瞎推荐;

    public List<GankDataResult> getAndroid() {
        return Android;
    }

    public void setAndroid(List<GankDataResult> android) {
        Android = android;
    }

    public List<GankDataResult> getiOS() {
        return iOS;
    }

    public void setiOS(List<GankDataResult> iOS) {
        this.iOS = iOS;
    }

    public List<GankDataResult> get休息视频() {
        return 休息视频;
    }

    public void set休息视频(List<GankDataResult> 休息视频) {
        this.休息视频 = 休息视频;
    }

    public List<GankDataResult> get前端() {
        return 前端;
    }

    public void set前端(List<GankDataResult> 前端) {
        this.前端 = 前端;
    }

    public List<GankDataResult> get拓展资源() {
        return 拓展资源;
    }

    public void set拓展资源(List<GankDataResult> 拓展资源) {
        this.拓展资源 = 拓展资源;
    }

    public List<GankDataResult> get瞎推荐() {
        return 瞎推荐;
    }

    public void set瞎推荐(List<GankDataResult> 瞎推荐) {
        this.瞎推荐 = 瞎推荐;
    }

    @Override
    public String toString() {
        return "GankDayDataResult{" +
                "Android=" + Android +
                ", iOS=" + iOS +
                ", 休息视频=" + 休息视频 +
                ", 前端=" + 前端 +
                ", 拓展资源=" + 拓展资源 +
                ", 瞎推荐=" + 瞎推荐 +
                '}';
    }
}
