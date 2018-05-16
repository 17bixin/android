package com.gestures.heart.login;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alens.base.immersion.ImmersionBar;
import com.alens.utils.GsonUtil;
import com.alens.utils.L;
import com.alens.utils.SelectorFactory;
import com.alens.utils.T;
import com.gestures.heart.R;
import com.gestures.heart.base.BaseActivity;
import com.gestures.heart.home.bean.TimeAndDate;
import com.gestures.heart.ui.AlensTextWatcher;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;
import com.okhttplib.callback.Callback;

import java.io.IOException;

public class LoginOrRegisterActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rl_login_layout;
    private LinearLayout ll_next_layout;
    private EditText telEditText;
    private ImageView iv_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        changeWindow();
        initView();
    }

    private void initView() {
        ImmersionBar.with(this).titleBar(R.id.tv_cancel, false).transparentBar().init();
        telEditText = getView(R.id.et_tel);
        formatTelNumber();
        rl_login_layout = getView(R.id.rl_login_layout);
        ll_next_layout = getView(R.id.ll_next_layout);
        iv_next = getView(R.id.iv_next);
        iv_next.setImageDrawable(SelectorFactory.newGeneralSelector()
                .setDefaultDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_next_1))
                .setActivatedDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_next_0))
                .create());
        iv_next.setOnClickListener(this);

        final String url = "http://api.k780.com:88/?app=life.time&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4";
//        &format=json";

        getView(R.id.iv_wx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doHttpAsync(HttpInfo.Builder().setRequestType(RequestType.GET)
                        .setUrl(url).addParam("format", "json").build(), new Callback() {
                    @Override
                    public void onFailure(HttpInfo info) throws IOException {
                        L.d(info.getRetDetail());
                    }

                    @Override
                    public void onSuccess(HttpInfo info) throws IOException {
                        L.d(info.getRetDetail());

                        TimeAndDate date = GsonUtil.GsonToBean(info.getRetDetail(), TimeAndDate.class);
                        L.d(date.toString());
                    }

                });

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_next:  // 下一步
                if (iv_next.isActivated()) {
                    String newTelNumm = telEditText.getText().toString().trim().replaceAll(" ", "");
                    if (isMobileNum(newTelNumm)){
                        if(newTelNumm.equals("13488883174"))
                            LoginPWActivity.startLoginPWActivity(this, newTelNumm);
                        else
                            FindOrRegisterPWActivity.startFindOrRegisterPWActivity(this, newTelNumm, false);
                    }else
                        T.showShort(this, "请输入正确手机号~");
                } else
                    L.e("不符合条件~~");

                break;
        }
    }

    private void formatTelNumber() {
        telEditText.addTextChangedListener(new AlensTextWatcher() {
            @Override
            public void onTextChanged(CharSequence str, int start, int before, int count) {
                String contents = str.toString();
                int length = contents.length();
                rl_login_layout.setVisibility(length > 0 ? View.GONE : View.VISIBLE);
                ll_next_layout.setVisibility(length > 0 ? View.VISIBLE : View.GONE);
                if (length == 4) {
                    if (contents.substring(3).equals(new String(" "))) { // -
                        contents = contents.substring(0, 3);
                        telEditText.setText(contents);
                        telEditText.setSelection(contents.length());
                    } else { // +
                        contents = contents.substring(0, 3) + " " + contents.substring(3);
                        telEditText.setText(contents);
                        telEditText.setSelection(contents.length());
                    }
                } else if (length == 9) {
                    if (contents.substring(8).equals(new String(" "))) { // -
                        contents = contents.substring(0, 8);
                        telEditText.setText(contents);
                        telEditText.setSelection(contents.length());
                    } else {// +
                        contents = contents.substring(0, 8) + " " + contents.substring(8);
                        telEditText.setText(contents);
                        telEditText.setSelection(contents.length());
                    }
                } else
                    iv_next.setActivated(length == 13 ? true : false);
            }
        });
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_register;
    }

    // 用户协议
    public void gotoAgree(View view) {
        L.d("-------------------------");

    }

    // 取消
    public void goCancel(View v) {
        finish();
    }

    // 修改窗口元素组件 透明
    private void changeWindow() {
//        Window window = getWindow();
//        WindowManager.LayoutParams wl = window.getAttributes();
//        wl.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
//        wl.alpha = 0.90f;
//        window.setAttributes(wl);
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNum(String mobiles) {
        //"[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "[1][34578]\\d{9}";
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }
}
