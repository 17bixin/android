package com.gestures.heart.wallet;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alens.utils.SelectorFactory;
import com.gestures.heart.R;
import com.gestures.heart.settings.BaseSettingActivity;

public class AddAccountActivity extends BaseSettingActivity implements View.OnClickListener{

    private TextView tv_commit, tv_get_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        getView(R.id.iv_back).setOnClickListener(this);
        ((TextView)getView(R.id.tv_title_name)).setText("添加支付宝账号");

        tv_get_code = getView(R.id.tv_get_code);
        tv_get_code.setBackground(SelectorFactory.newShapeSelector()
                .setCornerRadius(8)
                .setStrokeWidth(1)
                .setDefaultStrokeColor(Color.parseColor("#ED3649"))
                .create());
        tv_commit = getView(R.id.tv_commit);
        tv_commit.setBackground(SelectorFactory.newShapeSelector()
                .setCornerRadius(8)
                .setDefaultBgColor(Color.parseColor("#ED3649"))
                .create());
        tv_commit.setOnClickListener(this);

    }

    @Override
    public int getBarID() {
        return R.id.set_layout_head;
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_add_account;
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
