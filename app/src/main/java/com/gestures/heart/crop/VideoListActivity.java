package com.gestures.heart.crop;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gestures.heart.R;
import com.gestures.heart.base.BaseActivity;
import com.gestures.heart.base.immersion.ImmersionBar;
import com.gestures.heart.base.utils.L;
import com.gestures.heart.crop.adapter.VideoAdapter;
import com.gestures.heart.crop.bean.VideoEntity;

import java.util.ArrayList;
import java.util.List;
/**
 * 视频选择列表
 * */
public class VideoListActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private VideoAdapter adapter = null;
    private List<VideoEntity> dataList = new ArrayList<>(10);

    public static void startSelf(Context context){
        Intent intent = new Intent(context, VideoListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        getVideoListData();
    }

    private void initView() {

        ImmersionBar.with(this)
                .titleBar(R.id.set_layout_head, false)
                .transparentBar()
                .statusBarDarkFont(true, 0.2f)
                .init();

        getView(R.id.iv_back).setVisibility(View.INVISIBLE);
        ((TextView)getView(R.id.tv_title_name)).setText(getString(R.string.video_select));

        mRecyclerView = getView(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new VideoAdapter(dataList);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                VideoEntity item = (VideoEntity)adapter.getItem(position);
                L.d(item.filePath + " ----------------  ");
                VideoCropActivity.startSelf(VideoListActivity.this, item.filePath, item.duration);
            }
        });

        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_video_list;
    }


    private void getVideoListData(){
        dataList.clear();
        Cursor cursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Video.DEFAULT_SORT_ORDER);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                String title = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                String url = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                int duration = cursor.getInt(cursor
                                .getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                int size = (int) cursor.getLong(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
                L.d(id + "\n" + title + "\n" + url + "\n" + duration + "\n" + size);
                dataList.add(new VideoEntity(id, title, url, size, duration));
            } while (cursor.moveToNext());

        }
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }

        adapter.setNewData(dataList);
    }

}
