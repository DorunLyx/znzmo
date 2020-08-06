package com.pactera.znzmo.aliyun.oss;

public class KeyAndUrl
{
  private String key;
  private String url;

  public String getKey()
  {
    return this.key;
  }
  public String getUrl() { return this.url; }


  public void setKey(String key)
  {
    this.key = key; } 
  public void setUrl(String url) { this.url = url; } 
  public boolean equals(Object o) { if (o == this) return true; if (!(o instanceof KeyAndUrl)) return false; KeyAndUrl other = (KeyAndUrl)o; if (!other.canEqual(this)) return false; Object this$key = getKey(); Object other$key = other.getKey(); if (this$key == null ? other$key != null : !this$key.equals(other$key)) return false; Object this$url = getUrl(); Object other$url = other.getUrl(); return this$url == null ? other$url == null : this$url.equals(other$url); } 
  protected boolean canEqual(Object other) { return other instanceof KeyAndUrl; } 
  public int hashCode() { int PRIME = 59; int result = 1; Object $key = getKey(); result = result * 59 + ($key == null ? 43 : $key.hashCode()); Object $url = getUrl(); result = result * 59 + ($url == null ? 43 : $url.hashCode()); return result; } 
  public String toString() { return "KeyAndUrl(key=" + getKey() + ", url=" + getUrl() + ")"; } 
  public KeyAndUrl(String key, String url) { this.key = key; this.url = url;
  }
}