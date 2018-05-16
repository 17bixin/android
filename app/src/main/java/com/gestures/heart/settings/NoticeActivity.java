package com.gestures.heart.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gestures.heart.R;

/** 通知提醒 */
public class NoticeActivity extends BaseSettingActivity implements View.OnClickListener {

    public static void startNoticeActivity(Context context){
        Intent intent = new Intent(context, NoticeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getView(R.id.iv_back).setOnClickListener(this);
        ((TextView)getView(R.id.tv_title_name)).setText("通知提醒");

    }

    @Override
    public int getBarID() {
        return R.id.set_layout_head;
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_notice;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;

        }
    }
}
