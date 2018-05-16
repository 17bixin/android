package com.gestures.heart.home.bean;

import com.gestures.heart.ui.indexRV.bean.BaseIndexPinyinBean;

public class MsgBean extends BaseIndexPinyinBean {

    private String url;
    private String title;
    private String msg;
    private String date;
    private boolean isTop;//是否是最上面的 不需要被转化成拼音的

    public MsgBean(String url, String title, String msg, String date, boolean isTop) {
        this.url = url;
        this.title = title;
        this.msg = msg;
        this.date = date;
        this.isTop = isTop;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getMsg() {
        return msg;
    }

    public String getDate() {
        return date;
    }

    public boolean isTop() {
        return isTop;
    }

    @Override
    public String getTarget() {
        return title;
    }

    @Override
    public boolean isNeedToPinyin() {
        return !isTop;
    }


    @Override
    public boolean isShowSuspension() {
        return !isTop;
    }
}
