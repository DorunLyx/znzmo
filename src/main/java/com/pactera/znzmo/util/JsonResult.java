package com.pactera.znzmo.util;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class JsonResult<T>
{
  public static final String OK = "ok";
  private String status;
  private String msg;
  private T data;

  public static <T> JsonResult<T> ok(T data)
  {
    JsonResult result = new JsonResult();
    result.setStatus("ok");
    result.setMsg("ok");
    result.setData(data);
    return result;
  }

  public static <T> JsonResult<T> ok() {
    return ok(null);
  }

  public static JsonResult fail(String status, String msg) {
    status = status == null ? "fail" : status;
    if ("ok".equals(status)) {
      throw new RuntimeException("ok is not fail");
    }
    JsonResult result = new JsonResult();
    result.setStatus(status);
    result.setMsg(msg);
    return result;
  }

  public static JsonResult fail(ServiceStatusEnum serviceStatusEnum) {
    JsonResult result = new JsonResult();
    result.setStatus(serviceStatusEnum.getStatus());
    result.setMsg(serviceStatusEnum.getMsg());
    return result;
  }

  public static JsonResult badRequest(String msg) {
    return fail("bad_request", msg);
  }

  @JsonIgnore
  public boolean isOk() {
    return "ok".equals(this.status);
  }

  @JsonGetter
  public long getTimestamp() {
    return System.currentTimeMillis();
  }

  public String getStatus()
  {
    return this.status;
  }
  public String getMsg() { return this.msg; } 
  public T getData() {
    return this.data;
  }

  public void setStatus(String status)
  {
    this.status = status; } 
  public void setMsg(String msg) { this.msg = msg; } 
  public void setData(T data) { this.data = data; } 
  public boolean equals(Object o) { if (o == this) return true; if (!(o instanceof JsonResult)) return false; JsonResult other = (JsonResult)o; if (!other.canEqual(this)) return false; Object this$status = getStatus(); Object other$status = other.getStatus(); if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false; Object this$msg = getMsg(); Object other$msg = other.getMsg(); if (this$msg == null ? other$msg != null : !this$msg.equals(other$msg)) return false; Object this$data = getData(); Object other$data = other.getData(); return this$data == null ? other$data == null : this$data.equals(other$data); } 
  protected boolean canEqual(Object other) { return other instanceof JsonResult; } 
  public int hashCode() { int PRIME = 59; int result = 1; Object $status = getStatus(); result = result * 59 + ($status == null ? 43 : $status.hashCode()); Object $msg = getMsg(); result = result * 59 + ($msg == null ? 43 : $msg.hashCode()); Object $data = getData(); result = result * 59 + ($data == null ? 43 : $data.hashCode()); return result; } 
  public String toString() { return "JsonResult(status=" + getStatus() + ", msg=" + getMsg() + ", data=" + getData() + ")"; }

}