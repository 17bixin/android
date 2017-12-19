package com.gestures.heart.crop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gestures.heart.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

/**
 * 视频裁剪
 * */
public class VideoCropActivity extends AppCompatActivity {


    public static void startSelf(Context mContext){
        Intent intent = new Intent(mContext, VideoCropActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_crop);

        openPhoto();

    }

    private void openPhoto() {

        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofVideo())
                .selectionMode(PictureConfig.SINGLE)
                .previewVideo(true)
                .glideOverride(160, 160)
                .isCamera(false)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
//                    List<LocalMedia> selectList =  PictureSelector.obtainMultipleResult(data);
//                    LocalMedia item = selectList.get(0);
//
//                    TextView tv_text = (TextView) findViewById(R.id.tv_text);
//                    tv_text.setText(item.getPath());

                    break;
            }
        }
    }

}
