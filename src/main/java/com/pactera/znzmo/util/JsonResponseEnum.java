package com.pactera.znzmo.util;

public enum JsonResponseEnum {

	SUCCESS(0,"成功"),

    FAILED(1000,"系统异常"),

    WRONG(2000,"其他错误"),
    ;


	private int code;

    private String message;

    public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

    JsonResponseEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 通过code返回枚举
    */
    public static JsonResponseEnum parse(int code){
        JsonResponseEnum[] values = values();
        for (JsonResponseEnum value : values) {
            if(value.getCode() == code){
                return value;
            }
        }
        throw  new RuntimeException("Unknown code of ResultEnum");
    }
}
