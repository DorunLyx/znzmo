/**
 * 
 */
package com.pactera.znzmo.log;

import java.util.ArrayList;
import java.util.List;
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
import com.pactera.znzmo.vo.LogListVO;
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
		Supplier<IPage<LogListVO>> businessHandler = () ->{
			try {
				List<LogListVO> logList = new ArrayList<LogListVO>();
				Page<TbLog> page = new Page<TbLog>(logQueryParam.getPageNo(), logQueryParam.getPageSize());
				IPage<LogListVO> modeListPage =  new Page<LogListVO>(logQueryParam.getPageNo(), logQueryParam.getPageSize());
				IPage<TbLog> iPage = tbLogService.selectTbLogPages(page, logQueryParam);
				for (TbLog tblog : iPage.getRecords()) {
					LogListVO logListVO = new LogListVO();
					logListVO.setContent(tblog.getContent());
					logListVO.setEndTime(tblog.getUpdateTime());
					logListVO.setEvent(tblog.getEvent());
					logListVO.setIp(tblog.getIp());
					logListVO.setLogId(tblog.getId());
					logListVO.setOperatorId(tblog.getOperatorId());
					logListVO.setStartTime(tblog.getCreateTime());
					logList.add(logListVO);
	    		}
				modeListPage.setRecords(logList);
				modeListPage.setCurrent(iPage.getCurrent());
				modeListPage.setPages(iPage.getPages());
				modeListPage.setSize(iPage.getSize());
				modeListPage.setTotal(iPage.getTotal());			
				return modeListPage;
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
			return null;
		};
		return handleRequest(businessHandler);
	}
}
