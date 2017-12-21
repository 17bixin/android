package com.gestures.heart.crop;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gestures.heart.R;
import com.gestures.heart.base.BaseActivity;
import com.gestures.heart.base.immersion.ImmersionBar;
import com.gestures.heart.base.utils.T;
import com.gestures.heart.ui.crop.HgLVideoTrimmer;
import com.gestures.heart.ui.crop.interfaces.OnHgLVideoListener;
import com.gestures.heart.ui.crop.interfaces.OnTrimVideoListener;

/**
 * video crop
 * A-L-E-N-S
 * */
public class VideoCropActivity extends BaseActivity implements View.OnClickListener, OnTrimVideoListener, OnHgLVideoListener {

    private HgLVideoTrimmer mVideoTrimmer;
    private ProgressDialog mProgressDialog;

    private static final String VIDEO_PATH = "video_path_key";
    private static final String DURATION = "duration_key";

    private String videoPath = "";
    private int duration = 10;

    public static void startSelf(Context mContext, String path, int duration){
        Intent intent = new Intent(mContext, VideoCropActivity.class);
        intent.putExtra(VIDEO_PATH, path);
        intent.putExtra(DURATION, duration);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        videoPath = intent.getStringExtra(VIDEO_PATH);
        duration = intent.getIntExtra(DURATION, 10);

        initView();
    }

    private void initView() {

        ImmersionBar.with(this)
                .titleBar(R.id.set_layout_head, false)
                .transparentBar()
                .statusBarDarkFont(true, 0.2f)
                .init();

        getView(R.id.iv_back).setOnClickListener(this);
        ((TextView)getView(R.id.tv_title_name)).setText(getString(R.string.video_crop));
        View view = getView(R.id.tv_right);
        view.setVisibility(View.VISIBLE);
        view.setOnClickListener(this);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.trimming_progress));

        mVideoTrimmer = findViewById(R.id.timeLine);
        if (mVideoTrimmer != null) {
            mVideoTrimmer.setMaxDuration(duration);
            mVideoTrimmer.setOnTrimVideoListener(this);
            mVideoTrimmer.setOnHgLVideoListener(this);
            mVideoTrimmer.setVideoURI(Uri.parse(videoPath));
            mVideoTrimmer.setVideoInformationVisibility(true);
        }
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_video_crop;
    }

    @Override
    public void onTrimStarted() {
        mProgressDialog.show();
    }

    @Override
    public void getResult(final Uri contentUri) {
        mProgressDialog.cancel();
        try {
            final String cropPath = contentUri.getPath();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    T.showLong(VideoCropActivity.this, cropPath);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void cancelAction() {
        mProgressDialog.cancel();
        mVideoTrimmer.destroy();
        finish();
    }

    @Override
    public void onError(final String message) {
        mProgressDialog.cancel();
    }

    @Override
    public void onVideoPrepared() {}

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                mVideoTrimmer.onCancelClicked();
                finish();
                break;
            case R.id.tv_right:
                 mVideoTrimmer.onSaveClicked();
                break;
        }
    }
}
