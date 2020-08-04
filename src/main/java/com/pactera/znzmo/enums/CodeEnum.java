package com.pactera.znzmo.enums;

/**
 * 提示CODE枚举
 *  -100到-199定义输入异常
 *  -200到-299定义输出异常
 *  100-199用户自定义code与返回信息
 * @author panglx
 * @date 2019/2/20
 */
public enum CodeEnum {
    SUCCESS(0,"成功"),
    ERROR(1,"失败"),
    TOKEN_ERROR(-1,"Token认证失败"),
    REQUEST_PARAM_NOT_FULL_ERROR(-100,"请求参数不完整"),
    ILLEGAL_REQUEST_PARAM_ERROR(-101,"参数格式不正确"),
    ILLEGAL_REQUEST_METHOD_ERROR(-102,"请求方法不合法"),
    INTERNAL_ERROR(-200,"服务器内部错误"),
    NONE_PERMISSION_ERROR(-201,"无权限访问"),
    ;

    private int key;

    private String value;

    CodeEnum(int key, String value) {
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
