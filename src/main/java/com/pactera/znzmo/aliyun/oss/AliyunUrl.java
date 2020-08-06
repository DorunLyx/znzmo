package com.pactera.znzmo.aliyun.oss;

import java.io.UnsupportedEncodingException;

public class AliyunUrl
{
  private StringBuilder imageUrl;
  private static String prefix = "?x-oss-process=image";

  private static String waterImageName = "waterfall.png";

  private static String text = "仅提供申请办理合同签订，他用无效";

  private static String watermarkTextType = "d3F5LXplbmhlaQ";

  private AliyunUrl()
  {
    this.imageUrl = new StringBuilder();
  }

  public StringBuilder getImageUrl()
  {
    return this.imageUrl;
  }

  public void setImageUrl(StringBuilder imageUrl) {
    this.imageUrl = imageUrl;
  }

  public static class Builder
  {
    private AliyunUrl aliyunUrl = new AliyunUrl();

    public Builder(String url) {
      this.aliyunUrl.getImageUrl().append(url).append(AliyunUrl.prefix);
    }

    public Builder resizeLfit(int height, int withd) {
      this.aliyunUrl.getImageUrl().append("/resize").append(",h_")
        .append(height)
        .append(",w_").append(withd);
      return this;
    }

    public Builder resizeFill(int height, int withtd) {
      this.aliyunUrl.getImageUrl().append("/resize").append(",h_").append(height).append(",w_").append(withtd);
      return this;
    }

    public Builder circle(int r)
    {
      this.aliyunUrl.getImageUrl().append("/circle").append(",r_").append(r);
      return this;
    }

    public Builder quality(int percent) {
      this.aliyunUrl.getImageUrl().append("/quality").append(",Q_").append(percent);
      return this;
    }

    public Builder waterMarkText(int size, String watermark, String color, int shadow, int rotate, int fill)
    {
      try
      {
        this.aliyunUrl.getImageUrl().append("/watermark").append(",type_")
          .append(AliyunUrl.watermarkTextType)
          .append(",size_")
          .append(size)
          .append(",text_")
          .append(java.util.Base64.getUrlEncoder().encodeToString(watermark.getBytes("UTF-8")))
          .append(",color_")
          .append(color)
          .append(",shadow_")
          .append(shadow)
          .append(",rotate_")
          .append(rotate)
          .append(",fill_")
          .append(fill);
      }
      catch (UnsupportedEncodingException e) {
        return this;
      }
      return this;
    }
    public static void main(String[] args) {
      Builder builder = new Builder("http://image-demo.img-cn-hangzhou.aliyuncs.com/example.jpg");
      builder.waterMarkText(30, "徐心1695", "FFFFFF", 5, 300, 1);
      String build = builder.build();
      System.out.println(build);
    }

    public Builder waterMark(int x, int y) {
      if (y > 4096) {
        y = 4096;
      }
      if (x < 0) {
        x = 10;
      }
      if (y < 0) {
        y = 10;
      }
      this.aliyunUrl.getImageUrl().append("/watermark").append(",type_")
        .append(org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(AliyunUrl.waterImageName.getBytes()))
        .append(",x_")
        .append(x).append(",y_").append(y);

      return this;
    }

    public Builder sign() {
      this.aliyunUrl.getImageUrl().append("/watermark").append(",text_")
        .append(org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(AliyunUrl.text.getBytes()))
        .append(",size_120,color_FF0000,g_center");

      return this;
    }

    public Builder formatJpg() {
      this.aliyunUrl.getImageUrl().append("/format,jpg");
      return this;
    }

    public Builder interlace() {
      this.aliyunUrl.getImageUrl().append("/interlace,1");
      return this;
    }

    public String build() {
      return this.aliyunUrl.imageUrl.toString();
    }
  }
}