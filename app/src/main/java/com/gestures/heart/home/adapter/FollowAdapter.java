package com.gestures.heart.home.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alens.utils.GlideUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gestures.heart.R;
import com.gestures.heart.base.App;
import com.gestures.heart.home.bean.FollowBean;
import com.gestures.heart.ui.SelectableRoundedImageView;

import java.util.List;

public class FollowAdapter extends BaseQuickAdapter<FollowBean, ViewHolder> {

    public FollowAdapter(List<FollowBean> list) {
        super(R.layout.layout_follow_item, list);
    }

    @Override
    protected void convert(ViewHolder helper, FollowBean bean) {

        ImageView itemImage = helper.getView(R.id.iv_focus_item);
        Glide.with(mContext).load(bean.getUrl()).into(itemImage);
        ViewGroup.LayoutParams params = itemImage.getLayoutParams();
        params.width = App.W / 2;
        params.height = App.W / 2 + 60;
        itemImage.setLayoutParams(params);

        Glide.with(mContext).load(bean.getUrl()).into((ImageView) helper.getView(R.id.iv_icon));
        helper.setText(R.id.tv_distance, bean.getDistance());
        helper.setText(R.id.tv_like, bean.getLike());
        SelectableRoundedImageView image1 = helper.getView(R.id.image1);
        SelectableRoundedImageView image2 = helper.getView(R.id.image2);
        SelectableRoundedImageView image3 = helper.getView(R.id.image3);
        TextView tv_follow_count = helper.getView(R.id.tv_follow_count);
        LinearLayout ll_follow = helper.getView(R.id.ll_follow);

        RelativeLayout rl_image = helper.getView(R.id.rl_image);

        switch (bean.getFollowCount()){
            case 0:
                rl_image.setVisibility(View.GONE);
                image1.setVisibility(View.GONE);
                image2.setVisibility(View.GONE);
                image3.setVisibility(View.GONE);
                tv_follow_count.setVisibility(View.GONE);
                ll_follow.setVisibility(View.VISIBLE);
                break;
            case 1:
                rl_image.setVisibility(View.VISIBLE);
                image1.setVisibility(View.VISIBLE);
                image2.setVisibility(View.VISIBLE);
                GlideUtils.loadingImg(mContext, image1, bean.getUrl());
                GlideUtils.loadingImg(mContext, image2, bean.getUrl());
                image3.setVisibility(View.GONE);
                tv_follow_count.setVisibility(View.GONE);
                ll_follow.setVisibility(View.VISIBLE);
                break;
            default:
                rl_image.setVisibility(View.VISIBLE);
                image1.setVisibility(View.VISIBLE);
                image2.setVisibility(View.VISIBLE);
                image3.setVisibility(View.VISIBLE);
                GlideUtils.loadingImg(mContext, image1, bean.getUrl());
                GlideUtils.loadingImg(mContext, image2, bean.getUrl());
                GlideUtils.loadingImg(mContext, image3, bean.getUrl());
                tv_follow_count.setVisibility(View.VISIBLE);
                tv_follow_count.setText(String.valueOf(bean.getFollowCount()));
                ll_follow.setVisibility(View.GONE);
                break;
        }
    }
}
