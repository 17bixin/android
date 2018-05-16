package com.gestures.heart.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.view.View;


public abstract class BaseActivity extends HttpActivity {

    PowerManager powerManager = null;
    PowerManager.WakeLock wakeLock = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutID());

        if(isPowerManager()){
            powerManager = (PowerManager)this.getSystemService(this.POWER_SERVICE);
            wakeLock = this.powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "BX Lock");
        }
    }

    protected abstract int setLayoutID();

    protected <T extends View>T getView(int resourcesId){
        return (T) findViewById(resourcesId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(wakeLock != null)
            wakeLock.acquire();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(wakeLock != null)
            wakeLock.release();
    }

    public void goActivity(Class<?> cls){
        goActivity(cls, null);
    }

    public void goActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public boolean isPowerManager() {
        return false;
    }
}
