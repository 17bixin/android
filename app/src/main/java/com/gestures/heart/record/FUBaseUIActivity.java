package com.gestures.heart.record;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.gestures.heart.R;
import com.gestures.heart.camera.EffectAndFilterSelectAdapter;

/**
 * Base Acitivity, 负责界面UI的处理
 * Created by lirui on 2017/1/19.
 */

public abstract class FUBaseUIActivity extends Activity implements View.OnClickListener {

    private final String TAG = "FUBaseUIActivity";

    private RecyclerView mEffectRecyclerView;
    private EffectAndFilterSelectAdapter mEffectRecyclerAdapter;
    private BottomSheetBehavior<View> behavior;




    protected ImageView mRecordingBtn;
    private int mRecordStatus = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_base);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = 0.7f;
        getWindow().setAttributes(params);



        mEffectRecyclerView = findViewById(R.id.effect_recycle_view);
        mEffectRecyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 4));
        mEffectRecyclerAdapter = new EffectAndFilterSelectAdapter(mEffectRecyclerView, EffectAndFilterSelectAdapter.RECYCLEVIEW_TYPE_EFFECT);
        mEffectRecyclerAdapter.setOnItemSelectedListener(new EffectAndFilterSelectAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int itemPosition) {
                Log.d(TAG, "effect item selected " + itemPosition);
                onEffectItemSelected(EffectAndFilterSelectAdapter.EFFECT_ITEM_FILE_NAME[itemPosition]);
                //showHintText(mEffectRecyclerAdapter.getHintStringByPosition(itemPosition));
            }
        });
        mEffectRecyclerView.setAdapter(mEffectRecyclerAdapter);


        mRecordingBtn = findViewById(R.id.ivRecord);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivSwitchCamera:
                onCameraChange();
                break;
            case R.id.ivRecord:
                mRecordingBtn.setVisibility(View.INVISIBLE);
                if (mRecordStatus == 0) {
                    onStartRecording();
                    mRecordStatus ^= 1;
                } else {
                    onStopRecording();
                    mRecordStatus ^= 1;
                }
                break;
        }
    }

    Handler mMainHandler;





    /**
     * 道具贴纸选择
     *
     * @param effectItemName 道具贴纸文件名
     */
    abstract protected void onEffectItemSelected(String effectItemName);











    /**
     * 相机切换
     */
    abstract protected void onCameraChange();

    /**
     * 开始录制
     */
    abstract protected void onStartRecording();

    /**
     * 停止录制
     */
    abstract protected void onStopRecording();

}
