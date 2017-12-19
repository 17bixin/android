package com.gestures.heart.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.gestures.heart.base.immersion.ImmersionBar;
import com.gestures.heart.base.networkstate.NetInfo;
import com.gestures.heart.base.networkstate.NetworkStateListener;
import com.gestures.heart.base.networkstate.NetworkStateReceiver;
import com.gestures.heart.base.utils.L;

/**
 * 沉浸式状态栏
 * 输入法框 移除
 * 网络状态监听
 */
public class AbsBaseActivity extends AppCompatActivity {

    private InputMethodManager imm;
    protected ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isImmersionBar())
            initImmersionBar();

        initNetworkStateListener();
    }

    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    protected boolean isImmersionBar() {
        return true;
    }

    private NetworkStateListener networkStateListener;

    /**
     * 初始化网络状态监听器
     */
    private void initNetworkStateListener() {
        NetworkStateReceiver.registerNetworkStateReceiver(this);
        networkStateListener = new NetworkStateListener() {
            @Override
            public void onNetworkState(boolean isNetworkAvailable, NetInfo netInfo) {
                AbsBaseActivity.this.onNetworkState(isNetworkAvailable, netInfo);
            }
        };
        //添加网络状态监听
        NetworkStateReceiver.addNetworkStateListener(networkStateListener);
    }

    /**
     * 网络状态
     *
     * @param isNetworkAvailable 网络是否可用
     * @param netInfo            网络信息
     */
    public void onNetworkState(boolean isNetworkAvailable, NetInfo netInfo) {
        L.d("网络连接方式：--  " + netInfo.networkType);
    }

    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.imm == null) {
            this.imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((localView != null) && (this.imm != null)) {
            this.imm.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }

    @Override
    public void finish() {
        super.finish();
        hideSoftKeyBoard();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.imm = null;
        if (mImmersionBar != null)
            mImmersionBar.destroy();
        if (null != networkStateListener) {
            NetworkStateReceiver.removeNetworkStateListener(networkStateListener);
            NetworkStateReceiver.unRegisterNetworkStateReceiver(this);
        }
    }
}
