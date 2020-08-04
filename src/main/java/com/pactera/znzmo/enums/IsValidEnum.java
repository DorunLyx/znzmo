package com.pactera.znzmo.enums;

/**
 * 是否有效枚举
 */
public enum IsValidEnum {
    YES(1,"有效"),
    NO(0,"无效"),
    ;

    private int key;

    private String value;

    IsValidEnum(int key, String value) {
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
