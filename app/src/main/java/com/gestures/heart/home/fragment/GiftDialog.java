package com.gestures.heart.home.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alens.ui.gridviewpager.GridItemClickListener;
import com.alens.ui.gridviewpager.GridViewPager;
import com.alens.ui.gridviewpager.Model;
import com.gestures.heart.R;

import java.util.ArrayList;
import java.util.List;

public class GiftDialog extends DialogFragment {

    private Dialog dialog ;

    public static final GiftDialog newInstance(){
        GiftDialog fragment = new GiftDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_gift_win, null, false);
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        initDialogStyle(rootView);
        initView(rootView);
        return dialog;
    }

    private void initView(View rootView) {
        GridViewPager mGridViewPager = rootView.findViewById(R.id.mGridViewPager);
        mGridViewPager
                .setPageSize(8)
                .setGridItemClickListener(new GridItemClickListener() {
                    @Override
                    public void click(int pos, int position, String str) {
                        Log.d("123", pos + "/" + str);
                    }
                })
                .init(initData());
    }

    private void initDialogStyle(View view) {
        dialog = new Dialog(getActivity(), R.style.CustomGiftDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        window.setAttributes(lp);
    }

    private List<Model> initData() {
        List<Model> mData = new ArrayList<>();
        mData.add(new Model("比心礼物1", "http://bpic.588ku.com/element_origin_min_pic/00/00/05/115732f19cc0079.jpg", 35));
        mData.add(new Model("比心礼物2", "http://bpic.588ku.com/element_origin_min_pic/00/00/05/115732f1ac12d1d.jpg", 34));
        mData.add(new Model("比心礼物3", "http://bpic.588ku.com/element_origin_min_pic/00/00/05/115732f1bad97d1.jpg", 33));
        mData.add(new Model("比心礼物4", "http://bpic.588ku.com/element_origin_min_pic/00/00/05/115732f1c83c228.jpg", 32));
        mData.add(new Model("比心礼物5", "http://bpic.588ku.com/element_origin_min_pic/00/00/05/115732f1d53e3dd.jpg", 31));
        mData.add(new Model("比心礼物6", "http://bpic.588ku.com/element_origin_min_pic/00/00/05/115732f1e37fea9.jpg", 30));
        mData.add(new Model("比心礼物7", "http://bpic.588ku.com/element_origin_min_pic/00/00/05/115732f1ef4d709.jpg", 29));
        mData.add(new Model("比心礼物8", "http://bpic.588ku.com/element_origin_min_pic/00/00/05/115732f20b3ea10.jpg", 28));
        mData.add(new Model("比心礼物9", "http://bpic.588ku.com/element_origin_min_pic/00/00/05/115732f20b3ea10.jpg", 27));
        mData.add(new Model("比心礼物10", "http://bpic.588ku.com/element_origin_min_pic/00/00/05/115732f19cc0079.jpg", 26));
        mData.add(new Model("比心礼物11", "http://bpic.588ku.com/element_origin_min_pic/00/00/05/115732f1e37fea9.jpg", 25));
        mData.add(new Model("比心礼物12", "http://bpic.588ku.com/element_origin_min_pic/00/00/05/115732f19cc0079.jpg", 24));
        mData.add(new Model("比心礼物13", "http://bpic.588ku.com/element_origin_min_pic/00/00/05/115732f1bad97d1.jpg", 23));
        mData.add(new Model("比心礼物14", "http://bpic.588ku.com/element_origin_min_pic/00/00/05/115732f1ac12d1d.jpg", 22));
        return mData;
    }


}
