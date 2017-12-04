package com.sjl.gank.bean;

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

    public class SearchDataResult{
        public String desc;
        public String ganhuo_id;
        public String publishedAt;
        public String type;
        public String url;
        public String who;

        @Override
        public String toString() {
            return "SearchDataResult{" +
                    "desc='" + desc + '\'' +
                    ", ganhuo_id='" + ganhuo_id + '\'' +
                    ", publishedAt='" + publishedAt + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    ", who='" + who + '\'' +
                    '}';
        }
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
