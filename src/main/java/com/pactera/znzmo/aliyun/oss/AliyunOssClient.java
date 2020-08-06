package com.pactera.znzmo.aliyun.oss;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;

public class AliyunOssClient
{
  private static final Logger log = LoggerFactory.getLogger(AliyunOssClient.class);

  @Value("${oss.endpoint}")
  private String endpoint;

  @Value("${oss.accessKeyId}")
  private String accessKeyId;

  @Value("${oss.accessKeySecret}")
  private String accessKeySecret;

  @Value("${oss.bucket}")
  private String bucket;

  @Value("${oss.baseUrl}")
  private String baseUrl;

  @Value("${env}")
  private String env;
  private Random rand = new Random();

  private boolean isProd() {
    return this.env.equals("prod");
  }

  public KeyAndUrl upload(InputStream inputStream, String source, String fileName) throws Exception {
    OSSClient ossClient = new OSSClient(this.endpoint, this.accessKeyId, this.accessKeySecret);
    String path = generateFilePathName(source, fileName);
    PutObjectResult result = ossClient.putObject(this.bucket, path, inputStream);
    ossClient.shutdown();
    log.info("call ossClient.shutdown()");
    return new KeyAndUrl(result.getETag(), this.baseUrl + path);
  }

  public KeyAndUrl upload(byte[] data, String source, String fileName) throws Exception {
    return upload(new ByteArrayInputStream(data), source, fileName);
  }

  public KeyAndUrl upload(File file, String source, String fileName) throws Exception {
    return upload(new FileInputStream(file), source, fileName);
  }

  public void delete(String key) {
    OSSClient ossClient = new OSSClient(this.endpoint, this.accessKeyId, this.accessKeySecret);
    ossClient.deleteObject(this.bucket, key);
    ossClient.shutdown();
  }

  public byte[] getOSSObject(String key) throws IOException {
    OSSClient ossClient = new OSSClient(this.endpoint, this.accessKeyId, this.accessKeySecret);
    OSSObject ossObj = ossClient.getObject(this.bucket, key);
    byte[] content = input2byte(ossObj.getObjectContent());
    ossClient.shutdown();
    return content;
  }

  public final InputStream byte2Input(byte[] buf) {
    return new ByteArrayInputStream(buf);
  }

  public final byte[] input2byte(InputStream inStream) throws IOException
  {
    ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
    byte[] buff = new byte[100];
    int rc = 0;
    while ((rc = inStream.read(buff, 0, 100)) > 0) {
      swapStream.write(buff, 0, rc);
    }
    byte[] in2b = swapStream.toByteArray();
    return in2b;
  }

  private String generateFilePathName(String source, String fileName) {
    log.info("source = {}, fileName = {}", source, fileName);
    String suffix = parseSuffix(fileName);
    if (isProd()) {
      return "prod/" + source + "/" + randomName() + suffix;
    }
    return "test/" + source + "/" + randomName() + suffix;
  }

  private String randomName()
  {
    byte[] bytes = new byte[8];
    this.rand.nextBytes(bytes);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
  }

  private String parseSuffix(String fileName) {
    if (fileName == null) {
      return "";
    }
    int index = fileName.lastIndexOf('.');
    if (index >= 0) {
      return fileName.substring(index);
    }
    return "";
  }

  private void writeToLocal(String destination, InputStream input)
    throws IOException
  {
    byte[] bytes = new byte[1024];
    FileOutputStream downloadFile = new FileOutputStream(destination);
    int index;
    while ((index = input.read(bytes)) != -1) {
      downloadFile.write(bytes, 0, index);
      downloadFile.flush();
    }
    downloadFile.close();
    input.close();
  }
}