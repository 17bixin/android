package com.gestures.heart.home.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alens.base.LazyLoadFragment;
import com.alens.base.immersion.ImmersionBar;
import com.alens.utils.GlideUtils;
import com.gestures.heart.R;

public class VideoUpperFragment extends LazyLoadFragment implements View.OnClickListener{

    private String headUrl = "http://7xi8d6.com1.z0.glb.clouddn.com/20171123083218_5mhRLg_sakura.gun_23_11_2017_8_32_9_312.jpeg";

    public static VideoUpperFragment newInstance() {
        VideoUpperFragment fragment = new VideoUpperFragment();
        return fragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_video_upper;
    }

    @Override
    protected void initView() {
        LinearLayout layoutMar = getView(R.id.ll_layout);
        ImmersionBar.with(this).titleBarMarginTop(layoutMar).statusBarDarkFont(true, 0.2f).init();
        getView(R.id.iv_back).setOnClickListener(this);
        GlideUtils.loadingImg(getActivity(), (ImageView) getView(R.id.iv_head), headUrl);
        ((TextView) getView(R.id.tv_name)).setText("萌萌哒小图");
        ((TextView) getView(R.id.tv_addr)).setText("广东");
        ((TextView) getView(R.id.tv_age)).setText("23岁");
        getView(R.id.iv_add).setOnClickListener(this);
        getView(R.id.iv_share).setOnClickListener(this);
        getView(R.id.iv_gift).setOnClickListener(this);
        getView(R.id.ll_danmu).setOnClickListener(this);
    }

    @Override
    protected void lazyFetchData(){

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().finish();
                break;
            case R.id.iv_add:  // +

                break;
            case R.id.iv_share:  // 分享

                break;
            case R.id.iv_gift:
                GiftDialog.newInstance().show(getChildFragmentManager(), "dialog");
                break;
            case R.id.ll_danmu:
                BarrageDialog.newInstance().show(getChildFragmentManager(), "barrage");
                break;
        }
    }
}
