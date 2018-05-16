package com.gestures.heart.home.bean;


import android.view.Gravity;

import java.util.List;

public class Challeng {

    private int mGravity = Gravity.START;
    private String mText;
    private String mCount;
    private List<String> mItemList;

    public String getmCount() {
        return mCount;
    }

    public Challeng(String text, String count, List<String> itemList) {
        mText = text;

        mCount = count;
        mItemList = itemList;
    }

    public String getText(){
        return mText;
    }

    public int getGravity(){
        return mGravity;
    }

    public List<String> getmItemList() {
        return mItemList;
    }
}
