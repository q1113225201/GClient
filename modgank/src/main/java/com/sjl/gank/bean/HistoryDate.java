package com.sjl.gank.bean;

/**
 * HistoryDate
 *
 * @author SJL
 * @date 2017/12/1
 */

public class HistoryDate extends BaseData<HistoryDate.HistoryDateResult> {
    public class HistoryDateResult{
        public Integer id;
        public String date;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        @Override
        public String toString() {
            return "DBHistoryDate{" +
                    "id=" + id +
                    ", date='" + date + '\'' +
                    '}';
        }
    }
    @Override
    public String toString() {
        return "HistoryDate{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
