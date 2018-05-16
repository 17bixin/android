package com.gestures.heart.home.fragment;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alens.base.LazyLoadFragment;
import com.gestures.heart.R;
import com.gestures.heart.ui.NoScrollViewPager;

public class MeFragment extends LazyLoadFragment {

    private NoScrollViewPager mViewPager;
    private RadioGroup mIndicator;
    private RadioButton rb_work, rb_like;
    private FragmentPagerAdapter mAdapter;
    private TabFragment[] mFragments = new TabFragment[2];

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_me;
    }


    @Override
    protected void lazyFetchData(){
        for (int i = 0; i < mFragments.length; i++){
            mFragments[i] = TabFragment.newInstance("exchange");
        }

        mAdapter = new FragmentPagerAdapter(getChildFragmentManager()){
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
    protected void initView(){
        mIndicator =  getView(R.id.id_stickynavlayout_indicator);
        rb_work = getView(R.id.rb_work);
        rb_like = getView(R.id.rb_like);
        mViewPager = getView(R.id.id_stickynavlayout_viewpager);

        mIndicator.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                mViewPager.setCurrentItem(checkedId == R.id.rb_work ? 0 : 1);
            }
        });
    }
}
