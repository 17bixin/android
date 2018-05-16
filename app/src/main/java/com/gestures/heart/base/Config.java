package com.gestures.heart.base;

import android.os.Environment;

import com.gestures.heart.R;
import com.gestures.heart.ui.tab.entity.TabEntity;
import com.gestures.heart.ui.tab.listener.CustomTabEntity;

import java.util.ArrayList;

public class Config {

    /*** 验证码 倒计时*/
    public static final int COUNT_TIME = 60_000;

    private static final String BASE_PATH = Environment.getExternalStorageDirectory().getPath()+"/bixin/";

    /**okHttp 数据缓存目录 */
    public static String getCacheDir(){
        return BASE_PATH + "cache/";
    }

    /*** okHttp download目录 */
    public static String getDownloadDir(){
        return BASE_PATH + "download/";
    }

    public static ArrayList<CustomTabEntity> getHomeTabData(){
        String[] mTitles = {"小视频", "多人交友", "消息", "我的"};
        int[] mIconUnselectIds = {
                R.mipmap.ic_tab_video_0, R.mipmap.ic_tab_recreation_0,
                R.mipmap.ic_tab_mes_0, R.mipmap.ic_tab_me_0};
        int[] mIconSelectIds = {
                R.mipmap.ic_tab_video_1, R.mipmap.ic_tab_recreation_1,
                R.mipmap.ic_tab_mes_1, R.mipmap.ic_tab_me_1};

        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>(mTitles.length);

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        return mTabEntities;
    }

    public static String[] getHomeTopTabText(){
        String[] mTitles = {"关注", "跟拍", "挑战", "附近"};
        return mTitles;
    }

    public static ArrayList<CustomTabEntity> getHomeTopTabData(){
        String[] mTitles = getHomeTopTabText();
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>(mTitles.length);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
        }
        return mTabEntities;
    }

}
