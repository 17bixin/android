package com.gestures.heart.home.adapter;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alens.utils.ColorUtils;
import com.alens.utils.GlideUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gestures.heart.R;
import com.gestures.heart.base.App;
import com.gestures.heart.home.bean.MeWorkBean;

import java.util.List;

public class WorkAdapter extends BaseQuickAdapter<MeWorkBean, ViewHolder> {

    public WorkAdapter(List<MeWorkBean> list) {
        super(R.layout.layout_me_work_item, list);
    }

    @Override
    protected void convert(ViewHolder helper, MeWorkBean bean) {

        ImageView workItem =  helper.getView(R.id.iv_work_item);
        GlideUtils.loadingImg(mContext, workItem, bean.getUrl());
        ViewGroup.LayoutParams params = workItem.getLayoutParams();
        params.width = App.W / 3;
        params.height = App.W / 3 + 50;
        workItem.setLayoutParams(params);

        helper.setText(R.id.tv_like, bean.getUrge());

        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(30);
        drawable.setColor(ColorUtils.getRandomColor(helper.getLayoutPosition()));
        helper.setText(R.id.tv_tag, bean.getTagName());
        helper.setTextBackDrawable(R.id.tv_tag, drawable);
    }
}

class ViewHolder extends BaseViewHolder {

    public ViewHolder(View view) {
        super(view);
    }

    public ViewHolder setTextBackDrawable(@IdRes int viewId, Drawable drawable) {
        TextView view = getView(viewId);
        view.setBackground(drawable);
        return this;
    }
}