package com.gestures.heart.base;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.avos.avoscloud.AVOSCloud;
import com.dialogui.DialogUIUtils;

public class App extends Application {

    public static int H, W;
    public static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        getScreen(this);
        initPush();
        DialogUIUtils.init(this);
    }

    private void initPush() {
        AVOSCloud.initialize(this, "Q66wxp2kT1WcNAY4BBbqTv1v-gzGzoHsz",
                "7gd2zom3ht3vx6jkcmaamm1p2pkrn8hdye2pn4qjcwux1hl1");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public void getScreen(Context aty) {
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        H = dm.heightPixels;
        W = dm.widthPixels;
    }

}
