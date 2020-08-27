package com.pactera.znzmo.enums;

/**
 * 状态枚举
 */
public enum ApproveStatusEnum {
    WAIT(0,"待审核"),
    NOT_PASS(1,"未通过"),
    PASS(2,"已通过"),
    SOLD_OUT(3,"已下架");
	
    private Integer key;

    private String value;

    ApproveStatusEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
