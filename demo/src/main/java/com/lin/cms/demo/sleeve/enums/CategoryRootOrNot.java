package com.lin.cms.demo.sleeve.enums;

public enum CategoryRootOrNot {
    ROOT(1),
    NOT_ROOT(0);

    private int value;

    CategoryRootOrNot(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
