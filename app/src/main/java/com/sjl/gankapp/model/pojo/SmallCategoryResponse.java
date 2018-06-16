package com.sjl.gankapp.model.pojo;

import java.util.List;

/**
 * SmallCategoryResponse
 *
 * @author 林zero
 * @date 2018/6/16
 */

public class SmallCategoryResponse {
    private boolean error;
    private List<SmallCategory> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<SmallCategory> getResults() {
        return results;
    }

    public void setResults(List<SmallCategory> results) {
        this.results = results;
    }

    public class SmallCategory{
        private String _id;
        private String created_at;
        private String icon;
        private String id;
        private String title;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
