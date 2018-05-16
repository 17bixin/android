package com.gestures.heart.wallet.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gestures.heart.R;
import com.gestures.heart.base.CustomAdapter;
import com.gestures.heart.wallet.bean.RechargeItem;

public class RechargeWinAdapter extends CustomAdapter<RechargeItem>{

    public RechargeWinAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = getView(R.layout.layout_recharge_list_item);
            viewHolder.rl_layout = convertView.findViewById(R.id.rl_layout);
            viewHolder.tv_money_x = convertView.findViewById(R.id.tv_money_x);
            viewHolder.tv_money = convertView.findViewById(R.id.tv_money);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        RechargeItem item = getItem(position);
        viewHolder.rl_layout.setActivated(item.isChecked());
        viewHolder.tv_money_x.setText(item.getVirtual() +"心币");
        viewHolder.tv_money_x.setTextColor(item.isChecked() ? Color.WHITE : mContext.getColor(R.color.ED3649));
        viewHolder.tv_money.setText(item.getMoney() +"元");
        viewHolder.tv_money.setTextColor(item.isChecked() ? Color.WHITE : mContext.getColor(R.color.ED3649));
        return convertView;
    }

    static class ViewHolder {
        RelativeLayout rl_layout;
        TextView tv_money_x;
        TextView tv_money;
    }
}
