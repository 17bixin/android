package com.gestures.heart.home.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.alens.utils.GlideUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gestures.heart.R;
import com.gestures.heart.base.App;

import java.util.List;

public class ChallengItemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ChallengItemAdapter(List<String> list) {
        super(R.layout.layout_challeng_adapter, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, String url) {
        ImageView imageView = helper.getView(R.id.imageView);
        GlideUtils.loadingImg(mContext, imageView, url);
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        params.width = App.W / 4;
        params.height = App.W / 4 + 40;
        imageView.setLayoutParams(params);
    }
}

