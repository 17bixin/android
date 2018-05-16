package com.gestures.heart.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alens.utils.L;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gestures.heart.R;
import com.gestures.heart.base.App;

import java.util.ArrayList;
import java.util.List;

/**
 * 问题反馈
 */
public class FeedBackActivity extends BaseSettingActivity implements View.OnClickListener {

    private ImageView iv_more;
    private EditText et_text, et_tel;
    private RecyclerView mRecyclerView;
    private List<String> mList = new ArrayList<>(5);
    private ListAdapter adapter = null;
    private static int REQUEST_VIDEO_CODE = 10;

    public static void starSelf(Context context) {
        Intent intent = new Intent(context, FeedBackActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

    }

    private void initView() {
        getView(R.id.iv_back).setOnClickListener(this);
        ((TextView) getView(R.id.tv_title_name)).setText("问题反馈");
        iv_more = getView(R.id.iv_more);
        iv_more.setVisibility(View.VISIBLE);
        iv_more.setBackgroundResource(R.mipmap.ic_seting_send);
        iv_more.setOnClickListener(this);

        et_text = getView(R.id.et_text);
        et_tel = getView(R.id.et_tel);

        et_text.setHeight(200);

        mList.add("+");
        mRecyclerView = getView(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new ListAdapter(mList);
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String item = (String) adapter.getItem(position);
                if (item.equals("+")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_VIDEO_CODE);
                }
            }
        });
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_feed_back;
    }

    @Override
    public int getBarID() {
        return R.id.set_layout_head;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_VIDEO_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                String imagePath = cursor.getString(
                        cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                L.d(imagePath);
                mList.remove("+");
                mList.add(imagePath);
                if(mList.size() < 3)
                    mList.add("+");
                adapter.setNewData(mList);
            }
            cursor.close();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_more:
                break;
        }
    }

    class ListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public ListAdapter(List<String> list) {
            super(R.layout.layout_feed_back_list, list);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ImageView itemView = helper.getView(R.id.iv_item);
            if (item.equals("+")) {
                Glide.with(mContext).load(R.mipmap.ic_set_add).into(itemView);
            } else {
                Glide.with(mContext).load(item).into(itemView);
            }
            ViewGroup.LayoutParams params = itemView.getLayoutParams();
            params.width = App.W/ 4 - 90;
            params.height = App.W / 4 - 90;
            itemView.setLayoutParams(params);
        }
    }
}
