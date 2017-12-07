package com.gestures.heart.base.utils;

import com.gestures.heart.base.view.tab.entity.TabEntity;
import com.gestures.heart.base.view.tab.listener.CustomTabEntity;

import java.util.ArrayList;

public class Config {


    // 最小录制时间
    public final static long DEFAULT_MIN_RECORD_DURATION = 3_000;


    //最大录制时间
    public final static long DEFAULT_MAX_RECORD_DURATION = 15_000;


    /**  录制 Tab Data*/
    public static ArrayList<CustomTabEntity> getRecordTabData(){
        String[] mTitles = {"正常拍", "表情包"};
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>(mTitles.length);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
        }
        return mTabEntities;
    }


}
