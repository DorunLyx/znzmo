package com.pactera.znzmo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.enums.JsonResultEnum;
import com.pactera.znzmo.util.JsonResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName：AttachmentApi
 * @Description：附件API 
 * @author liyongxu 
 * @date 2020年8月5日 上午10:12:30 
 * @version 1.0.0 
 */
@Api(tags = "附件API", value = "附件API")
@Controller
@RequestMapping(value = "/api/attachment")
public class AttachmentApi {
	
	@Autowired
	private TbAttachmentService tbAttachmentService;
	
	public static final Logger logger = LoggerFactory.getLogger(AttachmentApi.class);
	
	/**
	 * @Title: getAttachmentInfo 
	 * @Description: 查询附件信息
	 * @param attachmentId
	 * @return JsonResult<TbAttachment>
	 * @author liyongxu
	 * @date 2020年8月6日 上午10:27:38 
	*/
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "查询附件信息", httpMethod = "POST", notes = "查询附件信息")
	@RequestMapping(value = "/getAttachmentInfo", method = RequestMethod.POST)
	public JsonResult<TbAttachment> getAttachmentInfo(
			@ApiParam(name = "attachmentId", value = "附件Id", required = true) @RequestParam(value = "attachmentId", defaultValue = "") String attachmentId) {
		try {
			QueryWrapper<TbAttachment> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq(TbAttachment.IS_VALID, IsValidEnum.YES.getKey())
	        	.eq(TbAttachment.ID, attachmentId);
			TbAttachment tbAttachment = tbAttachmentService.getOne(queryWrapper);
			return JsonResult.ok(tbAttachment);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JsonResult.fail(String.valueOf(JsonResultEnum.fail.getKey()), JsonResultEnum.fail.getValue());
		}
   }
   
}
