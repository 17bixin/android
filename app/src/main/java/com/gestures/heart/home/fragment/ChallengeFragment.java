package com.gestures.heart.home.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alens.base.LazyLoadFragment;
import com.alens.utils.L;
import com.bumptech.glide.Glide;
import com.gestures.heart.R;
import com.gestures.heart.base.App;
import com.gestures.heart.home.adapter.SnapAdapter;
import com.gestures.heart.home.bean.Challeng;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 挑战
 */
public class ChallengeFragment extends LazyLoadFragment implements OnBannerListener {

    private RecyclerView mRecyclerView;
    private SnapAdapter snapAdapter = null;

    private List<Challeng> listData = new ArrayList<>();
    private List<String> imageList = new ArrayList<>();
    private List<String> mList = new ArrayList<>(10);

    public static ChallengeFragment newInstance() {
        ChallengeFragment fragment = new ChallengeFragment();
        return fragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_challenge_layout;
    }

    @Override
    protected void initView() {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_challenge_head, null);
        Banner banner = view.findViewById(R.id.banner);
        banner.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, App.H / 4));

        mRecyclerView = getView(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        snapAdapter = new SnapAdapter(listData);
        mRecyclerView.setAdapter(snapAdapter);

        banner.setImages(imageList)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .start();

        snapAdapter.addHeaderView(view);
    }

    @Override
    protected void lazyFetchData(){
        listData.add(new Challeng("标题1", "10", mList));
        listData.add(new Challeng("标题2", "11", mList));
        listData.add(new Challeng("标题3", "12", mList));
        listData.add(new Challeng("标题4", "13", mList));
    }

    @Override
    public void OnBannerClick(int position) {
        L.d("你点击了：" + position);
    }

    class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context)
                    .load(path)
                    .into(imageView);
        }
    }


    {
        imageList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg");
        imageList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        imageList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        imageList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        imageList.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");

        mList.add("http://bpic.588ku.com/element_origin_min_pic/00/00/05/115732f19cc0079.jpg");
        mList.add("http://bpic.588ku.com/element_origin_min_pic/00/00/05/115732f1ac12d1d.jpg");
        mList.add("http://bpic.588ku.com/element_origin_min_pic/00/00/05/115732f1bad97d1.jpg");
        mList.add("http://bpic.588ku.com/element_origin_min_pic/00/00/05/115732f1c83c228.jpg");
        mList.add("http://bpic.588ku.com/element_origin_min_pic/00/00/05/115732f1d53e3dd.jpg");
        mList.add("http://bpic.588ku.com/element_origin_min_pic/00/00/05/115732f1e37fea9.jpg");
        mList.add("http://bpic.588ku.com/element_origin_min_pic/00/00/05/115732f1ef4d709.jpg");
        mList.add("http://bpic.588ku.com/element_origin_min_pic/00/00/05/115732f20b3ea10.jpg");
    }
}
