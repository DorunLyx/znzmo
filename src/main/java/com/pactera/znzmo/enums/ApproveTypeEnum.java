package com.pactera.znzmo.enums;

/**
 * 审批类型枚举
 */
public enum ApproveTypeEnum {
    INSERT("0","新增"),
	UPDATE("1","编辑"),
    IMPORT("2","导入"),
    BATCH_CHANGE("3","批量变更");


    private String key;

    private String value;

    ApproveTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
