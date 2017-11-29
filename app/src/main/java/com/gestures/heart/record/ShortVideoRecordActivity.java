package com.gestures.heart.record;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gestures.heart.R;
import com.gestures.heart.base.BaseActivity;
import com.gestures.heart.base.utils.Config;
import com.gestures.heart.base.view.CommonPopupWindow;
import com.gestures.heart.base.view.VideoProgressView;
import com.gestures.heart.base.view.tab.CommonTabLayout;
import com.gestures.heart.base.view.tab.listener.OnTabSelectListener;

/***
 * 短视频 录制
 * */
public class ShortVideoRecordActivity extends BaseActivity {

    private ImageView iv_record_btn;
    private VideoProgressView progressView;  // 录制进度条
    private ImageView iv_tool_menu;
    private LinearLayout layout_record_tool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_short_video_record;
    }

    private void initView() {

        getView(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        iv_record_btn = getView(R.id.iv_record_btn);
        progressView = getView(R.id.videoProgressView);
        layout_record_tool = getView(R.id.layout_record_tool);


        final CommonTabLayout topTabLayout = getView(R.id.layout_record_tab);
        topTabLayout.setTabData(Config.getRecordTabData());
        topTabLayout.setCurrentTab(0);

        topTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
//                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {}
        });

        iv_record_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                }
                return true;
            }
        });

        initPopupWindow();

    }

    private void initPopupWindow(){
        final CommonPopupWindow popWin = new CommonPopupWindow(this, R.layout.layout_record_tools_win, 520, ViewGroup.LayoutParams.WRAP_CONTENT) {
            private ImageView iv_flash_btn, iv_delay_btn, iv_beauty_btn;
            private TextView tv_flash_text, tv_delay_text, tv_beauty_text;
            @Override
            protected void initView() {
                View view = getContentView();

                iv_flash_btn = view.findViewById(R.id.iv_flash_btn);
                iv_delay_btn = view.findViewById(R.id.iv_delay_btn);
                iv_beauty_btn = view.findViewById(R.id.iv_beauty_btn);
                tv_flash_text = view.findViewById(R.id.tv_flash_text);
                tv_delay_text = view.findViewById(R.id.tv_delay_text);
                tv_beauty_text = view.findViewById(R.id.tv_beauty_text);
            }

            @Override
            protected void initEvent() {
                iv_flash_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 闪光灯开关
                    }
                });
            }
        };

        final CommonPopupWindow.LayoutGravity layoutGravity=new CommonPopupWindow.LayoutGravity(
                CommonPopupWindow.LayoutGravity.ALIGN_LEFT
                        | CommonPopupWindow.LayoutGravity.TO_BOTTOM);
        layoutGravity.setHoriGravity(CommonPopupWindow.LayoutGravity.CENTER_HORI);

        iv_tool_menu = getView(R.id.iv_tool_menu);
        iv_tool_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWin.showBashOfAnchor(iv_tool_menu, layoutGravity, 0, 0);
            }
        });
    }
}
