package com.gestures.heart.wallet;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alens.base.immersion.ImmersionBar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dialogui.DialogUIUtils;
import com.gestures.heart.R;
import com.gestures.heart.base.BaseActivity;
import com.gestures.heart.ui.RollingNumberView;
import com.gestures.heart.ui.indexRV.decoration.DividerItemDecoration;
import com.gestures.heart.wallet.adapter.RechargeWinAdapter;
import com.gestures.heart.wallet.bean.RechargeBean;
import com.gestures.heart.wallet.bean.RechargeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 充值
 */
public class RechargeActivity extends BaseActivity implements View.OnClickListener {

    private RollingNumberView tvTotalIncome;
    private RecyclerView mRecyclerView;
    private RechargeAdapter mAdapter;
    private List<RechargeBean> mList = new ArrayList<>(10);

    {
        mList.add(new RechargeBean("小白菜01", "2017-12-21", "余额：1024", "￥200"));
        mList.add(new RechargeBean("小白菜02", "2017-12-21", "余额：1024", "￥200"));
        mList.add(new RechargeBean("小白菜03", "2017-12-21", "余额：1024", "￥200"));
        mList.add(new RechargeBean("小白菜04", "2017-12-21", "余额：1024", "￥200"));
        mList.add(new RechargeBean("小白菜05", "2017-12-21", "余额：1024", "￥200"));
        mList.add(new RechargeBean("小白菜06", "2017-12-21", "余额：1024", "￥200"));
        mList.add(new RechargeBean("小白菜07", "2017-12-21", "余额：1024", "￥200"));
        mList.add(new RechargeBean("小白菜08", "2017-12-21", "余额：1024", "￥200"));
        mList.add(new RechargeBean("小白菜09", "2017-12-21", "余额：1024", "￥200"));
        mList.add(new RechargeBean("小白菜10", "2017-12-21", "余额：1024", "￥200"));
    }

    private List<RechargeItem> listData = new ArrayList<>();

    {
        listData.add(new RechargeItem("42", "6", true));
        listData.add(new RechargeItem("210", "30", false));
        listData.add(new RechargeItem("686", "98", false));
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, RechargeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RelativeLayout headView = getView(R.id.rl_head);
        headView.setBackground(getResources().getDrawable(R.color.ED3649));

        ImmersionBar.with(this).titleBar(headView, false).transparentBar().init();
        initView();
    }

    private void initView() {
        ImageView backBtn = getView(R.id.iv_back);
        backBtn.setBackground(getResources().getDrawable(R.mipmap.ic_back));
        backBtn.setOnClickListener(this);

        TextView titleName = getView(R.id.tv_title_name);
        titleName.setTextColor(Color.WHITE);
        titleName.setText("心币");

        View view = LayoutInflater.from(this).inflate(R.layout.layout_recharge_head, null);
        tvTotalIncome = view.findViewById(R.id.tv_income);
        tvTotalIncome.setText("12999.00", TextView.BufferType.NORMAL, new Handler());
        view.findViewById(R.id.tv_recharge).setOnClickListener(this);

        mRecyclerView = getView(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new RechargeAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.addHeaderView(view);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_income;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_recharge:
                popRechargeWin();
                break;
        }
    }

    private void popRechargeWin() {
        View popWin = LayoutInflater.from(this).inflate(R.layout.layout_recharge_window, null);
        TextView comitBtn = popWin.findViewById(R.id.tv_commit_recharge);
        GridView gridView = popWin.findViewById(R.id.gridView);
        final RechargeWinAdapter gridAdapter = new RechargeWinAdapter(this);
        gridAdapter.onlyAddAll(listData);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(int i = 0; i< listData.size(); i++){
                    RechargeItem item = listData.get(i);
                    item.setChecked(i == position);
                }
                gridAdapter.notifyDataSetChanged();
            }
        });

        final Dialog dialog = DialogUIUtils.showCustomBottomAlert(this, popWin).show();
        popWin.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    class RechargeAdapter extends BaseQuickAdapter<RechargeBean, BaseViewHolder> {

        public RechargeAdapter(List<RechargeBean> list) {
            super(R.layout.layout_income_item, list);
        }

        @Override
        protected void convert(BaseViewHolder helper, RechargeBean bean) {
            helper.setText(R.id.tv_title, bean.getTitle());
            helper.setText(R.id.tv_date, bean.getDate());
            helper.setText(R.id.tv_source, bean.getSource());
            helper.setText(R.id.tv_money, bean.getMoney());
        }
    }
}
