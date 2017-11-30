package com.sjl.gank.view.bean;

/**
 * TabItem
 *
 * @author SJL
 * @date 2017/11/30
 */

public class TabItem {
    private int index;
    private int topDrawable;
    private String text;
    private int textColor;

    public TabItem(int index, int topDrawable, String text, int textColor) {
        this.index = index;
        this.topDrawable = topDrawable;
        this.text = text;
        this.textColor = textColor;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getTopDrawable() {
        return topDrawable;
    }

    public void setTopDrawable(int topDrawable) {
        this.topDrawable = topDrawable;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
}
