package com.pactera.znzmo.common;

import java.io.File;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.enums.JsonResultEnum;
import com.pactera.znzmo.util.FileUtils;
import com.pactera.znzmo.util.JsonResult;
import com.pactera.znzmo.util.StringUtils;
import com.pactera.znzmo.util.UIHelper;
import com.pactera.znzmo.vo.DownloadExcelParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName：DownloadController
 * @Description：下载文件API
 * @author liyongxu 
 * @date 2019年12月21日 下午2:23:50 
 * @version 1.0.0 
 */
@Api(tags = "下载文件API", value = "下载文件API")
@RestController
@RequestMapping(value = "/api/download")
public class DownloadController {
	
	private static final Logger logger = LoggerFactory.getLogger(DownloadController.class);
	
	@Autowired
	private TbAttachmentService tbAttachmentService;
	
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
        String filePath = "tools/" + fileName;
        if (filePath.lastIndexOf("/") == -1) {
            return;
        }
        String webRoot = UIHelper.getWebRealPath(request);//FileUtils.getRealPath(request);
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
    public JsonResult checkDowloadFile(HttpServletRequest request, HttpServletResponse response,String filePath) throws Exception {
    	filePath = URLDecoder.decode(filePath,"UTF-8");
        if (filePath == null || filePath.isEmpty()) {
            return JsonResult.fail("1001", "文件不存在!");
        }
        if (filePath.lastIndexOf("/") == -1) {
            return JsonResult.fail("1002", "文件路径格式错误!");
        }
        String realPath = FileUtils.getRealPath(request);
        String fileDirPath = realPath +"/"+ filePath;
        File f = new File(fileDirPath);
        if (!f.exists()) {
            return JsonResult.fail("1003", "文件已被删除!");
        }
        
        return JsonResult.ok();
    }
    
    /**
     * @Title: saveAsset 
     * @Description: 公共下载文件(aliyun)
     * @param user
     * @param downloadExcelParam
     * @return JsonResult<String>
     * @author liyongxu
     * @date 2019年12月26日 上午12:47:18 
    */
	@ApiOperation(value = "公共下载文件(aliyun)", httpMethod = "POST", notes = "公共下载文件(aliyun)")
    @RequestMapping(value = "/downloadExcel", method = {RequestMethod.POST})
    public JsonResult downloadExcel(
    		@ApiParam(name="downloadExcelParam", value="下载参数", required=false)@RequestBody DownloadExcelParam downloadExcelParam) {
		try {
			if("1".equals(downloadExcelParam.getType())) {
		    	return JsonResult.ok(getUrl(5));
			}else if ("2".equals(downloadExcelParam.getType())) {
				return JsonResult.ok(getUrl(6));
			}else if ("3".equals(downloadExcelParam.getType())) {
				return JsonResult.ok(getUrl(7));
			}else {
				return JsonResult.ok(getUrl(8));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JsonResult.fail(String.valueOf(JsonResultEnum.fail.getKey()), JsonResultEnum.fail.getValue());
		}
    }
    
    /**
     * @Title: getUrl 
     * @Description: 获取Url
     * @param type
     * @return String
     * @author liyongxu
     * @date 2019年12月26日 上午12:47:25 
    */
    private String getUrl(Integer type) {
    	QueryWrapper<TbAttachment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(TbAttachment.IS_VALID, IsValidEnum.YES.getKey());
        TbAttachment tbAttachment = tbAttachmentService.getOne(queryWrapper);
       return tbAttachment.getAttachmentPath();
	}
    
}
