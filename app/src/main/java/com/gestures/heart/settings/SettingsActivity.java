package com.gestures.heart.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alens.utils.SharePreferenceUtils;
import com.gestures.heart.R;
import com.gestures.heart.ui.NiceTextView;
import com.gestures.heart.ui.SwitchView;

/**
 * 设置 主页面
 */
public class SettingsActivity extends BaseSettingActivity implements View.OnClickListener {

    private SwitchView switch_wifi, switch_mobile_2g;
    public static final String WIFI_SWITCH_STATE = "wifi_switch_state";
    public static final String MOBILE_2G_SWITCH_STATE = "mobile_2g_switch_state";

    public static void startSettingsActivity(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getView(R.id.iv_back).setOnClickListener(this);
        getView(R.id.tv_person).setOnClickListener(this);
        getView(R.id.layout_account_safe).setOnClickListener(this);
        getView(R.id.layout_notice).setOnClickListener(this);
        getView(R.id.layout_black_list).setOnClickListener(this);
        getView(R.id.tv_language).setOnClickListener(this);
        getView(R.id.tv_feed_back).setOnClickListener(this);

        NiceTextView tv_clear = getView(R.id.tv_clear);
        tv_clear.setRightString("2.9M");

        NiceTextView tv_language = getView(R.id.tv_language);
        tv_language.setRightString("简体中文");

        switch_wifi = getView(R.id.switch_wifi);
        switch_wifi.setOnClickListener(this);
        switch_wifi.setOpened(SharePreferenceUtils.getBool(this, WIFI_SWITCH_STATE, true));

        switch_mobile_2g = getView(R.id.switch_mobile_2g);
        switch_mobile_2g.setOnClickListener(this);
        switch_mobile_2g.setOpened(SharePreferenceUtils.getBool(this, MOBILE_2G_SWITCH_STATE, true));
    }

    @Override
    public int getBarID() {
        return R.id.set_layout_head;
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_settings;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_person:
                goActivity(PersonalActivity.class);
                break;
            case R.id.switch_wifi:
                SharePreferenceUtils.putBool(this, WIFI_SWITCH_STATE, switch_wifi.isOpened());
                break;
            case R.id.switch_mobile_2g:
                SharePreferenceUtils.putBool(this, MOBILE_2G_SWITCH_STATE, switch_mobile_2g.isOpened());
                break;
            case R.id.layout_black_list:
                goActivity(BlackListActivity.class);
                break;
            case R.id.layout_account_safe:
                goActivity(AccountSafeActivity.class);
                break;
            case R.id.layout_notice:
                goActivity(NoticeActivity.class);
                break;
            case R.id.tv_language:
                goActivity(SwitchLanguageActivity.class);
                break;
            case R.id.tv_feed_back:
                goActivity(FeedBackActivity.class);
                break;

            default:
                //  ----  清楚缓存
//            GlideUtils.clear(this);  // 清理图片缓存
//                SharePreferenceUtils.clear(this);  //
                break;

        }
    }
}
