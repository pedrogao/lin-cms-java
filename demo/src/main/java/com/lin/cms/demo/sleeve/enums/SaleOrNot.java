package com.lin.cms.demo.sleeve.enums;

public enum SaleOrNot {
    // 1 在售
    SALE(1),
    // 0 停售
    NOT_SALE(0);

    private int value;

    SaleOrNot(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
