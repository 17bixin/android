package com.gestures.heart;

import android.app.Application;
import android.content.Context;

/**
 * Created by 25623 on 2017/12/5.
 */

public class GHApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getAppContext(){
        return mContext;
    }

}
