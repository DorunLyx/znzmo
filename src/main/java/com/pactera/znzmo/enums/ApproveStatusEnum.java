package com.pactera.znzmo.enums;

/**
 * 状态枚举
 */
public enum ApproveStatusEnum {
    WAITAPPROVAL(0,"待审核"),
	APPROVALING(1,"审核中"),
    REJECTED(2,"已驳回"),
    APPROVAL_PASS(3,"审核通过"),
    TO_AUDIT(4,"待审核入库")
    ;
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
