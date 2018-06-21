package com.sjl.gankapp.model.pojo;

/**
 * 闲读
 *
 * @author 林zero
 * @date 2018/6/21
 */

public class CasualDetailResponse extends BaseData<CasualDetailResponse.CasualDetailBean> {

    public class CasualDetailBean{
        private String _id;
        private String content;
        private String cover;
        private String created_at;
        private boolean deleted;
        private String published_at;
        private String title;
        private String url;

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

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public String getPublished_at() {
            return published_at;
        }

        public void setPublished_at(String published_at) {
            this.published_at = published_at;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
