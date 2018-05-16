package com.gestures.heart.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alens.base.immersion.ImmersionBar;
import com.alens.utils.SelectorFactory;
import com.gestures.heart.R;
import com.gestures.heart.base.BaseActivity;
import com.gestures.heart.base.Config;
import com.gestures.heart.ui.AlensTextWatcher;
import com.gestures.heart.ui.MNPasswordEditText;

/**
 * 找回密码 或
 * 注册 密码设置
 * */
public class FindOrRegisterPWActivity extends BaseActivity implements View.OnClickListener {

    private static final String TEL_KEY = "tel_key";
    private static final String FLAG = "flag_key";
    private TextView mesTitle, tv_message;
    private ImageView iv_next;
    private String newTelNum = "";
    private MNPasswordEditText pw_note_text;  // 短信验证码
    private CountDownTimer countDownTimer = null;
    private EditText pw_text;   // 密码框
    private TextView countTextView;
    private boolean isFind = true;

    public static void startFindOrRegisterPWActivity(Context context, String telNum, boolean flag) {
        Intent intent = new Intent(context, FindOrRegisterPWActivity.class);
        intent.putExtra(TEL_KEY, telNum);
        intent.putExtra(FLAG, flag);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {

        ImmersionBar.with(this).titleBar(R.id.tv_cancel, false).transparentBar().init();
        isFind = getIntent().getBooleanExtra(FLAG, true);  // true  找回密码  false 填写注册密码
        tv_message = getView(R.id.tv_message);
        tv_message.setText(isFind ? "找回密码" : "手机号注册");

        mesTitle = getView(R.id.tv_message_title); //
        newTelNum = getIntent().getStringExtra(TEL_KEY);
        String titleName = "输入 " + newTelNum + " 收到的4位验证码";
        SpannableStringBuilder style = new SpannableStringBuilder(titleName);
        style.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFF00")), 3, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new ForegroundColorSpan(Color.WHITE), 15, titleName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mesTitle.setText(style);

        pw_note_text = getView(R.id.pw_note_text);
        pw_note_text.setOnPasswordChangeListener(new MNPasswordEditText.OnPasswordChangeListener() {
            @Override
            public void onPasswordChange(String password) {
                iv_next.setActivated(password.length() == 4 && pw_text.getText().length() > 5);
            }
        });

        pw_text = getView(R.id.pw_text);
        pw_text.addTextChangedListener(new AlensTextWatcher() {

            @Override
            public void onTextChanged(CharSequence str, int start, int before, int count) {
                iv_next.setActivated(str.toString().length() > 5 && pw_note_text.getText().toString().length() == 4);
            }
        });
        countTextView = getView(R.id.tv_mess_text);
        countTextView.setOnClickListener(this);
        sendShortNote();

        iv_next = getView(R.id.iv_next);
        iv_next.setImageDrawable(SelectorFactory.newGeneralSelector()
                .setDefaultDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_next_1))
                .setActivatedDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_next_0))
                .create());
        iv_next.setOnClickListener(this);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_register_pw;
    }

    // 返回
    public void goCancel(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_next:
                if(isFind){  // 找回密码

                }else
                    RegisterDataActivity.startRegisterDataActivity(this);
                break;
            case R.id.tv_mess_text: // 重新发送
                sendShortNote();
                break;
        }
    }

    private void sendShortNote() {
        countDownTimer = new CountDownTimer(Config.COUNT_TIME, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                countTextView.setEnabled(false);
                countTextView.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                countTextView.setEnabled(true);
                String str = "没有收到？重新发送";
                SpannableStringBuilder style = new SpannableStringBuilder(str);
                style.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                style.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFF00")), 5, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                countTextView.setText(style);
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null)
            countDownTimer.cancel();
    }
}
