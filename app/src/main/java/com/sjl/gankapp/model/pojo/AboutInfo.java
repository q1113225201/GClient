package com.sjl.gankapp.model.pojo;

/**
 * AboutInfo
 *
 * @author 林zero
 * @date 2018/6/23
 */

public class AboutInfo {
    private String app;
    private String me;

    public AboutInfo(String app, String me) {
        this.app = app;
        this.me = me;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getMe() {
        return me;
    }

    public void setMe(String me) {
        this.me = me;
    }
}
