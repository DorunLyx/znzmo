package com.pactera.znzmo.common;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pactera.znzmo.database.TbDatabase;
import com.pactera.znzmo.database.TbDatabaseService;
import com.pactera.znzmo.drawing.TbDrawingScheme;
import com.pactera.znzmo.drawing.TbDrawingSchemeService;
import com.pactera.znzmo.enums.JsonResultEnum;
import com.pactera.znzmo.enums.ReTypeEnum;
import com.pactera.znzmo.hd.TbHdMapping;
import com.pactera.znzmo.hd.TbHdMappingService;
import com.pactera.znzmo.model.TbThreedModel;
import com.pactera.znzmo.model.TbThreedModelService;
import com.pactera.znzmo.sumodel.TbSuModel;
import com.pactera.znzmo.sumodel.TbSuModelService;
import com.pactera.znzmo.vo.common.DownloadParam;
import com.pactera.znzmo.vo.common.VisitsParam;
import com.pactera.znzmo.web.BaseController;
import com.pactera.znzmo.web.JsonResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName：CommonController
 * @Description：公共方法API
 * @author liyongxu 
 * @date 2020年8月31日 上午10:59:45 
 * @version 1.0.0 
 */
@Api(tags = "公共方法API", value = "公共方法API")
@RestController
@RequestMapping(value = "/common")
public class CommonController extends BaseController{
	
	@Autowired
	private TbThreedModelService tbThreedModelService;
	
	@Autowired
	private TbSuModelService tbSuModelService;
	
	@Autowired
	private TbHdMappingService tbHdMappingService;

	@Autowired
	private TbDrawingSchemeService tbDrawingSchemeService;
	
	@Autowired
	private TbDatabaseService tbDatabaseService;
	
	@Autowired
	private TbAttachmentService tbAttachmentService;
	
	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	/**
	 * @Title: visitsNum 
	 * @Description: 浏览次数
	 * @param visitsParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月31日 上午11:29:14 
	*/
	@ApiOperation(value = "浏览次数", httpMethod = "POST", notes = "浏览次数")
    @RequestMapping(value = "/visitsNum", method = {RequestMethod.POST})
    public JsonResp visitsNum(
    		@ApiParam(name="visitsParam", value="浏览次数参数", required=false)@RequestBody VisitsParam visitsParam) {
		Supplier<String> businessHandler = () ->{
			try {
				if(ReTypeEnum.MODEL.getKey().equals(visitsParam.getType())) {
					TbThreedModel tbThreedModel = tbThreedModelService.getById(visitsParam.getReId());
					if(tbThreedModel != null) {
						tbThreedModel.setVisitsNum(tbThreedModel.getVisitsNum() + 1);
						tbThreedModelService.updateById(tbThreedModel);
					}
				}else if (ReTypeEnum.SUMODEL.getKey().equals(visitsParam.getType())) {
					TbSuModel tbSuModel = tbSuModelService.getById(visitsParam.getReId());
					if(tbSuModel != null) {
						tbSuModel.setVisitsNum(tbSuModel.getVisitsNum() + 1);
						tbSuModelService.updateById(tbSuModel);
					}
				}else if (ReTypeEnum.DRAWING.getKey().equals(visitsParam.getType())) {
					TbDrawingScheme tbDrawing = tbDrawingSchemeService.getById(visitsParam.getReId());
					if(tbDrawing != null) {
						tbDrawing.setVisitsNum(tbDrawing.getVisitsNum() + 1);
						tbDrawingSchemeService.updateById(tbDrawing);
					}
				}else if (ReTypeEnum.HD.getKey().equals(visitsParam.getType())) {
					TbHdMapping tbHdMapping = tbHdMappingService.getById(visitsParam.getReId());
					if(tbHdMapping != null) {
						tbHdMapping.setVisitsNum(tbHdMapping.getVisitsNum() + 1);
						tbHdMappingService.updateById(tbHdMapping);
					}
				}else if (ReTypeEnum.DATABASE.getKey().equals(visitsParam.getType())) {
					TbDatabase tbDatabase = tbDatabaseService.getById(visitsParam.getReId());
					if(tbDatabase != null) {
						tbDatabase.setVisitsNum(tbDatabase.getVisitsNum() + 1);
						tbDatabaseService.updateById(tbDatabase);
					}
				}
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
				logger.error(e.getMessage(),e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: downloadNum 
	 * @Description: 获取下载信息
	 * @param downloadParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月31日 上午11:35:52 
	*/
	@ApiOperation(value = "获取下载信息", httpMethod = "POST", notes = "获取下载信息")
    @RequestMapping(value = "/downloadNum", method = {RequestMethod.POST})
    public JsonResp downloadNum(
    		@ApiParam(name="downloadParam", value="下载次数参数", required=false)@RequestBody DownloadParam downloadParam) {
		Supplier<String> businessHandler = () ->{
			try {
				String attachmentName = "";
				if(ReTypeEnum.MODEL.getKey().equals(downloadParam.getType())) {
					TbThreedModel tbThreedModel = tbThreedModelService.getById(downloadParam.getReId());
					if(tbThreedModel != null) {
						tbThreedModel.setDownloadNum(tbThreedModel.getDownloadNum() + 1);
						tbThreedModelService.updateById(tbThreedModel);
						QueryWrapper<TbAttachment> queryWrapper = new QueryWrapper<>();
						queryWrapper.eq(TbAttachment.RE_TYPE, downloadParam.getType());
						TbAttachment tbAttachment = tbAttachmentService.getOne(queryWrapper);
						if(tbAttachment != null) {
							attachmentName = tbAttachment.getAttachmentName();
						}
					}
				}else if (ReTypeEnum.SUMODEL.getKey().equals(downloadParam.getType())) {
					TbSuModel tbSuModel = tbSuModelService.getById(downloadParam.getReId());
					if(tbSuModel != null) {
						tbSuModel.setDownloadNum(tbSuModel.getDownloadNum() + 1);
						tbSuModelService.updateById(tbSuModel);
						QueryWrapper<TbAttachment> queryWrapper = new QueryWrapper<>();
						queryWrapper.eq(TbAttachment.RE_TYPE, downloadParam.getType());
						TbAttachment tbAttachment = tbAttachmentService.getOne(queryWrapper);
						if(tbAttachment != null) {
							attachmentName = tbAttachment.getAttachmentName();
						}
					}
				}else if (ReTypeEnum.DRAWING.getKey().equals(downloadParam.getType())) {
					TbDrawingScheme tbDrawing = tbDrawingSchemeService.getById(downloadParam.getReId());
					if(tbDrawing != null) {
						tbDrawing.setDownloadNum(tbDrawing.getDownloadNum() + 1);
						tbDrawingSchemeService.updateById(tbDrawing);
						QueryWrapper<TbAttachment> queryWrapper = new QueryWrapper<>();
						queryWrapper.eq(TbAttachment.RE_TYPE, downloadParam.getType());
						TbAttachment tbAttachment = tbAttachmentService.getOne(queryWrapper);
						if(tbAttachment != null) {
							attachmentName = tbAttachment.getAttachmentName();
						}
					}
				}else if (ReTypeEnum.HD.getKey().equals(downloadParam.getType())) {
					TbHdMapping tbHdMapping = tbHdMappingService.getById(downloadParam.getReId());
					if(tbHdMapping != null) {
						tbHdMapping.setDownloadNum(tbHdMapping.getDownloadNum() + 1);
						tbHdMappingService.updateById(tbHdMapping);
						QueryWrapper<TbAttachment> queryWrapper = new QueryWrapper<>();
						queryWrapper.eq(TbAttachment.RE_TYPE, downloadParam.getType());
						TbAttachment tbAttachment = tbAttachmentService.getOne(queryWrapper);
						if(tbAttachment != null) {
							attachmentName = tbAttachment.getAttachmentName();
						}
					}
				}else if (ReTypeEnum.DATABASE.getKey().equals(downloadParam.getType())) {
					TbDatabase tbDatabase = tbDatabaseService.getById(downloadParam.getReId());
					if(tbDatabase != null) {
						tbDatabase.setDownloadNum(tbDatabase.getDownloadNum() + 1);
						tbDatabaseService.updateById(tbDatabase);
						QueryWrapper<TbAttachment> queryWrapper = new QueryWrapper<>();
						queryWrapper.eq(TbAttachment.RE_TYPE, downloadParam.getType());
						TbAttachment tbAttachment = tbAttachmentService.getOne(queryWrapper);
						if(tbAttachment != null) {
							attachmentName = tbAttachment.getAttachmentName();
						}
					}
				}
				return attachmentName;
			} catch (Exception e) {
				throwException(e);
				logger.error(e.getMessage(),e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
    }
}
