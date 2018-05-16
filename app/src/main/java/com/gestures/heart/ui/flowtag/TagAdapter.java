package com.gestures.heart.ui.flowtag;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alens.utils.ColorUtils;
import com.gestures.heart.R;
import com.gestures.heart.base.CustomAdapter;

public class TagAdapter extends CustomAdapter<String>{

    public TagAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = getView(R.layout.layout_tag_item);
            viewHolder.text = convertView.findViewById(R.id.tv_tag);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(30);
        drawable.setColor(ColorUtils.getRandomColor(position));
        viewHolder.text.setText(getItem(position));
        viewHolder.text.setBackground(drawable);
        return convertView;
    }

    static class ViewHolder {
        TextView text;
    }
}
