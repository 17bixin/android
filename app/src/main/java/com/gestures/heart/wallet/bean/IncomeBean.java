package com.gestures.heart.wallet.bean;

public class IncomeBean {

    private String title;
    private String date;
    private String source;
    private String money;


    public IncomeBean(String title, String date, String source, String money) {
        this.title = title;
        this.date = date;
        this.source = source;
        this.money = money;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getSource() {
        return source;
    }

    public String getMoney() {
        return money;
    }
}
