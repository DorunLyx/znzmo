package com.pactera.znzmo.drawing;

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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pactera.znzmo.common.TbAttachment;
import com.pactera.znzmo.common.TbAttachmentService;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.enums.JsonResultEnum;
import com.pactera.znzmo.enums.ReTypeEnum;
import com.pactera.znzmo.util.DataUtils;
import com.pactera.znzmo.util.DateUtils;
import com.pactera.znzmo.vo.common.UploadInfo;
import com.pactera.znzmo.vo.drawing.DrawingAddParam;
import com.pactera.znzmo.vo.drawing.DrawingDetailsVO;
import com.pactera.znzmo.vo.drawing.DrawingInfoVO;
import com.pactera.znzmo.vo.drawing.DrawingUpdateParam;
import com.pactera.znzmo.vo.homepage.HomePageSimplifyData;
import com.pactera.znzmo.vo.model.ModelListVO;
import com.pactera.znzmo.vo.model.ModelQueryDetailsParam;
import com.pactera.znzmo.vo.model.ModelQueryParam;
import com.pactera.znzmo.web.BaseController;
import com.pactera.znzmo.web.JsonResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName：DrawingController
 * @Description：图纸Api
 * @author liyongxu 
 * @date 2020年8月7日 上午11:35:16 
 * @version 1.0.0 
 */
@Api(tags = "图纸API", value = "图纸API")
@RestController
@RequestMapping(value = "/drawing")
public class DrawingController extends BaseController{
	
	@Autowired
	private TbDrawingSchemeService tbDrawingSchemeService;
	
	@Autowired
	private TbAttachmentService tbAttachmentService;
	
	public static final Logger logger = LoggerFactory.getLogger(DrawingController.class);

	/**
	 * @Title: getDrawingPage 
	 * @Description: 图纸前台页面查询
	 * @param modelQueryParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月24日 下午5:39:54 
	*/
	@ApiOperation(value = "图纸前台页面查询", httpMethod = "POST", notes = "图纸前台页面查询")
	@RequestMapping(value = "/getDrawingPage", method = {RequestMethod.POST})
	public JsonResp getDrawingPage(
			@ApiParam(name="modelQueryParam", value="图纸列表筛选参数", required=false)@RequestBody ModelQueryParam modelQueryParam) {
		Supplier<IPage<HomePageSimplifyData>> businessHandler = () ->{
			try {
				List<HomePageSimplifyData> homePageDrawingList = new ArrayList<HomePageSimplifyData>();
				Page<TbDrawingScheme> page = new Page<TbDrawingScheme>(modelQueryParam.getPageNo(), modelQueryParam.getPageSize());
				IPage<HomePageSimplifyData> modeListPage =  new Page<HomePageSimplifyData>(modelQueryParam.getPageNo(), modelQueryParam.getPageSize());
				IPage<TbDrawingScheme> iPage = tbDrawingSchemeService.selectDrawingPages(page, modelQueryParam);
				for (TbDrawingScheme tbDrawing : iPage.getRecords()) {
					HomePageSimplifyData homePageSimplifyData = new HomePageSimplifyData();
					homePageSimplifyData.setReId(tbDrawing.getId().toString());
					homePageSimplifyData.setReType(ReTypeEnum.MODEL.getKey());
					TbAttachment tbAttachment = tbAttachmentService.getById(tbDrawing.getMainGraph());
			        if(tbAttachment != null) {
			        	homePageSimplifyData.setMainGraph(tbAttachment.getAttachmentPath());
			        }
					homePageSimplifyData.setTitle(tbDrawing.getTitle());
					homePageSimplifyData.setPrice(tbDrawing.getPrice());
					homePageSimplifyData.setType(tbDrawing.getType());
					homePageDrawingList.add(homePageSimplifyData);
				}
				modeListPage.setRecords(homePageDrawingList);
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
	
	/**
	 * @Title: getDrawingDetails 
	 * @Description: 图纸方案下载页详情
	 * @param modelQueryDetailsParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月24日 下午5:43:35 
	*/
	@ApiOperation(value = "图纸方案下载页详情", httpMethod = "POST", notes = "图纸方案下载页详情")
    @RequestMapping(value = "/getDrawingDetails", method = {RequestMethod.POST})
    public JsonResp getDrawingDetails(
    		@ApiParam(name="modelQueryDetailsParam", value="图纸方案详情参数", required=false)@RequestBody ModelQueryDetailsParam modelQueryDetailsParam) {
		Supplier<DrawingDetailsVO> businessHandler = () ->{
			try {
				QueryWrapper<TbDrawingScheme> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq(TbDrawingScheme.IS_VALID, IsValidEnum.YES.getKey())
		        	.eq(TbDrawingScheme.ID, modelQueryDetailsParam.getModelId());
				TbDrawingScheme tbDrawing = tbDrawingSchemeService.getOne(queryWrapper);
				if(tbDrawing != null) {
					DrawingDetailsVO drawingDetailsVO = new DrawingDetailsVO();
					drawingDetailsVO.setDrawingId(tbDrawing.getId().toString());
					List<UploadInfo> uploadInfos = new ArrayList<>();
					QueryWrapper<TbAttachment> attachmentQueryWrapper = new QueryWrapper<>();
					attachmentQueryWrapper.eq(TbAttachment.IS_VALID, IsValidEnum.YES.getKey())
					.eq(TbAttachment.RELATION_ID, modelQueryDetailsParam.getModelId());
					List<TbAttachment> attachmentList = tbAttachmentService.list(attachmentQueryWrapper);
					if(DataUtils.isNotEmpty(attachmentList)) {
						for (TbAttachment tbAttachment : attachmentList) {
							UploadInfo uploadInfo = new UploadInfo();
							uploadInfo.setUploadVersion(tbAttachment.getUploadVersion());
							uploadInfo.setImgType(tbAttachment.getImgType());
							uploadInfo.setFileName(tbAttachment.getAttachmentName());
							uploadInfo.setFile(tbAttachment.getAttachmentPath());
							uploadInfo.setRealName(tbAttachment.getAliasName());
							uploadInfo.setUrl(tbAttachment.getAttachmentPath());
							uploadInfos.add(uploadInfo);
							if(tbAttachment.getId().equals(Long.valueOf(tbDrawing.getMainGraph()))) {
								drawingDetailsVO.setFileSize(tbAttachment.getAttachmentSize());
								drawingDetailsVO.setFileFormat(tbAttachment.getSuffix());
							}
						}
					}
					drawingDetailsVO.setUploadImg(uploadInfos);
					drawingDetailsVO.setStyleName(tbDrawing.getStyleName());
					drawingDetailsVO.setTitle(tbDrawing.getTitle());
					drawingDetailsVO.setVisitsNum(tbDrawing.getVisitsNum());
					drawingDetailsVO.setDownloadNum(tbDrawing.getDownloadNum());
					drawingDetailsVO.setCollectionNum(tbDrawing.getDownloadNum());
					drawingDetailsVO.setUpdatetTime(DateUtils.localDateTimeToString(tbDrawing.getUpdateTime(), DateUtils.DATE_FORMAT));
					drawingDetailsVO.setTextureMapping(tbDrawing.getTextureMapping());
					drawingDetailsVO.setVersion(tbDrawing.getVersion());
					drawingDetailsVO.setPrice(tbDrawing.getPrice());
					return drawingDetailsVO;
				}
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: getDrawingList 
	 * @Description: 图纸列表查询
	 * @param modelQueryParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月7日 上午11:36:20 
	*/
	@ApiOperation(value = "图纸列表查询", httpMethod = "POST", notes = "图纸模型列表查询")
    @RequestMapping(value = "/getDrawingList", method = {RequestMethod.POST})
    public JsonResp getDrawingList(
    		@ApiParam(name="modelQueryParam", value="图纸列表筛选参数", required=false)@RequestBody ModelQueryParam modelQueryParam) {
		Supplier<IPage<ModelListVO>> businessHandler = () ->{
			try {
				List<ModelListVO> modelList = new ArrayList<ModelListVO>();
				Page<TbDrawingScheme> page = new Page<TbDrawingScheme>(modelQueryParam.getPageNo(), modelQueryParam.getPageSize());
				IPage<ModelListVO> modeListPage =  new Page<ModelListVO>(modelQueryParam.getPageNo(), modelQueryParam.getPageSize());
				IPage<TbDrawingScheme> iPage = tbDrawingSchemeService.selectDrawingPages(page, modelQueryParam);
				for (TbDrawingScheme tbDrawing : iPage.getRecords()) {
					ModelListVO modelListVO = new ModelListVO();
					modelListVO.setModelId(tbDrawing.getId().toString());
					TbAttachment tbAttachment = tbAttachmentService.getById(tbDrawing.getMainGraph());
			        if(tbAttachment != null) {
			        	modelListVO.setMainGraph(tbAttachment.getAttachmentPath());
			        }
					modelListVO.setCode(tbDrawing.getCode());
					modelListVO.setPrimaryClassName(tbDrawing.getPrimaryClassName());
					modelListVO.setSecondaryClassName(tbDrawing.getSecondaryClassName());
					modelListVO.setStyleName(tbDrawing.getStyleName());
					modelListVO.setTitle(tbDrawing.getTitle());
					modelListVO.setType(tbDrawing.getType());
					modelListVO.setPrice(tbDrawing.getPrice());
					modelListVO.setTextureMapping(tbDrawing.getTextureMapping());
					modelListVO.setStatus(tbDrawing.getStatus());
					modelListVO.setVisitsNum(tbDrawing.getVisitsNum());
					modelListVO.setDownloadNum(tbDrawing.getDownloadNum());
					modelList.add(modelListVO);
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

	/**
	 * @Title: addDrawing 
	 * @Description: 图纸方案新增
	 * @param drawingAddParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月7日 上午11:54:16 
	*/
	@ApiOperation(value = "图纸方案新增", httpMethod = "POST", notes = "图纸方案新增")
    @RequestMapping(value = "/addDrawing", method = {RequestMethod.POST})
	public JsonResp addDrawing(
			@ApiParam(name="drawingAddParam", value="图纸新增参数", required=false)@RequestBody DrawingAddParam drawingAddParam) {
		Supplier<String> businessHandler = () ->{
			try {
				tbDrawingSchemeService.addDrawing(drawingAddParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
	
	/**
	 * @Title: getDrawingInfo 
	 * @Description: 图纸方案详情
	 * @param modelQueryDetailsParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月7日 下午2:53:28 
	*/
	@ApiOperation(value = "图纸方案详情", httpMethod = "POST", notes = "图纸方案详情")
    @RequestMapping(value = "/getDrawingInfo", method = {RequestMethod.POST})
    public JsonResp getDrawingInfo(
    		@ApiParam(name="modelQueryDetailsParam", value="图纸方案详情参数", required=false)@RequestBody ModelQueryDetailsParam modelQueryDetailsParam) {
		Supplier<DrawingInfoVO> businessHandler = () ->{
			try {
				QueryWrapper<TbDrawingScheme> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq(TbDrawingScheme.IS_VALID, IsValidEnum.YES.getKey())
		        	.eq(TbDrawingScheme.ID, modelQueryDetailsParam.getModelId());
				TbDrawingScheme tbDrawing = tbDrawingSchemeService.getOne(queryWrapper);
				if(tbDrawing != null) {
					DrawingInfoVO drawingInfoVO = new DrawingInfoVO();
					drawingInfoVO.setDrawingId(tbDrawing.getId().toString());
					drawingInfoVO.setPrimaryClassId(tbDrawing.getPrimaryClassId().toString());
					drawingInfoVO.setPrimaryClassName(tbDrawing.getPrimaryClassName());
					drawingInfoVO.setSecondaryClassId(tbDrawing.getSecondaryClassId().toString());
					drawingInfoVO.setSecondaryClassName(tbDrawing.getSecondaryClassName());
					drawingInfoVO.setStyleId(tbDrawing.getStyleId().toString());
					drawingInfoVO.setStyleName(tbDrawing.getStyleName());
					drawingInfoVO.setTitle(tbDrawing.getTitle());
					drawingInfoVO.setType(tbDrawing.getType());
					drawingInfoVO.setPrice(tbDrawing.getPrice());
					drawingInfoVO.setVersion(tbDrawing.getVersion());
					drawingInfoVO.setDesignTime(DateUtils.localDateTimeToString(tbDrawing.getDesignTime(), DateUtils.DATE_FORMAT));
					drawingInfoVO.setSynopsis(tbDrawing.getSynopsis());
					drawingInfoVO.setText(tbDrawing.getText());
					drawingInfoVO.setRemarks(tbDrawing.getRemarks());
					List<UploadInfo> uploadInfos = new ArrayList<>();
					QueryWrapper<TbAttachment> attachmentQueryWrapper = new QueryWrapper<>();
					attachmentQueryWrapper.eq(TbAttachment.IS_VALID, IsValidEnum.YES.getKey())
					.eq(TbAttachment.RELATION_ID, modelQueryDetailsParam.getModelId());
					List<TbAttachment> attachmentList = tbAttachmentService.list(attachmentQueryWrapper);
					if(DataUtils.isNotEmpty(attachmentList)) {
						for (TbAttachment tbAttachment : attachmentList) {
							UploadInfo uploadInfo = new UploadInfo();
							uploadInfo.setUploadVersion(tbAttachment.getUploadVersion());
							uploadInfo.setImgType(tbAttachment.getImgType());
							uploadInfo.setFileName(tbAttachment.getAttachmentName());
							uploadInfo.setFile(tbAttachment.getAttachmentPath());
							uploadInfo.setRealName(tbAttachment.getAliasName());
							uploadInfo.setUrl(tbAttachment.getAttachmentPath());
							uploadInfos.add(uploadInfo);
						}
					}
					drawingInfoVO.setUploadImg(uploadInfos);
					return drawingInfoVO;
				}
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: updteDrawing 
	 * @Description: 图纸方案编辑
	 * @param drawingUpdateParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月7日 上午11:55:44 
	*/
	@ApiOperation(value = "图纸方案编辑", httpMethod = "POST", notes = "图纸方案编辑")
	@RequestMapping(value = "/updteDrawing", method = {RequestMethod.POST})
	public JsonResp updteDrawing(
			@ApiParam(name="drawingUpdateParam", value="图纸方案编辑参数", required=false)@RequestBody DrawingUpdateParam drawingUpdateParam) {
		Supplier<String> businessHandler = () ->{
			try {
				tbDrawingSchemeService.updteDrawing(drawingUpdateParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
	
	/**
	 * @Title: updateDrawingStatus 
	 * @Description: 变更状态-图纸方案
	 * @param modelQueryDetailsParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月7日 下午2:53:12 
	*/
	@ApiOperation(value = "变更状态-图纸方案", httpMethod = "POST", notes = "变更状态-图纸方案")
    @RequestMapping(value = "/updateDrawingStatus", method = {RequestMethod.POST})
    public JsonResp updateDrawingStatus(
    		@ApiParam(name="modelQueryDetailsParam", value="图纸方案详情参数", required=false)@RequestBody ModelQueryDetailsParam modelQueryDetailsParam) {
		Supplier<String> businessHandler = () ->{
			try {
				TbDrawingScheme tbDrawing = tbDrawingSchemeService.getById(modelQueryDetailsParam.getModelId());
				if(tbDrawing != null) {
					tbDrawing.setStatus(modelQueryDetailsParam.getStatus());
					tbDrawingSchemeService.updateById(tbDrawing);
					return JsonResultEnum.ok.getValue();
				}else {
					return JsonResultEnum.empty.getValue();
				}
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
	
}
