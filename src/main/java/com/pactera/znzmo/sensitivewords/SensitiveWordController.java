/**
 * 
 */
package com.pactera.znzmo.sensitivewords;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pactera.znzmo.enums.JsonResultEnum;
import com.pactera.znzmo.vo.sensitivewords.SensiWordAddParam;
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

}
