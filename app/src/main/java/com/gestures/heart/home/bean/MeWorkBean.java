package com.gestures.heart.home.bean;

public class MeWorkBean {

    private String url;
    private String urge;
    private String tagName;

    public MeWorkBean(String url, String urge, String tagName) {
        this.url = url;
        this.urge = urge;
        this.tagName = tagName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrge() {
        return urge;
    }

    public void setUrge(String urge) {
        this.urge = urge;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
