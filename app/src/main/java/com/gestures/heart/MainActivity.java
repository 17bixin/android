package com.gestures.heart;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gestures.heart.record.FUDualInputToTextureExampleActivity;
import com.gestures.heart.crop.VideoCropActivity;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        PermissionsUtil.requestPermission(getBaseContext(),
                new PermissionListener() {
                    @Override
                    public void permissionGranted(@NonNull String[] permission) {

                    }

                    @Override
                    public void permissionDenied(@NonNull String[] permission) {

                    }
                },
        new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO});

        findViewById(R.id.tv_video_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FUDualInputToTextureExampleActivity.class));
            }
        });

        findViewById(R.id.tv_video_crop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoCropActivity.startSelf(MainActivity.this);
            }
        });
    }

}
