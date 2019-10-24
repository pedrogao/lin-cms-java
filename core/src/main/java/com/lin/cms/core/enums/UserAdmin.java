package com.lin.cms.core.enums;

public enum UserAdmin {

    COMMON(1),
    ADMIN(2);

    private int value;


    UserAdmin(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
