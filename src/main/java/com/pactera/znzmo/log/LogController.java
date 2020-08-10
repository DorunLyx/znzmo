/**
 * 
 */
package com.pactera.znzmo.log;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pactera.znzmo.vo.LogQueryParam;
import com.pactera.znzmo.web.BaseController;
import com.pactera.znzmo.web.JsonResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Administrator
 *
 */
@Api(tags = "日志管理API", value = "日志管理API")
@RestController
@RequestMapping(value = "/log")
public class LogController extends BaseController {
	@Autowired
	TbLogService tbLogService;
	public static final Logger logger = LoggerFactory.getLogger(LogController.class);
	
	@ApiOperation(value = "日志管理查询", httpMethod = "POST", notes = "日志查询")
    @RequestMapping(value = "/queryLogPage", method = {RequestMethod.POST})
    public JsonResp queryLogPage(@ApiParam(name="logQueryParam", value="首页列表筛选参数", required=false)@RequestBody LogQueryParam logQueryParam) {
		Supplier<IPage<TbLog>> businessHandler = () ->{
			try {
				Page<TbLog> page = new Page<TbLog>(logQueryParam.getPageNo(), logQueryParam.getPageSize());
				tbLogService.selectTbLogPages(page, logQueryParam);
				return null;
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
			return null;
		};
		return handleRequest(businessHandler);
	}
}
