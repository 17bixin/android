package com.gestures.heart.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alens.utils.GlideUtils;
import com.gestures.heart.R;
import com.gestures.heart.ui.CircleImageView;
import com.gestures.heart.ui.NiceTextView;

/**个人信息*/
public class PersonalActivity extends BaseSettingActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        getView(R.id.iv_back).setOnClickListener(this);
        ((TextView)getView(R.id.tv_title_name)).setText("编辑资料");

        String url = "http://bpic.588ku.com/element_origin_min_pic/00/00/05/115732f19cc0079.jpg";

        CircleImageView headView = getView(R.id.iv_head);
        GlideUtils.loadingImg(this, headView, url);

        NiceTextView tvName = getView(R.id.tv_name);
        tvName.setRightString("豆腐");
        NiceTextView tvSex = getView(R.id.tv_sex);
        tvSex.setRightString("男");
        NiceTextView tvConstel = getView(R.id.tv_constellation);
        tvConstel.setRightString("水瓶座");

        NiceTextView tvAddr = getView(R.id.tv_addr);
        tvAddr.setRightString("广东省珠海市");

        NiceTextView tvWork = getView(R.id.tv_work);
        tvWork.setRightString("设计师");

    }

    @Override
    public int getBarID() {
        return R.id.set_layout_head;
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_personal;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
