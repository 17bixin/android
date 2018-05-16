package com.gestures.heart.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alens.videoplayer.VideoPlayer;
import com.gestures.heart.R;
import com.gestures.heart.base.BaseActivity;
import com.gestures.heart.home.fragment.EmptyFragment;
import com.gestures.heart.home.fragment.VideoUpperFragment;

public class ShortVideoPlayActivity extends BaseActivity {

    private VideoPlayer videoPlayer;
    public static final String url_key = "URL_KEY";
    private String url = "";
    private ViewPager mViewPager;
    private Fragment[] mFragments = new Fragment[2];
    private FragmentPagerAdapter mAdapter = null;

    public static void starActiviity(Context mContext, String url) {
        Intent intent = new Intent(mContext, ShortVideoPlayActivity.class);
        intent.putExtra(url_key, url);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra(url_key);
        initView();
    }

    private void initView() {
        videoPlayer = getView(R.id.videoPlayer);
        videoPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoPlayer != null && videoPlayer.isPlaying())
                    videoPlayer.pauseVideo();
                else
                    videoPlayer.startVideo();
            }
        });
        videoPlayer.playVideo(url);

        mViewPager = getView(R.id.view_pager);

        mFragments[0] = VideoUpperFragment.newInstance();
        mFragments[1] = EmptyFragment.newInstance();

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()){
            @Override
            public int getCount(){
                return mFragments.length;
            }

            @Override
            public Fragment getItem(int position)
            {
                return mFragments[position];
            }
        };

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setCurrentItem(0);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_video_play;
    }

    @Override
    public boolean isPowerManager() {
        return true;
    }

    @Override
    protected void onDestroy() {
        if (videoPlayer != null) {
            videoPlayer.destroyVideo();
            videoPlayer = null;
        }
        super.onDestroy();
    }
}
