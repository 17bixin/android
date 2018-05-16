package com.gestures.heart.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gestures.heart.R;
import com.gestures.heart.ui.NiceTextView;

/** 切换语言 * */
public class SwitchLanguageActivity extends BaseSettingActivity implements View.OnClickListener{

    private NiceTextView simple_chinese, tv_traditional, tv_english;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getView(R.id.iv_back).setOnClickListener(this);
        ((TextView)getView(R.id.tv_title_name)).setText("切换语言");

        simple_chinese = getView(R.id.simple_chinese);
        tv_traditional = getView(R.id.tv_traditional);
        tv_english = getView(R.id.tv_english);

        simple_chinese.setOnClickListener(this);
        tv_traditional.setOnClickListener(this);
        tv_english.setOnClickListener(this);

        simple_chinese.setCbChecked(true);  // 默认
    }

    @Override
    public int getBarID() {
        return R.id.set_layout_head;
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_switch_language;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.simple_chinese:
                simple_chinese.setCbChecked(true);
                tv_traditional.setCbChecked(false);
                tv_english.setCbChecked(false);
                break;

            case R.id.tv_traditional:
                simple_chinese.setCbChecked(false);
                tv_traditional.setCbChecked(true);
                tv_english.setCbChecked(false);

                break;
            case R.id.tv_english:
                simple_chinese.setCbChecked(false);
                tv_traditional.setCbChecked(false);
                tv_english.setCbChecked(true);
                break;
        }
    }
}
