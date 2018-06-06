package com.sjl.gankapp.model.pojo;

/**
 * GankDayData
 *
 * @author SJL
 * @date 2017/12/11
 */

public class GankDayData{
    private boolean error;
    private GankDayDataResult results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public GankDayDataResult getResults() {
        return results;
    }

    public void setResults(GankDayDataResult results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "GankDayData{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
