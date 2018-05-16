package com.gestures.heart.home.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alens.base.LazyLoadFragment;
import com.alens.base.immersion.ImmersionBar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gestures.heart.R;
import com.gestures.heart.home.bean.CasinoBean;
import com.gestures.heart.ui.indexRV.decoration.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 多人交友
 */
public class CasinoFragment extends LazyLoadFragment {

    private RecyclerView mRecyclerView;
    private CasinoAdapater mAdapter = null;

    private List<CasinoBean> mList = new ArrayList<>(10);

    {
        mList.add(new CasinoBean("家乡人说家乡话", 1000));
        mList.add(new CasinoBean("Android社区", 1000));
        mList.add(new CasinoBean("IOS社区", 700));
        mList.add(new CasinoBean("Java社区", 200));
        mList.add(new CasinoBean("PHP论坛", 5000));
        mList.add(new CasinoBean("APK 文件怎么打包", 1000));
        mList.add(new CasinoBean("Android Studio", 1000));
        mList.add(new CasinoBean("比心视频怎么收费", 1000));
        mList.add(new CasinoBean("Android Studio", 1000));
        mList.add(new CasinoBean("Android Studio", 1000));
    }


    public static CasinoFragment newInstance() {
        CasinoFragment fragment = new CasinoFragment();
        return fragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_casino;
    }

    @Override
    protected void initView() {

        RelativeLayout layoutView = getView(R.id.set_layout_head);
        ImmersionBar.with(this).titleBarMarginTop(layoutView)
                .statusBarDarkFont(true, 0.2f)
                .init();

        getView(R.id.iv_back).setVisibility(View.INVISIBLE);
        ((TextView) getView(R.id.tv_title_name)).setText("房间分类");

        mRecyclerView = getView(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new CasinoAdapater(mList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void lazyFetchData(){}

    class CasinoAdapater extends BaseQuickAdapter<CasinoBean, BaseViewHolder> {
        public CasinoAdapater(List<CasinoBean> list) {
            super(R.layout.layout_casino_item, list);
        }

        @Override
        protected void convert(BaseViewHolder helper, CasinoBean bean) {
            helper.setText(R.id.tv_title, bean.getTitle());
            helper.setText(R.id.tv_count, "在线人数：" + bean.getCount());
        }
    }
}
