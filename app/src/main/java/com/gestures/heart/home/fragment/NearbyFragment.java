package com.gestures.heart.home.fragment;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alens.base.LazyLoadFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gestures.heart.R;
import com.gestures.heart.home.adapter.NearbyAdapter;
import com.gestures.heart.home.bean.NearbyBean;
import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.annotation.RequestType;
import com.okhttplib.callback.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**附近*/
public class NearbyFragment extends LazyLoadFragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<NearbyBean> mList = new ArrayList<>();
    private NearbyAdapter adapter;
    private int nextPageNum = 1;
    private final int PAGE_SIZE = 10;

    public static NearbyFragment newInstance() {
        NearbyFragment fragment = new NearbyFragment();
        return fragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_short_video_item;
    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout = getView(R.id.swipeLayout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMore(true);
            }
        });

        mRecyclerView = getView(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new NearbyAdapter(mList);

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore(false);
            }
        }, mRecyclerView);

        mRecyclerView.setAdapter(adapter);
        adapter.setPreLoadNumber(3);
    }

    @Override
    protected void lazyFetchData(){
        mSwipeRefreshLayout.setRefreshing(true);
        loadMore(true);
    }

    private void loadMore(final boolean isRefresh) {

        final String url = "http://blog.csdn.net/wangye21323/article/details/75215579";

        adapter.setEnableLoadMore(false);
        OkHttpUtil.getDefault(NearbyFragment.this).doAsync(HttpInfo.Builder().setRequestType(RequestType.GET)
                .setUrl(url).build(), new Callback() {
            @Override
            public void onSuccess(HttpInfo info) throws IOException {

                adapter.setEnableLoadMore(true);
                mSwipeRefreshLayout.setRefreshing(false);

                ArrayList<NearbyBean> itemList = new ArrayList<>(PAGE_SIZE);
                for (int i = 0; i < 8; i++) {
                    String url;
                    if(i % 2 == 0)
                        url = "http://7xi8d6.com1.z0.glb.clouddn.com/20171123083218_5mhRLg_sakura.gun_23_11_2017_8_32_9_312.jpeg";
                    else
                        url = "http://7xi8d6.com1.z0.glb.clouddn.com/20171114101305_NIAzCK_rakukoo_14_11_2017_10_12_58_703.jpeg";
                    itemList.add(new NearbyBean(url , "11w", "1.8km")) ;
                }
                setData(isRefresh, itemList);
            }

            @Override
            public void onFailure(HttpInfo info) throws IOException {
                adapter.setEnableLoadMore(true);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void setData(boolean isRefresh, List<NearbyBean> data){
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            adapter.setNewData(data);
        } else {
            if (size > 0) {
                nextPageNum++;
                adapter.addData(data);
            }
        }

        if (size < PAGE_SIZE) {
            adapter.loadMoreEnd(false);
        } else {
            adapter.loadMoreComplete();
        }
    }


}
