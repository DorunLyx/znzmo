package com.pactera.znzmo.enums;

/**
 * 业务类型枚举
 */
public enum ReTypeEnum {
	MODEL(0,"3dModel"),
	SUMODEL(1,"suModel"),
	DRAWING(2,"drawing"),
    HD(3,"hd"),
	DATABASE(4,"dataBase"),
	BANNER(5,"banner");


    private Integer key;

    private String value;

    ReTypeEnum(Integer key, String value) {
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
