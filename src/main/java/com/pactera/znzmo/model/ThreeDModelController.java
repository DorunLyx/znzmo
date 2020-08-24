package com.pactera.znzmo.model;

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
import com.pactera.znzmo.vo.homepage.HomePageSimplifyData;
import com.pactera.znzmo.vo.model.ModelAddParam;
import com.pactera.znzmo.vo.model.ModelDetailsVO;
import com.pactera.znzmo.vo.model.ModelInfoVO;
import com.pactera.znzmo.vo.model.ModelListVO;
import com.pactera.znzmo.vo.model.ModelQueryDetailsParam;
import com.pactera.znzmo.vo.model.ModelQueryParam;
import com.pactera.znzmo.vo.model.ModelUpdateParam;
import com.pactera.znzmo.web.BaseController;
import com.pactera.znzmo.web.JsonResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName：ModelController
 * @Description：3D模型
 * @author liyongxu 
 * @date 2020年8月4日 上午10:58:55 
 * @version 1.0.0 
 */
@Api(tags = "3D模型API", value = "3D模型API")
@RestController
@RequestMapping(value = "/3dmodel")
public class ThreeDModelController extends BaseController{
	
	@Autowired
	private TbThreedModelService tbThreedModelService;
	
	@Autowired
	private TbAttachmentService tbAttachmentService;
	
	public static final Logger logger = LoggerFactory.getLogger(ThreeDModelController.class);

	/**
	 * @Title: get3DModelPage 
	 * @Description: 3D模型前台页面查询
	 * @param modelQueryParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月12日 下午4:57:43 
	*/
	@ApiOperation(value = "3D模型前台页面查询", httpMethod = "POST", notes = "3D模型前台页面查询")
    @RequestMapping(value = "/get3DModelPage", method = {RequestMethod.POST})
    public JsonResp get3DModelPage(
    		@ApiParam(name="modelQueryParam", value="3d模型列表筛选参数", required=false)@RequestBody ModelQueryParam modelQueryParam) {
		Supplier<IPage<HomePageSimplifyData>> businessHandler = () -> {
			try {
				List<HomePageSimplifyData> homePageModelDataList = new ArrayList<HomePageSimplifyData>();
				Page<TbThreedModel> page = new Page<TbThreedModel>(modelQueryParam.getPageNo(), modelQueryParam.getPageSize());
		        IPage<HomePageSimplifyData> modeListPage =  new Page<HomePageSimplifyData>(modelQueryParam.getPageNo(), modelQueryParam.getPageSize());
		        IPage<TbThreedModel> iPage = tbThreedModelService.select3DModelPages(page, modelQueryParam);
				for (TbThreedModel tbThreedModel : iPage.getRecords()) {
					HomePageSimplifyData homePageSimplifyData = new HomePageSimplifyData();
					homePageSimplifyData.setReId(tbThreedModel.getId().toString());
					homePageSimplifyData.setReType(ReTypeEnum.MODEL.getKey());
					homePageSimplifyData.setMainGraph(tbThreedModel.getMainGraph());
					homePageSimplifyData.setTitle(tbThreedModel.getTitle());
					homePageSimplifyData.setPrice(tbThreedModel.getPrice());
					homePageSimplifyData.setType(tbThreedModel.getType());
					homePageModelDataList.add(homePageSimplifyData);
				}
				modeListPage.setRecords(homePageModelDataList);
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
	 * @Title: get3DModelDetails 
	 * @Description: 3D模型下载页详情
	 * @param modelQueryDetailsParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月24日 下午4:07:33 
	*/
	@ApiOperation(value = "3D模型下载页详情", httpMethod = "POST", notes = "3D模型下载页详情")
    @RequestMapping(value = "/get3DModelDetails", method = {RequestMethod.POST})
    public JsonResp get3DModelDetails(
    		@ApiParam(name="modelQueryDetailsParam", value="3d模型详情参数", required=false)@RequestBody ModelQueryDetailsParam modelQueryDetailsParam) {
		Supplier<ModelDetailsVO> businessHandler = () ->{
			try {
				QueryWrapper<TbThreedModel> modelQueryWrapper = new QueryWrapper<>();
				modelQueryWrapper.eq(TbThreedModel.IS_VALID, IsValidEnum.YES.getKey())
		        	.eq(TbThreedModel.ID, modelQueryDetailsParam.getModelId());
		        TbThreedModel tbThreedModel = tbThreedModelService.getOne(modelQueryWrapper);
		        if(tbThreedModel != null) {
		        	ModelDetailsVO modelDetailsVO = new ModelDetailsVO();
					modelDetailsVO.setModelId(tbThreedModel.getId().toString());
					modelDetailsVO.setMainGraph(tbThreedModel.getMainGraph());
					List<UploadInfo> uploadInfos = new ArrayList<>();
					QueryWrapper<TbAttachment> attachmentQueryWrapper = new QueryWrapper<>();
					attachmentQueryWrapper.eq(TbAttachment.IS_VALID, IsValidEnum.YES.getKey())
			        	.eq(TbAttachment.RELATION_ID, modelQueryDetailsParam.getModelId());
			        List<TbAttachment> attachmentList = tbAttachmentService.list(attachmentQueryWrapper);
			        if(DataUtils.isNotEmpty(attachmentList)) {
			        	for (TbAttachment tbAttachment : attachmentList) {
							UploadInfo uploadInfo = new UploadInfo();
							uploadInfo.setType(tbAttachment.getReType());
							uploadInfo.setFileName(tbAttachment.getAttachmentName());
							uploadInfo.setFile(tbAttachment.getAttachmentPath());
							uploadInfo.setRealName(tbAttachment.getAliasName());
							uploadInfo.setUrl(tbAttachment.getAttachmentPath());
							uploadInfos.add(uploadInfo);
						}
			        }
			        modelDetailsVO.setUploadImg(uploadInfos);
			        modelDetailsVO.setTitle(tbThreedModel.getTitle());
			        modelDetailsVO.setVisitsNum(tbThreedModel.getVisitsNum());
			        modelDetailsVO.setDownloadNum(tbThreedModel.getDownloadNum());
			        modelDetailsVO.setCollectionNum(tbThreedModel.getDownloadNum());
			        modelDetailsVO.setUpdatetTime(DateUtils.localDateTimeToString(tbThreedModel.getUpdateTime(), DateUtils.DATE_FORMAT));
			        modelDetailsVO.setFileSize("");
					modelDetailsVO.setStyleName(tbThreedModel.getStyleName());
					modelDetailsVO.setTextureMapping(tbThreedModel.getTextureMapping());
					modelDetailsVO.setType(tbThreedModel.getType());
					modelDetailsVO.setLightingEffects(tbThreedModel.getLightingEffects());
					modelDetailsVO.setPrice(tbThreedModel.getPrice());
					modelDetailsVO.setMAXVersion("");
					return modelDetailsVO;
		        }
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: get3DModelList 
	 * @Description: 3D模型列表查询
	 * @param homePageQueryParam
	 * @return JsonResult<IPage<HomePageListVO>>
	 * @author liyongxu
	 * @date 2020年8月4日 上午11:35:35 
	*/
	@ApiOperation(value = "3D模型列表查询", httpMethod = "POST", notes = "3D模型列表查询")
    @RequestMapping(value = "/get3DModelList", method = {RequestMethod.POST})
    public JsonResp get3DModelList(
    		@ApiParam(name="modelQueryParam", value="3d模型列表筛选参数", required=false)@RequestBody ModelQueryParam modelQueryParam) {
		Supplier<IPage<ModelListVO>> businessHandler = () -> {
			try {
				List<ModelListVO> modelList = new ArrayList<ModelListVO>();
				Page<TbThreedModel> page = new Page<TbThreedModel>(modelQueryParam.getPageNo(), modelQueryParam.getPageSize());
		        IPage<ModelListVO> modeListPage =  new Page<ModelListVO>(modelQueryParam.getPageNo(), modelQueryParam.getPageSize());
		        IPage<TbThreedModel> iPage = tbThreedModelService.select3DModelPages(page, modelQueryParam);
				for (TbThreedModel tbThreedModel : iPage.getRecords()) {
					ModelListVO modelListVO = new ModelListVO();
					modelListVO.setModelId(tbThreedModel.getId().toString());
					modelListVO.setMainGraph(tbThreedModel.getMainGraph());
					modelListVO.setCode(tbThreedModel.getCode());
					modelListVO.setPrimaryClassName(tbThreedModel.getPrimaryClassName());
					modelListVO.setSecondaryClassName(tbThreedModel.getSecondaryClassName());
					modelListVO.setStyleName(tbThreedModel.getStyleName());
					modelListVO.setTitle(tbThreedModel.getTitle());
					modelListVO.setType(tbThreedModel.getType());
					modelListVO.setPrice(tbThreedModel.getPrice());
					modelListVO.setTextureMapping(tbThreedModel.getTextureMapping());
					modelListVO.setLightingEffects(tbThreedModel.getLightingEffects());
					modelListVO.setStatus(tbThreedModel.getStatus());
					modelListVO.setVisitsNum(tbThreedModel.getVisitsNum());
					modelListVO.setDownloadNum(tbThreedModel.getDownloadNum());
					modelListVO.setUploadUser(tbThreedModel.getCreateName());
					modelListVO.setUploadTime(DateUtils.localDateTimeToString(tbThreedModel.getCreateTime(), DateUtils.DATE_FORMAT));
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
	 * @Title: add3DModel 
	 * @Description: 3D模型新增
	 * @param modelQueryParam
	 * @return JsonResult
	 * @author liyongxu
	 * @date 2020年8月5日 下午3:18:15 
	*/
	@ApiOperation(value = "3D模型新增", httpMethod = "POST", notes = "3D模型新增")
    @RequestMapping(value = "/add3DModel", method = {RequestMethod.POST})
	public JsonResp add3DModel(
			@ApiParam(name="modelAddParam", value="3d模型新增参数", required=false)@RequestBody ModelAddParam modelAddParam) {
		Supplier<String> businessHandler = () ->{
			try {
				tbThreedModelService.add3DModel(modelAddParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
	
	/**
	 * @Title: get3DModelInfo 
	 * @Description: 3D模型详情
	 * @param modelQueryDetailsParam
	 * @return JsonResult<ModelDetailsVO>
	 * @author liyongxu
	 * @date 2020年8月5日 下午4:09:54 
	*/
	@ApiOperation(value = "3D模型详情", httpMethod = "POST", notes = "3D模型详情")
    @RequestMapping(value = "/get3DModelInfo", method = {RequestMethod.POST})
    public JsonResp get3DModelInfo(
    		@ApiParam(name="modelQueryDetailsParam", value="3d模型详情参数", required=false)@RequestBody ModelQueryDetailsParam modelQueryDetailsParam) {
		Supplier<ModelInfoVO> businessHandler = () ->{
			try {
				QueryWrapper<TbThreedModel> modelQueryWrapper = new QueryWrapper<>();
				modelQueryWrapper.eq(TbThreedModel.IS_VALID, IsValidEnum.YES.getKey())
		        	.eq(TbThreedModel.ID, modelQueryDetailsParam.getModelId());
		        TbThreedModel tbThreedModel = tbThreedModelService.getOne(modelQueryWrapper);
		        if(tbThreedModel != null) {
		        	ModelInfoVO modelInfoVO = new ModelInfoVO();
					modelInfoVO.setModelId(tbThreedModel.getId().toString());
					modelInfoVO.setMainGraph(tbThreedModel.getMainGraph());
					List<UploadInfo> uploadInfos = new ArrayList<>();
					QueryWrapper<TbAttachment> attachmentQueryWrapper = new QueryWrapper<>();
					attachmentQueryWrapper.eq(TbAttachment.IS_VALID, IsValidEnum.YES.getKey())
			        	.eq(TbAttachment.RELATION_ID, modelQueryDetailsParam.getModelId());
			        List<TbAttachment> attachmentList = tbAttachmentService.list(attachmentQueryWrapper);
			        if(DataUtils.isNotEmpty(attachmentList)) {
			        	for (TbAttachment tbAttachment : attachmentList) {
							UploadInfo uploadInfo = new UploadInfo();
							uploadInfo.setType(tbAttachment.getReType());
							uploadInfo.setFileName(tbAttachment.getAttachmentName());
							uploadInfo.setFile(tbAttachment.getAttachmentPath());
							uploadInfo.setRealName(tbAttachment.getAliasName());
							uploadInfo.setUrl(tbAttachment.getAttachmentPath());
							uploadInfos.add(uploadInfo);
						}
			        }
			        modelInfoVO.setUploadImg(uploadInfos);
					modelInfoVO.setPrimaryClassId(tbThreedModel.getPrimaryClassId().toString());
					modelInfoVO.setPrimaryClassName(tbThreedModel.getPrimaryClassName());
					modelInfoVO.setSecondaryClassId(tbThreedModel.getSecondaryClassId().toString());
					modelInfoVO.setSecondaryClassName(tbThreedModel.getSecondaryClassName());
					modelInfoVO.setStyleId(tbThreedModel.getStyleId().toString());
					modelInfoVO.setStyleName(tbThreedModel.getStyleName());
					modelInfoVO.setTitle(tbThreedModel.getTitle());
					modelInfoVO.setType(tbThreedModel.getType());
					modelInfoVO.setPrice(tbThreedModel.getPrice());
					modelInfoVO.setTextureMapping(tbThreedModel.getTextureMapping());
					modelInfoVO.setLightingEffects(tbThreedModel.getLightingEffects());
					modelInfoVO.setRemarks(tbThreedModel.getRemarks());
					return modelInfoVO;
		        }
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: updte3DModel 
	 * @Description: 3D模型编辑
	 * @param modelUpdateParam
	 * @return JsonResult
	 * @author liyongxu
	 * @date 2020年8月5日 下午3:54:44 
	*/
	@ApiOperation(value = "3D模型编辑", httpMethod = "POST", notes = "3D模型编辑")
	@RequestMapping(value = "/updte3DModel", method = {RequestMethod.POST})
	public JsonResp updte3DModel(
			@ApiParam(name="modelUpdateParam", value="3d模型编辑参数", required=false)@RequestBody ModelUpdateParam modelUpdateParam) {
		Supplier<String> businessHandler = () ->{
			try {
				tbThreedModelService.updte3DModel(modelUpdateParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
	
	/**
	 * @Title: update3DModelStatus 
	 * @Description: 变更状态-3d模型
	 * @param modelQueryDetailsParam
	 * @return JsonResult<ModelDetailsVO>
	 * @author liyongxu
	 * @date 2020年8月5日 下午4:12:18 
	*/
	@ApiOperation(value = "变更状态-3d模型", httpMethod = "POST", notes = "变更状态-3d模型")
    @RequestMapping(value = "/update3DModelStatus", method = {RequestMethod.POST})
    public JsonResp update3DModelStatus(
    		@ApiParam(name="modelQueryDetailsParam", value="3d模型详情参数", required=false)@RequestBody ModelQueryDetailsParam modelQueryDetailsParam) {
		Supplier<String> businessHandler = () ->{
			try {
				TbThreedModel tbThreedModel = tbThreedModelService.getById(modelQueryDetailsParam.getModelId());
				if(tbThreedModel != null) {
					tbThreedModel.setStatus(modelQueryDetailsParam.getStatus());
					tbThreedModelService.updateById(tbThreedModel);
					return JsonResultEnum.ok.getValue();
				}else {
					return JsonResultEnum.empty.getValue();
				}
			} catch (Exception e) {
				throwException(e);
				logger.error(e.getMessage(),e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
    }
	
}
