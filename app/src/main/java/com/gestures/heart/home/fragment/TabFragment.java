package com.gestures.heart.home.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gestures.heart.R;
import com.gestures.heart.home.adapter.WorkAdapter;
import com.gestures.heart.home.bean.MeWorkBean;

import java.util.ArrayList;
import java.util.List;

public class TabFragment extends Fragment {
    public static final String TITLE = "title";
    private String mTitle = "Defaut Value";
    private RecyclerView mRecyclerView;
    private List<String> mDatas = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            mTitle = getArguments().getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_me_list, container, false);
        mRecyclerView = view.findViewById(R.id.id_stickynavlayout_innerscrollview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        List<MeWorkBean> workData = new ArrayList<>(10);
        for (int i = 0; i < 20; i++) {
            String url;
            if(i % 2 == 0)
                url = "http://7xi8d6.com1.z0.glb.clouddn.com/20171123083218_5mhRLg_sakura.gun_23_11_2017_8_32_9_312.jpeg";
            else
                url = "http://7xi8d6.com1.z0.glb.clouddn.com/20171114101305_NIAzCK_rakukoo_14_11_2017_10_12_58_703.jpeg";
            workData.add(new MeWorkBean(url , String.valueOf(i), "真心话")) ;
        }
        WorkAdapter adapter = new WorkAdapter(workData);
        mRecyclerView.setAdapter(adapter);

        return view;
    }

    public static TabFragment newInstance(String title){
        TabFragment tabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

}
