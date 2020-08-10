package com.pactera.znzmo.enums;

/**
 * 状态枚举
 */
public enum ApproveStatusEnum {
    WAITAPPROVAL(0,"待审核"),
    REJECTED(1,"已驳回"),
    APPROVAL_PASS(2,"审核通过");
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
