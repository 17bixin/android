package com.gestures.heart.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gestures.heart.R;
import com.gestures.heart.base.BaseActivity;
import com.gestures.heart.home.fragment.TabFragment;
import com.gestures.heart.ui.NoScrollViewPager;

public class LookMeActivity extends BaseActivity {

    private NoScrollViewPager mViewPager;
    private RadioGroup mIndicator;
    private RadioButton rb_work, rb_like;
    private FragmentPagerAdapter mAdapter;
    private TabFragment[] mFragments = new TabFragment[2];

    public static void startLookMeActivity(Context context) {
        Intent intent = new Intent(context, LookMeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
        initDatas();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_me;
    }

    private void initDatas(){

        for (int i = 0; i < mFragments.length; i++){
            mFragments[i] = TabFragment.newInstance("交互值");
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()){
            @Override
            public int getCount()
            {
                return mFragments.length;
            }

            @Override
            public Fragment getItem(int position)
            {
                return mFragments[position];
            }
        };

        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
    }

    private void initViews(){
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
