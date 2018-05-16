package com.gestures.heart.home.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alens.base.immersion.ImmersionBar;
import com.alens.utils.GlideUtils;
import com.alens.utils.L;
import com.dialogui.DialogUIUtils;
import com.dialogui.bean.TieBean;
import com.dialogui.listener.DialogUIItemListener;
import com.gestures.heart.CommonActivity;
import com.gestures.heart.R;
import com.gestures.heart.home.LookMeActivity;
import com.gestures.heart.settings.SettingsActivity;
import com.gestures.heart.ui.CircleImageView;
import com.gestures.heart.ui.flowtag.FlowTagLayout;
import com.gestures.heart.ui.flowtag.OnTagClickListener;
import com.gestures.heart.ui.flowtag.TagAdapter;
import com.gestures.heart.wallet.IncomeActivity;
import com.gestures.heart.wallet.RechargeActivity;

import java.util.ArrayList;
import java.util.List;

import static com.dialogui.DialogUIUtils.showToast;

public class MeWorkHeader extends LinearLayout implements View.OnClickListener {

    private ImageView iv_bg, iv_setting, iv_more;
    private CircleImageView headView;
    private Toolbar mToolbar;

    private TagAdapter adapter = null;
    private boolean isMe = true;

    public MeWorkHeader(Context context) {
        super(context);
    }

    public MeWorkHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MeWorkHeader);
        isMe = ta.getBoolean(R.styleable.MeWorkHeader_isMe, true);
        ta.recycle();
        initView(context);
    }

    public MeWorkHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_me_head, this);

        mToolbar = view.findViewById(R.id.toolbar);
        ImmersionBar.setTitleBar((Activity) context, mToolbar);

        iv_setting = view.findViewById(R.id.iv_setting);
        iv_setting.setOnClickListener(this);
        iv_more = view.findViewById(R.id.iv_more);
        iv_more.setOnClickListener(this);

        final String headUrl = "http://b.hiphotos.baidu.com/album/pic/item/caef76094b36acafe72d0e667cd98d1000e99c5f.jpg?psign=e72d0e667cd98d1001e93901213fb80e7aec54e737d1b867";
        iv_bg = view.findViewById(R.id.iv_bg);

        GlideUtils.loadingImgWithBlur(getContext(), iv_bg, headUrl);

        headView = view.findViewById(R.id.iv_head);
        GlideUtils.loadingImg(getContext(), headView, headUrl);
        headView.setOnClickListener(this);

        view.findViewById(R.id.ll_head_photo).setOnClickListener(this);
        view.findViewById(R.id.tv_income).setOnClickListener(this);
        view.findViewById(R.id.tv_recharge).setOnClickListener(this);

        LinearLayout ll_head_photo = view.findViewById(R.id.ll_me_layout);
        TextView tv_sendMes = view.findViewById(R.id.tv_me_layout);
        tv_sendMes.setOnClickListener(this);
        if (isMe) {
            ll_head_photo.setVisibility(View.VISIBLE);
            tv_sendMes.setVisibility(View.GONE);
            iv_setting.setVisibility(VISIBLE);
            iv_more.setVisibility(View.GONE);
        } else {
            ll_head_photo.setVisibility(View.GONE);
            tv_sendMes.setVisibility(View.VISIBLE);
            iv_setting.setVisibility(GONE);
            iv_more.setVisibility(View.VISIBLE);
        }

        FlowTagLayout tagLayout = view.findViewById(R.id.color_flow_layout);
        adapter = new TagAdapter(context);
        tagLayout.setAdapter(adapter);
        tagLayout.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
                L.d("颜色:" + parent.getAdapter().getItem(position));
            }
        });
        setTagLayoutData();
    }

    private void setTagLayoutData() {
        List<String> dataSource = new ArrayList<>();
        dataSource.add("红色");
        dataSource.add("黑色");
        dataSource.add("屌丝");
        dataSource.add("彩虹色");
        adapter.onlyAddAll(dataSource);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_setting:   // 设置
                SettingsActivity.startSettingsActivity(getContext());
                break;
            case R.id.iv_more:   // 更多

                List<TieBean> strings = new ArrayList<TieBean>();
                strings.add(new TieBean("举报"));
                strings.add(new TieBean("拉黑"));
                DialogUIUtils.showSheet(getContext(), strings, "取消", Gravity.BOTTOM, true, true, new DialogUIItemListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        showToast(text + "---" + position);
                    }

                    @Override
                    public void onBottomBtnClick() {
                        showToast("取消");
                    }
                }).show();


                break;
            case R.id.iv_head: // 头像 image
                LookMeActivity.startLookMeActivity(getContext());
                break;
            case R.id.ll_head_photo:  // 头像拍摄
                CommonActivity.startActivity(getContext());
                break;
            case R.id.tv_me_layout:  // 发消息

                break;
            case R.id.tv_income:  // 收益
                IncomeActivity.startActivity(getContext());
                break;
            case R.id.tv_recharge:  // 心币
                RechargeActivity.startActivity(getContext());
                break;

        }
    }
}
