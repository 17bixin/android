package com.gestures.heart;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alens.utils.DownloadUtil;
import com.alens.utils.T;
import com.dialogui.DialogUIUtils;
import com.gestures.heart.base.App;
import com.gestures.heart.base.BaseActivity;
import com.gestures.heart.base.Config;
import com.gestures.heart.home.FactoryManage;
import com.gestures.heart.home.listener.OnSelectedTabListener;
import com.gestures.heart.ui.tab.CommonTabLayout;
import com.gestures.heart.ui.tab.listener.OnTabSelectListener;
import com.gestures.heart.ui.tab.utils.UnreadMsgUtils;

public class MainActivity extends BaseActivity implements OnSelectedTabListener {

    private CommonTabLayout tabLayout;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

//        checkUpdate();
    }

    private void initView(){
        tabLayout = getView(R.id.tab_layout);
        tabLayout.setTabData(Config.getHomeTabData());
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                FactoryManage.getFactory().switchFragmentIndex(MainActivity.this, position);
            }

            @Override
            public void onTabReselect(int position) {}
        });
        FactoryManage.getFactory().defaultSelected(this);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void onFragmentInteraction(int count) {
        UnreadMsgUtils.show(tabLayout.getMsgView(1), 99);
    }

    private void checkUpdate(){
        View view = LayoutInflater.from(this).inflate(R.layout.layout_check_update, null);
        TextView contentView =  view.findViewById(R.id.tv_content);
        final String strText = "更新内容" +
                "\n• 支持文字、贴纸、背景音乐，尽情展现欢乐气氛；" +
                "\n• 两人视频通话支持实时滤镜，丰富滤镜，多彩心情；" +
                "\n• 图片编辑新增艺术滤镜，一键打造文艺画风；" +
                "\n• 资料卡新增点赞排行榜，看好友里谁是魅力之王。";
        contentView.setText(strText);

        ((TextView)view.findViewById(R.id.tv_ver_code)).setText("最新版本:" + "v1.0.1");
        ((TextView) view.findViewById(R.id.tv_size)).setText("新版本大小:"+"20M");

        final Dialog dialog =  DialogUIUtils.showCustomAlert(this, view, Gravity.CENTER, false, false).show();

        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUIUtils.dismiss(dialog);
            }
        });

        view.findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updateUrl = "http://download.fir.im/v2/app/install/58f87d50959d6904280005a3?download_token=5f92dec5479f505e3c5e571075297e64\\u0026source=update";
                DownloadUtil.downloadApk(App.app, updateUrl, getString(R.string.app_name), strText, "bixin.apk");
                DialogUIUtils.dismiss(dialog);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            T.showShort(getApplicationContext(), getString(R.string.exit_commit));
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
