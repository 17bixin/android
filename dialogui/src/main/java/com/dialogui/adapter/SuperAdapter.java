package com.dialogui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

import com.dialogui.holder.SuperItemHolder;
import com.dialogui.listener.OnItemClickListener;

import java.util.List;
public abstract class SuperAdapter<T> extends Adapter<ViewHolder> {

    /**
     * 上下文
     */
    protected Context mContext;
    /**
     * 接收传递过来的数据
     */
    protected List<T> mDatas;
    /**
     * 获得holder
     */
    private SuperItemHolder baseHolder;
    protected OnItemClickListener mListener;

    public SuperAdapter(Context mContext, List<T> mDatas) {
        this.mContext = mContext;
        setmDatas(mDatas);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return getItemHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder != null) {
            baseHolder = (SuperItemHolder) holder;
            baseHolder.setPosition(position);
            baseHolder.setData(mDatas.get(position), countPosition(position));
        }
    }

    /**
     * 1top 2midle 3bottom 4all
     */
    protected int countPosition(int position) {
        return 2;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public List<T> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    /**
     * 获得Holder
     */
    public abstract SuperItemHolder getItemHolder(ViewGroup parent, int viewType);

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

}