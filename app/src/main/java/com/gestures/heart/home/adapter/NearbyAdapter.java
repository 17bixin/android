package com.gestures.heart.home.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.alens.utils.GlideUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gestures.heart.R;
import com.gestures.heart.base.App;
import com.gestures.heart.home.bean.NearbyBean;

import java.util.List;

public class NearbyAdapter extends BaseQuickAdapter<NearbyBean, ViewHolder> {

    public NearbyAdapter(List<NearbyBean> list) {
        super(R.layout.layout_focus_item, list);
    }

    @Override
    protected void convert(ViewHolder helper, NearbyBean bean) {
        ImageView workItem = helper.getView(R.id.iv_focus_item);
        GlideUtils.loadingImg(mContext, workItem, bean.getUrl());
        ViewGroup.LayoutParams params = workItem.getLayoutParams();
        params.width = App.W / 2;
        params.height = App.W / 2 + 60;
        workItem.setLayoutParams(params);

        GlideUtils.loadingImg(mContext, (ImageView) helper.getView(R.id.iv_icon), bean.getUrl());
        helper.setText(R.id.tv_distance, bean.getDistance());
        helper.setText(R.id.tv_like, bean.getLike());
    }
}
