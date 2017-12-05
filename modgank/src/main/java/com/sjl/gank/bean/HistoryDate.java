package com.sjl.gank.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
 * HistoryDate
 *
 * @author SJL
 * @date 2017/12/1
 */

public class HistoryDate extends BaseData<String> {
    @Override
    public String toString() {
        return "HistoryDate{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
