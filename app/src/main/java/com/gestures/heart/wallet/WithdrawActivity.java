package com.gestures.heart.wallet;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alens.utils.SelectorFactory;
import com.gestures.heart.R;
import com.gestures.heart.settings.BaseSettingActivity;

/**
 * 提现
 */
public class WithdrawActivity extends BaseSettingActivity implements View.OnClickListener {

    private EditText moneyText;
    private TextView tv_commit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        getView(R.id.iv_back).setOnClickListener(this);
        ((TextView) getView(R.id.tv_title_name)).setText("收益提现");

        moneyText = getView(R.id.et_money_text);
        getView(R.id.tv_add_account).setOnClickListener(this);
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
        return R.layout.activity_withdraw;
    }

    public void gotoAgree(View view){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_add_account:
                goActivity(AddAccountActivity.class);
                break;
            case R.id.tv_commit:
            //  提交
                break;

        }
    }
}
