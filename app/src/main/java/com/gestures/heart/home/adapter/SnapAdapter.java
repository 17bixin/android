package com.gestures.heart.home.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gestures.heart.R;
import com.gestures.heart.home.bean.Challeng;
import com.gestures.heart.home.fragment.GravitySnapHelper;

import java.util.List;

public class SnapAdapter extends BaseQuickAdapter<Challeng, BaseViewHolder> implements GravitySnapHelper.SnapListener {

    private GradientDrawable drawable = new GradientDrawable();
    {
        drawable.setCornerRadius(30);
        drawable.setColor(Color.parseColor("#9b9b9b"));
    }

    public SnapAdapter(List<Challeng> list) {
        super(R.layout.layout_challeng_item, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, Challeng bean) {

        helper.setText(R.id.snapTextView, bean.getText());
        TextView countTextView = helper.getView(R.id.countTextView);
        countTextView.setText(bean.getmCount());
        countTextView.setBackground(drawable);

        RecyclerView mRecyclerView = helper.getView(R.id.recyclerView);


        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(
                        mRecyclerView.getContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false));

        new GravitySnapHelper(bean.getGravity(), false, this).attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(new ChallengItemAdapter(bean.getmItemList()));
    }

    @Override
    public void onSnap(int position) {

    }
}


