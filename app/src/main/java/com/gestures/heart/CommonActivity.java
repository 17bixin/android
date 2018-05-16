package com.gestures.heart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alens.utils.GlideUtils;
import com.gestures.heart.base.BaseActivity;
import com.gestures.heart.login.LoginOrRegisterActivity;
import com.gestures.heart.ui.CircleImageView;

public class CommonActivity extends BaseActivity {

    public static void startActivity(Context context){
        Intent intent = new Intent(context, CommonActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getView(R.id.tv_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CommonActivity.this, LoginOrRegisterActivity.class));
            }
        });

        getView(R.id.tv_count).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onButtonPressed(new Random().nextInt(100) + 1);
            }
        });

        final String headUrl = "http://b.hiphotos.baidu.com/album/pic/item/caef76094b36acafe72d0e667cd98d1000e99c5f.jpg?psign=e72d0e667cd98d1001e93901213fb80e7aec54e737d1b867";

        CircleImageView headView = getView(R.id.iv_head);
        GlideUtils.loadingImg(this, headView, headUrl);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_common;
    }
}
