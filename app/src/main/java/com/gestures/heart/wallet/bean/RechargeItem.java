package com.gestures.heart.wallet.bean;


public class RechargeItem {

    private String virtual;
    private String money;
    private boolean isChecked = false;

    public RechargeItem(String virtual, String money, boolean isChecked) {
        this.virtual = virtual;
        this.money = money;
        this.isChecked = isChecked;
    }

    public String getVirtual() {
        return virtual;
    }

    public String getMoney() {
        return money;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setVirtual(String virtual) {
        this.virtual = virtual;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
