package com.gestures.heart.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutID());
    }

    protected abstract int setLayoutID();

    protected <T extends View>T getView(int resourcesId){
        return (T) findViewById(resourcesId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
