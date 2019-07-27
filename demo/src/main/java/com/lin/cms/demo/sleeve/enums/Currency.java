package com.lin.cms.demo.sleeve.enums;

public enum Currency {
    // CNY
    // 0 停售
    Currency("CNY");

    private String value;

    Currency(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
