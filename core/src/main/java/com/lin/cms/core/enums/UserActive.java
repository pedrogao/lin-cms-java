package com.lin.cms.core.enums;

public enum UserActive {

    ACTIVE(1),
    NOT_ACTIVE(2);

    private int value;


    UserActive(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
