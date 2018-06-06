package com.sjl.gankapp.model.pojo;

/**
 * SearchData
 *
 * @author SJL
 * @date 2017/12/1
 */

public class SearchData extends BaseData {
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "SearchData{" +
                "count=" + count +
                ", error=" + error +
                ", results=" + results +
                '}';
    }
}
