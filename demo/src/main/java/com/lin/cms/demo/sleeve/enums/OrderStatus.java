package com.lin.cms.demo.sleeve.enums;

public enum OrderStatus {
    ALL(0), // 全部
    UNPAID(1), // 待支付
    PAID(2), // 已支付
    DELIVERED(3), // 已发货
    FINISHED(4), // 已完成
    CANCLED(5); // 已取消

    private int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
