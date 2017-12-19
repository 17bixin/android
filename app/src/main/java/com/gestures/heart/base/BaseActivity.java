package com.gestures.heart.base;

import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.view.View;


public abstract class BaseActivity extends AbsBaseActivity {

    PowerManager powerManager = null;
    PowerManager.WakeLock wakeLock = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutID());

        powerManager = (PowerManager)this.getSystemService(this.POWER_SERVICE);
        wakeLock = this.powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "BX Lock");
    }

    protected abstract int setLayoutID();

    protected <T extends View>T getView(int resourcesId){
        return (T) findViewById(resourcesId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        wakeLock.acquire();
    }

    @Override
    protected void onPause() {
        super.onPause();
        wakeLock.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
