package com.pactera.znzmo.common;

import java.io.File;
import java.net.URLDecoder;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pactera.znzmo.util.FileUtils;
import com.pactera.znzmo.util.StringUtils;
import com.pactera.znzmo.util.UIHelper;
import com.pactera.znzmo.web.BaseController;
import com.pactera.znzmo.web.JsonResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName：DownloadController
 * @Description：下载文件API
 * @author liyongxu 
 * @date 2019年12月21日 下午2:23:50 
 * @version 1.0.0 
 */
@Api(tags = "下载文件API", value = "下载文件API")
@RestController
@RequestMapping(value = "/download")
public class DownloadController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(DownloadController.class);
	
	/**
	 * @Title: downloadExcel 
	 * @Description: 公共下载文件
	 * @param request
	 * @param response
	 * @param filePath
	 * @param fileName
	 * @throws Exception void
	 * @author liyongxu
	 * @date 2019年12月21日 下午2:19:16 
	*/
	@ApiOperation(value = "公共下载文件", httpMethod = "GET", notes = "公共下载文件")
	@RequestMapping(value = "/downloadExcel/{fileName}", method = RequestMethod.GET)
	public void downloadExcel(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String fileName) throws Exception {
		if (StringUtils.isEmpty(fileName)) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print("<script>alert('下载的文件名称不正确!');</script>");
            return;
        }
        response.setCharacterEncoding("utf-8");
        String filePath = "files/" + fileName;
        if (filePath.lastIndexOf("/") == -1) {
            return;
        }
        String webRoot = UIHelper.getWebRealPath(request);
//        //本地
//        String downloadPath = webRoot + filePath.replaceAll("/", "\\\\");
        //服务器
        String downloadPath = webRoot + filePath;
        logger.info("下载地址：" + downloadPath);
        FileUtils.downloadFile(downloadPath, fileName, request, response);
    }
    
    /**
     * @Title: checkDowloadFile 
     * @Description: 校验下载文件是否存在
     * @param request
     * @param response
     * @param filePath
     * @return
     * @throws Exception JsonResult
     * @author liyongxu
     * @date 2019年12月21日 下午2:28:05 
    */
	@ApiOperation(value = "校验下载文件是否存在", httpMethod = "POST", notes = "校验下载文件是否存在")
	@RequestMapping(value = "/checkDownfile", method = RequestMethod.POST)
    public JsonResp checkDowloadFile(HttpServletRequest request, HttpServletResponse response, String filePath) throws Exception {
    	Supplier<String> businessHandler = () ->{
    		try {
    			URLDecoder.decode(filePath,"UTF-8");
        		if (filePath == null || filePath.isEmpty()) {
        			return ("文件不存在!");
        		}
        		if (filePath.lastIndexOf("/") == -1) {
        			return ("文件路径格式错误!");
        		}
        		String realPath = FileUtils.getRealPath(request);
        		String fileDirPath = realPath +"/"+ filePath;
        		File f = new File(fileDirPath);
        		if (!f.exists()) {
        			return ("文件已被删除!");
        		}
			} catch (Exception e) {
				throwException(e);
			}
    		return null;
    	};
        return handleRequest(businessHandler);
    }
}
