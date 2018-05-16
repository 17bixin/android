package com.gestures.heart.settings;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gestures.heart.R;
import com.gestures.heart.ui.NiceTextView;

/** 账号与安全 */
public class AccountSafeActivity extends BaseSettingActivity implements View.OnClickListener {

    private NiceTextView tv_mobile_bind, tv_certification;

    public static void startAccountSafeActivity(Context context){
        Intent intent = new Intent(context, AccountSafeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getView(R.id.iv_back).setOnClickListener(this);
        ((TextView)getView(R.id.tv_title_name)).setText(getString(R.string.account_safe));

        tv_mobile_bind = getView(R.id.tv_mobile_bind);
        tv_certification = getView(R.id.tv_certification);

        tv_mobile_bind.setRightString("134 0000 0000");
        tv_mobile_bind.setRightTVColor(Color.parseColor("#49D148"));
        tv_certification.setRightString("未实名验证");
        tv_certification.setRightTVColor(Color.parseColor("#FF3B30"));

    }

    @Override
    public int getBarID() {
        return R.id.set_layout_head;
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_account_safe;
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
