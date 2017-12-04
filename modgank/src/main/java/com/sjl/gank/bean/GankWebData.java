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
