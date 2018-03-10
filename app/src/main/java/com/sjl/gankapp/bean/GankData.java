package com.sjl.gankapp.bean;

/**
 * GankData
 *
 * @author SJL
 * @date 2017/12/1
 */

public class GankData extends BaseData<GankDataResult> {

    @Override
    public String toString() {
        return "GankData{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
