package com.gestures.heart.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gestures.heart.R;
import com.gestures.heart.ui.indexRV.IndexBar;
import com.gestures.heart.ui.indexRV.SwipeMenuLayout;
import com.gestures.heart.ui.indexRV.bean.CityBean;
import com.gestures.heart.ui.indexRV.decoration.DividerItemDecoration;
import com.gestures.heart.ui.indexRV.suspension.SuspensionDecoration;

import java.util.ArrayList;
import java.util.List;

/** 黑名单
 * */
public class BlackListActivity extends BaseSettingActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mManager;
    private SwipeDelMenuAdapter mAdapter = null;
    private SuspensionDecoration mDecoration;

    private IndexBar mIndexBar;
    private TextView mTvSideBarHint;

    private List<CityBean> mDatas = new ArrayList<>();

    private static final String INDEX_STRING_TOP = "↑";

    public static void startBlackListActivity(Context context) {
        Intent intent = new Intent(context, BlackListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getView(R.id.iv_back).setOnClickListener(this);
        ((TextView) getView(R.id.tv_title_name)).setText("黑名单");

        mRecyclerView = getView(R.id.recyclerview);
        mRecyclerView.setLayoutManager(mManager = new LinearLayoutManager(this));


        mAdapter = new SwipeDelMenuAdapter(mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(mDecoration = new SuspensionDecoration(this, mDatas));
        //如果add两个，那么按照先后顺序，依次渲染。
        //mRv.addItemDecoration(new TitleItemDecoration2(this,mDatas));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        mTvSideBarHint = getView(R.id.tvSideBarHint);//HintTextView
        mIndexBar = getView(R.id.indexBar);

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ((SwipeMenuLayout) adapter.getViewByPosition(mRecyclerView, position, R.id.layout_swipe)).quickClose();
                    mDatas.remove(position);
                    mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                            .setNeedRealIndex(true)//设置需要真实的索引
                            .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
                            .setmSourceDatas(mDatas)//设置数据
                            .invalidate();
                    mAdapter.notifyDataSetChanged();
            }
        });

        initDatas(getResources().getStringArray(R.array.provinces));
    }

    @Override
    public int getBarID() {
        return R.id.set_layout_head;
    }

    private void initDatas(final String[] data) {
        mDatas = new ArrayList<>();
        //微信的头部 也是可以右侧IndexBar导航索引的，
        // 但是它不需要被ItemDecoration设一个标题titile
        mDatas.add((CityBean) new CityBean("新的朋友").setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
        mDatas.add((CityBean) new CityBean("群聊").setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
        mDatas.add((CityBean) new CityBean("标签").setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
        mDatas.add((CityBean) new CityBean("公众号").setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
        for (int i = 0; i < data.length; i++) {
            CityBean cityBean = new CityBean();
            cityBean.setCity(data[i]);
            mDatas.add(cityBean);
        }
        mAdapter.setNewData(mDatas);

        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
                .setmSourceDatas(mDatas)//设置数据
                .invalidate();
        mDecoration.setmDatas(mDatas);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_black_list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }


    private class SwipeDelMenuAdapter extends BaseQuickAdapter<CityBean, BaseViewHolder> {
        public SwipeDelMenuAdapter(List<CityBean> list) {
            super(R.layout.item_city_swipe, list);
        }

        @Override
        protected void convert(final BaseViewHolder helper, CityBean bean) {

            String url = "http://7xi8d6.com1.z0.glb.clouddn.com/20171123083218_5mhRLg_sakura.gun_23_11_2017_8_32_9_312.jpeg";
            Glide.with(mContext).load(url).into((ImageView) helper.getView(R.id.ivAvatar));
            helper.setText(R.id.tvCity, bean.getCity());
            helper.addOnClickListener(R.id.btnDel);
        }
    }
}
