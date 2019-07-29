package com.lin.cms.demo.sleeve.enums;

public enum StandardOrNot {
    STANDARD(1),
    NOT_STANDARD(0);

    private int value;

    StandardOrNot(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
