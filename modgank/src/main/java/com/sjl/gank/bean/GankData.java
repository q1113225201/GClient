package com.sjl.gank.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

import java.util.List;

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
