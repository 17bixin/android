package com.gestures.heart.home.bean;

public class NearbyBean {

    private String url;
    private String like;
    private String distance;

    public NearbyBean(String url, String like, String distance) {
        this.url = url;
        this.like = like;
        this.distance = distance;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
