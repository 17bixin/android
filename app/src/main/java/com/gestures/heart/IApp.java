package com.gestures.heart;

import android.app.Application;
import android.content.Context;

public class IApp extends Application {

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
