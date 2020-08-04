package com.pactera.znzmo.util;

import java.util.HashMap;
import java.util.Map;

public enum ServiceStatusEnum
{
  OK("ok", "操作成功"), 
  SERVICE_INTERNAL_ERROR("service internal error", "服务器内部错误"), 
  NEW_PASSWORD_DIFFERENCE("password difference", "两次输入新密码不一致"), 
  PARAM_CAN_NOT_EMPTY("Parameter cannot be empty", "参数不能为空"), 
  RECORD_NOT_FOUND("record not found", "记录未找到"), 
  FILE_NOT_UPLOAD("file not upload", "文件未上传"), 
  RECORD_ALREADY_EXISTED("The record has already existed", "记录已存在"), 
  PARAM_ERROR("parameter error", "参数错误"), 
  FAIL("fail", "错误");

  private String status;
  private String msg;

  private ServiceStatusEnum(String status, String msg)
  {
    this.status = status;
    this.msg = msg;
  }

  public String getStatus() {
    return this.status;
  }

  public String getMsg() {
    return this.msg;
  }

  public static Map<String, Object> getServiceStatusMap(ServiceStatusEnum serviceStatusEnum) {
    Map map = new HashMap();
    map.put("status", serviceStatusEnum.getStatus());
    map.put("msg", serviceStatusEnum.getMsg());
    return map;
  }
}