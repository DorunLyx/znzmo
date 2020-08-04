package com.pactera.znzmo.enums;

/**
 * 启用禁用枚举
 */
public enum StatusEnum {

    START_USE(1,"启用"),
    FORBIDDEN(0,"禁用")
    ;

    private int key;

    private String value;

    StatusEnum(int key, String value) {
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
