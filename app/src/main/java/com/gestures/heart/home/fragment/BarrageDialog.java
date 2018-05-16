package com.gestures.heart.home.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alens.utils.GlideUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gestures.heart.R;
import com.gestures.heart.base.App;
import com.gestures.heart.home.bean.Barrage;
import com.gestures.heart.ui.indexRV.decoration.AlensDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class BarrageDialog extends DialogFragment {

    private Dialog dialog;
    private RecyclerView mRecyclerView;
    private List<Barrage> mList = new ArrayList<>();
    private BarrageAdapter mAdapter = null;
    private TextView tanmuCount;

    private String headUrl = "http://7xi8d6.com1.z0.glb.clouddn.com/20171123083218_5mhRLg_sakura.gun_23_11_2017_8_32_9_312.jpeg";

    public static final BarrageDialog newInstance() {
        BarrageDialog fragment = new BarrageDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_barrage_win, null, false);
        initDialogStyle(rootView);
        initView(rootView);
        return dialog;
    }

    private void initView(View rootView) {

        tanmuCount = rootView.findViewById(R.id.tv_tanmu_count);
        tanmuCount.setText("弹幕列表("+ mList.size() + ")");

        rootView.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.addItemDecoration(new AlensDividerItemDecoration(getActivity(), R.color.white_66));
        mAdapter = new BarrageAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initDialogStyle(View view) {
        dialog = new Dialog(getActivity(), R.style.CustomGiftDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = App.H/2;
        window.setAttributes(lp);
    }

    class BarrageAdapter extends BaseQuickAdapter<Barrage, BaseViewHolder> {

        public BarrageAdapter(List<Barrage> list) {
            super(R.layout.layout_barrage_item, list);
        }

        @Override
        protected void convert(BaseViewHolder helper, Barrage bean) {
            GlideUtils.loadingImg(getContext(), (ImageView) helper.getView(R.id.iv_head), bean.getHeadUrl());
            helper.setText(R.id.tv_name, bean.getTitle());

            helper.setTextColor(R.id.tv_name,
                    helper.getLayoutPosition() % 2 == 0 ?
                            Color.parseColor("#00DBFF") : Color.parseColor("#FF59EB"));
            helper.setText(R.id.tv_value, bean.getValue());
            helper.setText(R.id.tv_nice_count, bean.getNiceCount() == 0 ? "" : String.valueOf(bean.getNiceCount()));
        }
    }

    {
        mList.add(new Barrage(headUrl, "豆腐~~~", "看广告转钱，5000 徒弟，领80000", 40));
        mList.add(new Barrage(headUrl, "豆腐1~~~", "看广告转钱，5000 徒弟，领80000", 41));
        mList.add(new Barrage(headUrl, "豆腐2~~~", "看广告转钱，5000 徒弟，领80000", 4222));
        mList.add(new Barrage(headUrl, "豆腐3~~~", "看广告转钱，5000 徒弟，领80000", 40000));
        mList.add(new Barrage(headUrl, "豆腐4~~~", "看广告转钱，5000 徒弟，领80000", 499));
        mList.add(new Barrage(headUrl, "豆腐5~~~", "看广告转钱，5000 徒弟，领80000", 0));
        mList.add(new Barrage(headUrl, "豆腐6~~~", "看广告转钱，5000 徒弟，领80000", 0));
        mList.add(new Barrage(headUrl, "豆腐7~~~", "看广告转钱，5000 徒弟，领80000", 4));
        mList.add(new Barrage(headUrl, "豆腐8~~~", "看广告转钱，5000 徒弟，领80000", 10));
        mList.add(new Barrage(headUrl, "豆腐9~~~", "看广告转钱，5000 徒弟，领80000", 420));
        mList.add(new Barrage(headUrl, "豆腐10~~~", "看广告转钱，5000 徒弟，领80000", 240));
        mList.add(new Barrage(headUrl, "豆腐11~~~", "看广告转钱，5000 徒弟，领80000", 340));
        mList.add(new Barrage(headUrl, "豆腐12~~~", "看广告转钱，5000 徒弟，领80000", 440));
        mList.add(new Barrage(headUrl, "豆腐13~~~", "看广告转钱，5000 徒弟，领80000", 540));
        mList.add(new Barrage(headUrl, "豆腐14~~~", "看广告转钱，5000 徒弟，领80000", 640));
        mList.add(new Barrage(headUrl, "豆腐15~~~", "看广告转钱，5000 徒弟，领80000", 640));
    }
}
