package com.gestures.heart.home.bean;

public class FollowBean {

    private String url;
    private String like;
    private String distance;
    private int followCount;

    public FollowBean(String url, String like, String distance, int followCount) {
        this.url = url;
        this.like = like;
        this.distance = distance;
        this.followCount = followCount;
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

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }
}
