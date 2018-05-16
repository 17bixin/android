package com.gestures.heart.home.fragment;

import com.alens.base.LazyLoadFragment;
import com.gestures.heart.R;

public class EmptyFragment extends LazyLoadFragment {

    public static EmptyFragment newInstance() {
        EmptyFragment fragment = new EmptyFragment();
        return fragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_empty;
    }

    @Override
    protected void initView() {}

    @Override
    protected void lazyFetchData() {}
}
