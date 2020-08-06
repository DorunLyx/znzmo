package com.pactera.znzmo.common;

import java.io.File;
import java.util.Map;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.pactera.znzmo.aliyun.oss.AliyunOssClient;
import com.pactera.znzmo.aliyun.oss.KeyAndUrl;
import com.pactera.znzmo.util.Constants;
import com.pactera.znzmo.util.FileUtils;
import com.pactera.znzmo.util.MediaTypes;
import com.pactera.znzmo.web.BaseController;
import com.pactera.znzmo.web.JsonResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName：UploadController
 * @Description：上传文件API 
 * @author liyongxu 
 * @date 2020年8月5日 上午10:12:30 
 * @version 1.0.0 
 */
@Api(tags = "上传文件API", value = "上传文件API")
@Controller
@RequestMapping(value = "/upload")
public class UploadController extends BaseController{

	@Autowired(required = false)
	private AliyunOssClient aliyunOssClient;
	
	/**
	 * @Title: uploadRequestfile 
	 * @Description: 上传文件
	 * @param file
	 * @param request
	 * @param response
	 * @throws Exception void
	 * @author liyongxu
	 * @date 2020年8月5日 上午10:12:47 
	*/
	@ApiOperation(value = "公共上传文件", httpMethod = "POST", notes = "公共上传文件")
	@RequestMapping(value = "/file", method = RequestMethod.POST)
	public void uploadRequestfile(@RequestParam MultipartFile file,HttpServletRequest request, HttpServletResponse response) throws Exception {
	   response.setContentType(MediaTypes.TEXT_HTML_UTF_8);
	   
	   Map<String, Object> resultData = FileUtils.uploadFile(request, file,Constants.UPLOAD_ROOT);
	   String jsonStr = JSONObject.toJSONString(resultData);
	   response.getWriter().print(jsonStr);
   }
   
	/**
	 * @Title: putUpload 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param file
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月6日 下午3:53:45 
	*/
	@ApiOperation(value = "上传方法", httpMethod = "POST", notes = "上传方法")
    @RequestMapping(value = "/putUpload", method = {RequestMethod.POST})
    public JsonResp putUpload(@ApiParam(name="file", value="上传参数")@RequestParam MultipartFile file) {
        Supplier<KeyAndUrl> businessHandler = () ->{
        	try {
        		String source = Constants.UPLOAD_ROOT_ASSET;
        		File photeFile = FileUtils.getFile(file);
        		if(!FileUtils.isFileExceedSize(photeFile, 10)){
        			KeyAndUrl keyAndUrl = aliyunOssClient.upload(photeFile, source,photeFile.getName());
        			return keyAndUrl;
        		}else{
            		JsonResp jsonResp = new JsonResp();
            		jsonResp.setError("超过10M限制，不允许上传~");
        		}
        	} catch (Exception e) {
        		throwException(e);
        	}
        	return null;
        };
        return handleRequest(businessHandler);
    }
	
}
