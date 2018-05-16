package com.gestures.heart.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alens.base.immersion.ImmersionBar;
import com.alens.utils.L;
import com.alens.utils.SelectorFactory;
import com.gestures.heart.R;
import com.gestures.heart.base.BaseActivity;
import com.gestures.heart.ui.AlensTextWatcher;

/***
 * 登录 输入密码页
 * */
public class LoginPWActivity extends BaseActivity {

    private static final String TEL_KEY = "tel_key";

    private TextView titleText;
    private ImageView iv_submit;
    private EditText pw_text;
    private String newTelNum = "";

    public static void startLoginPWActivity(Context context, String telNum){
        Intent intent = new Intent(context, LoginPWActivity.class);
        intent.putExtra(TEL_KEY, telNum);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).titleBar(R.id.tv_cancel, false).transparentBar().init();
        titleText = getView(R.id.tv_message_title);
        newTelNum = getIntent().getStringExtra(TEL_KEY);
        String str = "输入账号 "+ newTelNum +" 的密码";
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        style.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFF00")), 5, 16, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new ForegroundColorSpan(Color.WHITE), 16, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        titleText.setText(style);

        pw_text = getView(R.id.pw_text);
        pw_text.addTextChangedListener(new AlensTextWatcher(){
            @Override
            public void onTextChanged(CharSequence str, int start, int before, int count) {
                super.onTextChanged(str, start, before, count);
                iv_submit.setActivated(str.toString().length() > 5);
            }
        });
        iv_submit = getView(R.id.iv_submit);
        iv_submit.setImageDrawable(SelectorFactory.newGeneralSelector()
                .setDefaultDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_submit_0))
                .setActivatedDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_submit_1))
                .create());
        iv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 提交

            }
        });
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_login_pw;
    }

    // 找回密码
    public void findPW(View view){
        L.e("找回密码");
        FindOrRegisterPWActivity.startFindOrRegisterPWActivity(this, newTelNum, true);
    }

    // 取消
    public void goCancel(View view){
        finish();
    }
}
