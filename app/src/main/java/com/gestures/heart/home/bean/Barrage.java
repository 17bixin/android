package com.gestures.heart.home.bean;


public class Barrage {

    private String headUrl;
    private String title;
    private String value;
    private int niceCount;

    public Barrage(String headUrl, String title, String value, int niceCount) {
        this.headUrl = headUrl;
        this.title = title;
        this.value = value;
        this.niceCount = niceCount;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getNiceCount() {
        return niceCount;
    }

    public void setNiceCount(int niceCount) {
        this.niceCount = niceCount;
    }
}
