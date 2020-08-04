package com.pactera.znzmo.enums;

/**
 * 是否有效枚举
 */
public enum LocationStatusEnum {
    NOTANOMALY(0,"不异常"),
    ANOMALY(1,"异常")
    ;

    private int key;

    private String value;

    LocationStatusEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
