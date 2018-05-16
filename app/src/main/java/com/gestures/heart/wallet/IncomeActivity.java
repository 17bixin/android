package com.gestures.heart.wallet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alens.base.immersion.ImmersionBar;
import com.gestures.heart.R;
import com.gestures.heart.base.BaseActivity;
import com.gestures.heart.ui.RollingNumberView;
import com.gestures.heart.ui.indexRV.decoration.DividerItemDecoration;
import com.gestures.heart.wallet.adapter.IncomeAdapter;
import com.gestures.heart.wallet.bean.IncomeBean;

import java.util.ArrayList;
import java.util.List;

/**收益 */
public class IncomeActivity extends BaseActivity implements View.OnClickListener{

    private RollingNumberView tvTotalIncome;
    private RecyclerView mRecyclerView;
    private IncomeAdapter mAdapter;
    private List<IncomeBean> mList = new ArrayList<>(10);

    {
        mList.add(new IncomeBean("小白菜01", "2017-12-21", "来自小视频", "￥200"));
        mList.add(new IncomeBean("小白菜02", "2017-12-21", "小视频", "￥200"));
        mList.add(new IncomeBean("小白菜03", "2017-12-21", "到支付宝", "￥200"));
        mList.add(new IncomeBean("小白菜04", "2017-12-21", "来自小视频", "￥200"));
        mList.add(new IncomeBean("小白菜05", "2017-12-21", "小视频", "￥200"));
        mList.add(new IncomeBean("小白菜06", "2017-12-21", "来自小视频", "￥200"));
        mList.add(new IncomeBean("小白菜07", "2017-12-21", "来自小视频", "￥200"));
        mList.add(new IncomeBean("小白菜08", "2017-12-21", "来自小视频", "￥200"));
        mList.add(new IncomeBean("小白菜09", "2017-12-21", "小视频", "￥200"));
        mList.add(new IncomeBean("小白菜10", "2017-12-21", "小视频", "￥200"));
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, IncomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout headView = getView(R.id.rl_head);
        headView.setBackground(getResources().getDrawable(R.color.A2D91D8));

        ImmersionBar.with(this).titleBar(headView, false).transparentBar().init();
        initView();
    }

    private void initView() {

        ImageView backBtn =  getView(R.id.iv_back);
        backBtn.setBackground(getResources().getDrawable(R.mipmap.ic_back));
        backBtn.setOnClickListener(this);

        TextView titleName = getView(R.id.tv_title_name);
        titleName.setTextColor(Color.WHITE);
        titleName.setText("收益");

        View view = LayoutInflater.from(this).inflate(R.layout.layout_income_head, null);

        tvTotalIncome = view.findViewById(R.id.tv_income);
        tvTotalIncome.setText("1288.00", TextView.BufferType.NORMAL, new Handler());
        view.findViewById(R.id.tv_withdraw).setOnClickListener(this);

        mRecyclerView = getView(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new IncomeAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.addHeaderView(view);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_income;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_withdraw:
                goActivity(WithdrawActivity.class);
                break;
        }
    }
}
