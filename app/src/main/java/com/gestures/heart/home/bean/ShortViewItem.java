package com.gestures.heart.home.bean;

public class ShortViewItem {

    private String url;

    public ShortViewItem(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ShortViewItem{" +
                "url='" + url + '\'' +
                '}';
    }
}
