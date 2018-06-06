package com.sjl.gankapp.model.event;

/**
 * EventClick
 *
 * @author 林zero
 * @date 2018/6/6
 */

public class EventClick {
    /**
     * checkVersion 版本检测
     */
    private String type;
    private Object value;

    public EventClick() {
    }

    public EventClick(String type, Object value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
