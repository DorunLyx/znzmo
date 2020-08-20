package com.pactera.znzmo.enums;

/**
 * jsonresult返回数据
 */
public enum JsonResultEnum {
	ok(1,"ok"),
	fail(2,"系统异常，请联系管理员！"),
	empty(3,"数据查询为空！"),
    ;

    private int key;

    private String value;

    JsonResultEnum(int key, String value) {
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
