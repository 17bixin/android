package com.gestures.heart.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alens.base.immersion.ImmersionBar;
import com.alens.utils.SelectorFactory;
import com.dialogui.DialogUIUtils;
import com.dialogui.widget.TimePickerView;
import com.gestures.heart.R;
import com.gestures.heart.base.BaseActivity;
import com.gestures.heart.ui.AlensTextWatcher;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 注册资料填写
 */
public class RegisterDataActivity extends BaseActivity {

    private TextView birthday_text;
    private EditText user_name_text;
    private ImageView iv_submit;
    private boolean isSelectedBoy = true;

    public static void startRegisterDataActivity(Context context) {
        Intent intent = new Intent(context, RegisterDataActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {

        ImmersionBar.with(this).titleBar(R.id.tv_cancel, false).transparentBar().init();
        RadioGroup rb_selector = getView(R.id.radioGroup);

        final RadioButton rb_boy = getView(R.id.radio_boy);
        Drawable drawable_boy = getResources().getDrawable(R.drawable.selector_bg_boy);
        drawable_boy.setBounds(0, 0, 270, 270);
        rb_boy.setCompoundDrawables(null, drawable_boy, null, null);
        rb_boy.setChecked(true);

        final RadioButton rb_girl = getView(R.id.radio_girl);
        Drawable drawable_girl = getResources().getDrawable(R.drawable.selector_bg_girl);
        drawable_girl.setBounds(0, 0, 270, 270);
        rb_girl.setCompoundDrawables(null, drawable_girl, null, null);

        rb_selector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.radio_boy) {
                    rb_boy.setChecked(true);
                    isSelectedBoy = true;
                } else if (checkedId == R.id.radio_girl) {
                    rb_girl.setChecked(true);
                    isSelectedBoy = false;
                }
            }
        });

        user_name_text = getView(R.id.user_name_text);
        user_name_text.addTextChangedListener(new AlensTextWatcher() {
            @Override
            public void onTextChanged(CharSequence str, int start, int before, int count) {
                iv_submit.setActivated(str.toString().length() > 0 && birthday_text.getText().length() > 0);
            }
        });
        birthday_text = getView(R.id.birthday_text);
        birthday_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogUIUtils.showDatePick(RegisterDataActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String strDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
                        birthday_text.setText(strDate);
                        iv_submit.setActivated(user_name_text.getText().toString().length() > 0 && strDate.length() > 0);
                    }
                }).show();
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
                DialogUIUtils.showToastCenter("注册成功~");
            }
        });


//        List<String> strings = new ArrayList<>();
//        strings.add("1");
//        strings.add("2");
//        strings.add("3");
//        DialogUIUtils.showBottomSheetAndCancel(activity, strings, "取消", new DialogUIItemListener() {
//            @Override
//            public void onItemClick(CharSequence text, int position) {
//            }
//
//            @Override
//            public void onBottomBtnClick() {
//            }
//        }).show();

    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_register_data;
    }

    // 取消
    public void goCancel(View v) {
        finish();
    }


}
