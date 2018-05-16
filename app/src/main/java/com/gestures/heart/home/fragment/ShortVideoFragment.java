package com.gestures.heart.home.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.alens.base.LazyLoadFragment;
import com.alens.base.immersion.ImmersionBar;
import com.gestures.heart.R;
import com.gestures.heart.base.Config;
import com.gestures.heart.ui.tab.CommonTabLayout;
import com.gestures.heart.ui.tab.listener.OnTabSelectListener;

public class ShortVideoFragment extends LazyLoadFragment {

    private ViewPager mViewPager = null;

    private Fragment[] fragmentArr = new Fragment[]
            {FocusFragment.newInstance(), FollowFragment.newInstance(),
                    ChallengeFragment.newInstance(), NearbyFragment.newInstance()};

    public static ShortVideoFragment newInstance() {
        return new ShortVideoFragment();
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_short_video;
    }

    @Override
    protected void initView() {
        final CommonTabLayout topTabLayout = getView(R.id.top_tab_layout);
        ImmersionBar.with(getActivity()).titleBarMarginTop(topTabLayout)
                .statusBarDarkFont(true, 0.2f)
                .init();
        topTabLayout.setTabData(Config.getHomeTopTabData());
        topTabLayout.setCurrentTab(0);

        topTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        mViewPager = getView(R.id.viewpager);
        mViewPager.setAdapter(new TopTabPagerAdapter(getChildFragmentManager()));
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mViewPager.setCurrentItem(position, false);
                topTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected void lazyFetchData() {
    }

    private class TopTabPagerAdapter extends FragmentPagerAdapter {
        public TopTabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragmentArr.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentArr[position];
        }
    }
}
