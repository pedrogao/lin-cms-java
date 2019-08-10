package com.lin.cms.demo.sleeve.enums;

public enum CouponType {
    FULL_MONEY_CUT(1), // 满减券
    DISCOUNT(2), // 折扣券
    ALL(3), // 无门槛券
    FULL_MONEY_DISCOUNT(4); // 满金额折扣券

    private int value;

    CouponType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
