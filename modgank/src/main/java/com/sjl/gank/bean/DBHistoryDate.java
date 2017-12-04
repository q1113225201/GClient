package com.sjl.gank.bean;

/**
 * DBHistoryDate
 *
 * @author SJL
 * @date 2017/12/4
 */

public class DBHistoryDate {
    private Integer id;
    private String date;

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
