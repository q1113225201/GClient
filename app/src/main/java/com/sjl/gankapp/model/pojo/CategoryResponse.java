package com.sjl.gankapp.model.pojo;

import java.util.List;

/**
 * 闲读大类
 *
 * @author 林zero
 * @date 2018/6/16
 */

public class CategoryResponse {
    private boolean error;
    private List<CategoryBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<CategoryBean> getResults() {
        return results;
    }

    public void setResults(List<CategoryBean> results) {
        this.results = results;
    }

    public class CategoryBean {
        private String _id;
        private String en_name;
        private String name;
        private int rank;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getEn_name() {
            return en_name;
        }

        public void setEn_name(String en_name) {
            this.en_name = en_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
    }
}
