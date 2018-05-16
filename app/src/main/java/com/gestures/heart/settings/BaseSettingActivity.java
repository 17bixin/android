package com.gestures.heart.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alens.base.immersion.ImmersionBar;
import com.gestures.heart.base.BaseActivity;

public abstract class BaseSettingActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImmersionBar.with(this)
                .titleBar(getBarID(), false)
                .transparentBar()
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    public abstract int getBarID();
}
