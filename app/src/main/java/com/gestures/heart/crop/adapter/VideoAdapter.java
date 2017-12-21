package com.gestures.heart.crop.adapter;


import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gestures.heart.R;
import com.gestures.heart.base.utils.DateUtils;
import com.gestures.heart.base.utils.DisplayUtil;
import com.gestures.heart.crop.bean.VideoEntity;

import java.util.List;

public class VideoAdapter extends BaseQuickAdapter<VideoEntity, BaseViewHolder> {

    public VideoAdapter(List<VideoEntity> mList) {
        super(R.layout.item_video_select, mList);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoEntity bean) {

        ImageView itemView = helper.getView(R.id.iv_item);
        Glide.with(mContext).load(bean.filePath).into(itemView);
        ViewGroup.LayoutParams params = itemView.getLayoutParams();
        params.width = DisplayUtil.getScreenWidth(mContext) / 3;
        params.height = DisplayUtil.getScreenWidth(mContext) / 3 + 40;
        itemView.setLayoutParams(params);
        helper.setText(R.id.tv_duration_text, DateUtils.timeParse(bean.duration));
    }

}
