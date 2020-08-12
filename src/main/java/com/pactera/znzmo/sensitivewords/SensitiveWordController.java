/**
 * 
 */
package com.pactera.znzmo.sensitivewords;

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
import com.pactera.znzmo.enums.JsonResultEnum;
import com.pactera.znzmo.msg.TbSysMsg;
import com.pactera.znzmo.vo.MsgListVO;
import com.pactera.znzmo.vo.MsgQueryParam;
import com.pactera.znzmo.vo.sensitivewords.SensiWordAddParam;
import com.pactera.znzmo.vo.sensitivewords.SensiWordQueryParam;
import com.pactera.znzmo.vo.sensitivewords.seniWordListVO;
import com.pactera.znzmo.web.BaseController;
import com.pactera.znzmo.web.JsonResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author heliang
 *
 */
@Api(tags = "敏感词API", value = "敏感词API")
@RestController
@RequestMapping(value = "/SensitiveWord")
public class SensitiveWordController extends BaseController {
	public static final Logger logger = LoggerFactory.getLogger(SensitiveWordController.class);

	@Autowired
	TbSensitiveWordsService sensitiveWordService;
	
	@ApiOperation(value = "新增敏感词", httpMethod = "POST", notes = "敏感词新增")
	@RequestMapping(value = "/addSensitiveWord", method = { RequestMethod.POST })
	public JsonResp addSensitiveWord(
			@ApiParam(name = "SensiWordAddParam", value = "敏感词新增参数", required = false) @RequestBody SensiWordAddParam sensiWordAddParam) {
		Supplier<String> businessHandler = () -> {
			try {
				sensitiveWordService.addSensitiveWord(sensiWordAddParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
	
	@ApiOperation(value = "敏感词列表查询", httpMethod = "POST", notes = "敏感词列表查询")
	@RequestMapping(value = "/getSensiWordList", method = { RequestMethod.POST })
	public JsonResp getSensiWordList(
			@ApiParam(name = "sensiWordQueryParam", value = "系统消息列表筛选参数", required = false) @RequestBody SensiWordQueryParam sensiWordQueryParam) {
		Supplier<IPage<seniWordListVO>> businessHandler = () -> {
			try {
				List<seniWordListVO> modelList = new ArrayList<seniWordListVO>();
				Page<TbSensitiveWords> page = new Page<TbSensitiveWords>(sensiWordQueryParam.getPageNo(), sensiWordQueryParam.getPageSize());
				IPage<seniWordListVO> modeListPage = new Page<seniWordListVO>(sensiWordQueryParam.getPageNo(),
						sensiWordQueryParam.getPageSize());
				IPage<TbSensitiveWords> iPage = sensitiveWordService.selectSensiWordPages(page, sensiWordQueryParam);
				for (TbSensitiveWords sensiWord : iPage.getRecords()) {
					seniWordListVO msgListVO = new seniWordListVO();
					msgListVO.setName(sensiWord.getName());
					msgListVO.setShowContent(sensiWord.getShowContents());
					msgListVO.setCreateAccount(sensiWord.getCreateAccount());
					msgListVO.setId(sensiWord.getId());
					msgListVO.setCreateFTime(sensiWord.getCreateTime());
					modelList.add(msgListVO);
				}
				modeListPage.setRecords(modelList);
				modeListPage.setCurrent(iPage.getCurrent());
				modeListPage.setPages(iPage.getPages());
				modeListPage.setSize(iPage.getSize());
				modeListPage.setTotal(iPage.getTotal());
				return modeListPage;
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);

	}
}
