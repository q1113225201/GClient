package com.sjl.gankapp.bean;

import java.util.List;

/**
 * BaseData
 *
 * @author SJL
 * @date 2017/12/1
 */

public class BaseData<T> {
    protected boolean error;
    protected List<T> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
