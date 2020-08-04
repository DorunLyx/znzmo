package com.pactera.znzmo.enums;

/**
 * 用户类型枚举
 */
public enum UserRoleIdEnum {
	/**
	 *1 超级管理员
	 */
    GROUP_ADMIN(1,"超级管理员"),
    /**
	 *2 区域管理员
	 */
    CITY_ADMIN(2,"区域管理员"),
    
    /**
   	 *100 区域管理员
   	 */
    CM(100,"店长"),
    
    /**
   	 *101 管家
   	 */
    CO(101,"管家"),
    ;
    private int key;

    private String value;

    UserRoleIdEnum(int key, String value) {
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
