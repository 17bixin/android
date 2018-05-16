package com.gestures.heart.wallet.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gestures.heart.R;
import com.gestures.heart.wallet.bean.IncomeBean;

import java.util.List;

public class IncomeAdapter extends BaseQuickAdapter<IncomeBean, BaseViewHolder> {

    public IncomeAdapter(List<IncomeBean> list) {
        super(R.layout.layout_income_item, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, IncomeBean bean) {
        helper.setText(R.id.tv_title, bean.getTitle());
        helper.setText(R.id.tv_date, bean.getDate());
        helper.setText(R.id.tv_source, bean.getSource());
        helper.setText(R.id.tv_money, bean.getMoney());
    }
}
