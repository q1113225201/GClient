package com.sjl.gank.bean;

import java.util.List;

/**
 * GankData
 *
 * @author SJL
 * @date 2017/12/1
 */

public class GankData extends BaseData<GankData.GankDataResult> {

    public class GankDataResult{
        public String _id;
        public String createdAt;
        public String desc;
        public List<String> images;
        public String publishedAt;
        public String source;
        public String type;
        public String url;
        public String used;
        public String who;

        @Override
        public String toString() {
            return "GankDataResult{" +
                    "_id='" + _id + '\'' +
                    ", createdAt='" + createdAt + '\'' +
                    ", desc='" + desc + '\'' +
                    ", images=" + images +
                    ", publishedAt='" + publishedAt + '\'' +
                    ", source='" + source + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    ", used='" + used + '\'' +
                    ", who='" + who + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GankData{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
