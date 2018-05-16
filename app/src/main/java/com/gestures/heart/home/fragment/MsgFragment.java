package com.gestures.heart.home.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alens.base.LazyLoadFragment;
import com.alens.base.immersion.ImmersionBar;
import com.alens.utils.GlideUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gestures.heart.R;
import com.gestures.heart.home.bean.MsgBean;
import com.gestures.heart.ui.CircleImageView;
import com.gestures.heart.ui.indexRV.SwipeMenuLayout;
import com.gestures.heart.ui.indexRV.decoration.DividerItemDecoration;
import com.gestures.heart.ui.indexRV.suspension.SuspensionDecoration;

import java.util.ArrayList;
import java.util.List;

public class MsgFragment extends LazyLoadFragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mManager;
    private SwipeDelMenuAdapter mAdapter = null;
    private SuspensionDecoration mDecoration;

    private List<MsgBean> mDatas = new ArrayList<>();
    private String url = "http://7xi8d6.com1.z0.glb.clouddn.com/20171123083218_5mhRLg_sakura.gun_23_11_2017_8_32_9_312.jpeg";

    public static MsgFragment newInstance() {
        MsgFragment fragment = new MsgFragment();
        return fragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_msg;
    }

    @Override
    protected void initView() {
        RelativeLayout layoutView = getView(R.id.set_layout_head);
        ImmersionBar.with(this).titleBarMarginTop(layoutView)
                .statusBarDarkFont(true, 0.2f)
                .init();

        getView(R.id.iv_back).setVisibility(View.INVISIBLE);
        ((TextView) getView(R.id.tv_title_name)).setText("消息");
        ImageView moreView = getView(R.id.iv_more);
        moreView.setImageResource(R.mipmap.ic_msg_right);
        moreView.setVisibility(View.VISIBLE);
        moreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mRecyclerView = getView(R.id.recyclerview);
        mRecyclerView.setLayoutManager(mManager = new LinearLayoutManager(getActivity()));

        mAdapter = new SwipeDelMenuAdapter(mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(mDecoration = new SuspensionDecoration(getActivity(), mDatas));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ((SwipeMenuLayout) adapter.getViewByPosition(mRecyclerView, position, R.id.layout_swipe)).quickClose();
                mDatas.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void lazyFetchData(){
        initDatas(30);
    }

    private void initDatas(int count) {
        mDatas = new ArrayList<>();
        mDatas.add(new MsgBean("0", "待处理事项", "5个请求待处理", "", true));
        mDatas.add(new MsgBean("1", "互动通知", "暂无通知消息", "", true));
        for (int i = 0; i < count; i++) {
            mDatas.add(new MsgBean(url, "这是标题" + i, "我是内容" + i, "2017-12-29", false));
        }
        mAdapter.setNewData(mDatas);
//        mDecoration.setmDatas(mDatas);
    }


    private class SwipeDelMenuAdapter extends BaseQuickAdapter<MsgBean, BaseViewHolder> {
        public SwipeDelMenuAdapter(List<MsgBean> list) {
            super(R.layout.layout_msg_item, list);
        }

        @Override
        protected void convert(final BaseViewHolder helper, MsgBean bean) {

            SwipeMenuLayout swipeMenuLayout = helper.getView(R.id.layout_swipe);
            CircleImageView headView = helper.getView(R.id.iv_head);
            if (bean.isTop()) {
                GlideUtils.loadingImg(getContext(), headView,
                        bean.getUrl().equals("0") ?
                        R.mipmap.ic_msg_wait : R.mipmap.ic_msg_notice);
//                headView.loadLocalImage(
//                        bean.getUrl().equals("0") ?
//                                R.mipmap.ic_msg_wait : R.mipmap.ic_msg_notice,
//                        Color.GRAY);
                helper.setBackgroundColor(R.id.content, Color.parseColor("#F0F0F0"));
                swipeMenuLayout.setSwipeEnable(false);
            } else {
                helper.setBackgroundColor(R.id.content, Color.WHITE);
                GlideUtils.loadingImg(getContext(), headView, url);
                swipeMenuLayout.setSwipeEnable(true);
            }

            helper.setText(R.id.msg_title, bean.getTitle());
            helper.setText(R.id.tv_msg, bean.getMsg());
            helper.setText(R.id.tv_date, bean.getDate());
            helper.addOnClickListener(R.id.btnDel);
        }
    }
}
