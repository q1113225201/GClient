package com.sjl.gank.bean;

/**
 * GankWebData
 *
 * @author SJL
 * @date 2017/12/1
 */

public class GankWebData extends BaseData<GankWebData.WebDataResult> {

    public class WebDataResult {
        public String _id;
        public String content;
        public String created_at;
        public String publishedAt;
        public String rand_id;
        public String title;
        public String update_at;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getRand_id() {
            return rand_id;
        }

        public void setRand_id(String rand_id) {
            this.rand_id = rand_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUpdate_at() {
            return update_at;
        }

        public void setUpdate_at(String update_at) {
            this.update_at = update_at;
        }

        @Override
        public String toString() {
            return "WebDataResult{" +
                    "_id='" + _id + '\'' +
                    ", content='" + content + '\'' +
                    ", created_at='" + created_at + '\'' +
                    ", publishedAt='" + publishedAt + '\'' +
                    ", rand_id='" + rand_id + '\'' +
                    ", title='" + title + '\'' +
                    ", update_at='" + update_at + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GankWebData{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
