package com.gestures.heart.crop.bean;

public class VideoEntity {

    public int ID;
    public String title;
    public String filePath;
    public int size;
    public int duration;

    public VideoEntity(int ID, String title, String filePath, int size, int duration) {
        this.ID = ID;
        this.title = title;
        this.filePath = filePath;
        this.size = size;
        this.duration = duration;
    }

}
