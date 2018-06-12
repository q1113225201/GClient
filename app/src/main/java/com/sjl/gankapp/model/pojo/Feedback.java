package com.sjl.gankapp.model.pojo;

import cn.bmob.v3.BmobObject;

/**
 * Feedback
 *
 * @author 林zero
 * @date 2018/6/12
 */

public class Feedback extends BmobObject {
    private String version;
    private String phone;
    private Integer sdk;
    private String opinion;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSdk() {
        return sdk;
    }

    public void setSdk(Integer sdk) {
        this.sdk = sdk;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }
}
