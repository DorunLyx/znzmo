/**
 * 
 */
package com.pactera.znzmo.examineverify;

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
import com.pactera.znzmo.enums.JsonResultEnum;
import com.pactera.znzmo.vo.ExamineListVO;
import com.pactera.znzmo.vo.ExamineQueryParam;
import com.pactera.znzmo.vo.ExamineStatusParam;
import com.pactera.znzmo.web.BaseController;
import com.pactera.znzmo.web.JsonResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName：ExamineVerifyController
 * @Description：审核管理
 * @author liyongxu 
 * @date 2020年8月10日 上午10:58:55 
 * @version 1.0.0 
 */
@Api(tags = "审核管理API", value = "审核管理API")
@RestController
@RequestMapping(value = "/examine")
public class ExamineController extends BaseController{
	
	@Autowired
	private TbExamineVerifyService tbExamineVerifyService;
	
	public static final Logger logger = LoggerFactory.getLogger(ExamineController.class);

	/**
	 * @Title: getExamineList 
	 * @Description: 审核管理查询
	 * @param examineQueryParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月10日 上午11:43:29 
	*/
	@ApiOperation(value = "审核管理查询", httpMethod = "POST", notes = "审核管理查询")
    @RequestMapping(value = "/getExamineList", method = {RequestMethod.POST})
    public JsonResp getExamineList(
    		@ApiParam(name="examineQueryParam", value="审核列表筛选参数", required=false)@RequestBody ExamineQueryParam examineQueryParam) {
		Supplier<IPage<ExamineListVO>> businessHandler = () ->{
			try {
				Page<TbExamineVerify> page = new Page<TbExamineVerify>(examineQueryParam.getPageNo(), examineQueryParam.getPageSize());
				IPage<ExamineListVO> iPage = tbExamineVerifyService.selectExaminePages(page, examineQueryParam);
				return iPage;
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }

	/**
	 * @Title: updateExamineStatus 
	 * @Description: 变更状态-审核
	 * @param examineStatusParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月10日 上午11:43:20 
	*/
	@ApiOperation(value = "变更状态-审核", httpMethod = "POST", notes = "变更状态-审核")
    @RequestMapping(value = "/updateExamineStatus", method = {RequestMethod.POST})
    public JsonResp updateExamineStatus(
    		@ApiParam(name="examineStatusParam", value="审核参数", required=false)@RequestBody ExamineStatusParam examineStatusParam) {
		Supplier<String> businessHandler = () ->{
			try {
				tbExamineVerifyService.updateExamineStatus(examineStatusParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
	
}
