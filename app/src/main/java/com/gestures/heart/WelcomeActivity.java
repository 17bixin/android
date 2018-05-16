package com.gestures.heart;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.alens.utils.L;
import com.gestures.heart.base.BaseActivity;
import java.lang.ref.WeakReference;

public class WelcomeActivity extends BaseActivity {

    protected final MyHandler sHandler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sHandler.postDelayed(mRunnable, 2_000);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sHandler.removeCallbacks(mRunnable);
    }

    protected static class MyHandler extends Handler {
        private final WeakReference<WelcomeActivity> mActivity;
        public MyHandler(WelcomeActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            WelcomeActivity activity = mActivity.get();
            if (activity != null) {}
        }
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024 / 1024);
            L.d("Remaining memory:" + maxMemory + "M");
            goActivity(MainActivity.class);
            finish();
            WelcomeActivity.this.overridePendingTransition(R.anim.scale_in, R.anim.shrink_out);
        }
    };
}
