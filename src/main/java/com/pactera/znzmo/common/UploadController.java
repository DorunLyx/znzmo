package com.pactera.znzmo.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.pactera.znzmo.util.Constants;
import com.pactera.znzmo.util.FileUtils;
import com.pactera.znzmo.util.MediaTypes;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName：UploadController
 * @Description：上传文件API 
 * @author liyongxu 
 * @date 2020年8月5日 上午10:12:30 
 * @version 1.0.0 
 */
@Api(tags = "上传文件API", value = "上传文件API")
@Controller
@RequestMapping(value = "/api/upload")
public class UploadController {
	
	/**
	 * @Title: uploadrequestfile 
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
	public void uploadrequestfile(@RequestParam MultipartFile file,HttpServletRequest request, HttpServletResponse response) throws Exception {
	   response.setContentType(MediaTypes.TEXT_HTML_UTF_8);
	   
	   Map<String, Object> resultData = FileUtils.uploadFile(request, file,Constants.UPLOAD_ROOT);
	   String jsonStr = JSONObject.toJSONString(resultData);
	   response.getWriter().print(jsonStr);
   }
   
}
