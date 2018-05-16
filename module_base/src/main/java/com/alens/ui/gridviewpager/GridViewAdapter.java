package com.alens.ui.gridviewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alens.R;
import com.alens.utils.GlideUtils;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    private List<Model> mData;
    private LayoutInflater inflater;
    /**
     * 页数下标,从0开始(当前是第几页)
     */
    private int curIndex;
    /**
     * 每一页显示的个数
     */
    private int pageSize;

    private Context mContext;

    public GridViewAdapter(Context context, List<Model> mData, int curIndex, int pageSize) {
        inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mData = mData;
        this.curIndex = curIndex;
        this.pageSize = pageSize;
    }

    /**
     * 先判断数据集的大小是否足够显示满本页？mData.size() > (curIndex+1)*pageSize,
     * 如果够，则直接返回每一页显示的最大条目个数pageSize,
     * 如果不够，则有几项返回几,(mData.size() - curIndex * pageSize);(也就是最后一页的时候就显示剩余item)
     */
    @Override
    public int getCount() {
        return mData.size() > (curIndex + 1) * pageSize ? pageSize : (mData.size() - curIndex * pageSize);

    }

    @Override
    public Model getItem(int position) {
        return mData.get(position + curIndex * pageSize);
    }

    @Override
    public long getItemId(int position) {
        return position + curIndex * pageSize;
    }


    private int clickPos = -1;

    public void setSelect(int posClick) {
        this.clickPos = posClick;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_gridview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ll_layout = convertView.findViewById(R.id.ll_layout);
            viewHolder.giftName = convertView.findViewById(R.id.tv_name);
            viewHolder.giftImg = convertView.findViewById(R.id.imageView);
            viewHolder.giftPrice = convertView.findViewById(R.id.tv_price);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /**
         * 在给View绑定显示的数据时，计算正确的position = position + curIndex * pageSize，
         */
        Model item = getItem(position);
        if (clickPos == position)
            viewHolder.ll_layout.setBackgroundResource(R.drawable.shape_gift_item);
        else
            viewHolder.ll_layout.setBackgroundResource(android.R.color.transparent);
        viewHolder.giftName.setText(item.getName());
        GlideUtils.loadingImg(mContext, viewHolder.giftImg, item.getImgUrl());
        viewHolder.giftPrice.setText(item.getPrice() + "");

        return convertView;
    }

    private class ViewHolder {
        LinearLayout ll_layout;
        TextView giftName;
        ImageView giftImg;
        TextView giftPrice;
    }
}