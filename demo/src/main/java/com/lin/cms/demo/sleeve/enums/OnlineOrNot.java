package com.lin.cms.demo.sleeve.enums;

public enum OnlineOrNot {
    ONLINE(1),
    NOT_ONLINE(0);

    private int value;

    OnlineOrNot(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
