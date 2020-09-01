package com.pactera.znzmo.sumodel;

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
import com.pactera.znzmo.vo.model.ModelListVO;
import com.pactera.znzmo.vo.model.ModelQueryDetailsParam;
import com.pactera.znzmo.vo.model.ModelQueryParam;
import com.pactera.znzmo.vo.su.SuModelAddParam;
import com.pactera.znzmo.vo.su.SuModelDetailsVO;
import com.pactera.znzmo.vo.su.SuModelInfoVO;
import com.pactera.znzmo.vo.su.SuModelUpdateParam;
import com.pactera.znzmo.web.BaseController;
import com.pactera.znzmo.web.JsonResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName：SuModelController
 * @Description：su模型
 * @author liyongxu 
 * @date 2020年8月4日 上午10:58:55 
 * @version 1.0.0 
 */
@Api(tags = "su模型API", value = "su模型API")
@RestController
@RequestMapping(value = "/sumodel")
public class SuModelController extends BaseController{
	
	@Autowired
	private TbSuModelService tbSuModelService;
	
	@Autowired
	private TbAttachmentService tbAttachmentService;
	
	public static final Logger logger = LoggerFactory.getLogger(SuModelController.class);

	/**
	 * @Title: getsuModelList 
	 * @Description: su模型列表查询
	 * @param homePageQueryParam
	 * @return JsonResult<IPage<HomePageListVO>>
	 * @author liyongxu
	 * @date 2020年8月4日 上午11:35:35 
	*/
	@ApiOperation(value = "su模型前台页面查询", httpMethod = "POST", notes = "su模型前台页面查询")
    @RequestMapping(value = "/getSuModelPage", method = {RequestMethod.POST})
    public JsonResp getSuModelPage(
    		@ApiParam(name="modelQueryParam", value="Su模型列表筛选参数", required=false)@RequestBody ModelQueryParam modelQueryParam) {
		Supplier<IPage<HomePageSimplifyData>> businessHandler = () ->{
			try {
				List<HomePageSimplifyData> homePageSuModelDataList = new ArrayList<HomePageSimplifyData>();
				Page<TbSuModel> page = new Page<TbSuModel>(modelQueryParam.getPageNo(), modelQueryParam.getPageSize());
				IPage<HomePageSimplifyData> suModeListPage =  new Page<HomePageSimplifyData>(modelQueryParam.getPageNo(), modelQueryParam.getPageSize());
				IPage<TbSuModel> iPage = tbSuModelService.selectSuModelPages(page, modelQueryParam);
				for (TbSuModel tbSuModel : iPage.getRecords()) {
					HomePageSimplifyData homePageSimplifyData = new HomePageSimplifyData();
					homePageSimplifyData.setReId(tbSuModel.getId().toString());
					homePageSimplifyData.setReType(ReTypeEnum.MODEL.getKey());
					TbAttachment tbAttachment = tbAttachmentService.getById(tbSuModel.getMainGraph());
			        if(tbAttachment != null) {
			        	homePageSimplifyData.setMainGraph(tbAttachment.getAttachmentPath());
			        }
					homePageSimplifyData.setTitle(tbSuModel.getTitle());
					homePageSimplifyData.setPrice(tbSuModel.getPrice());
					homePageSimplifyData.setType(tbSuModel.getType());
					homePageSuModelDataList.add(homePageSimplifyData);
	    		}
				suModeListPage.setRecords(homePageSuModelDataList);
				suModeListPage.setCurrent(iPage.getCurrent());
				suModeListPage.setPages(iPage.getPages());
				suModeListPage.setSize(iPage.getSize());
				suModeListPage.setTotal(iPage.getTotal());			
				return suModeListPage;
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: getSuModelDetails 
	 * @Description: su模型下载页详情
	 * @param modelQueryDetailsParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月24日 下午5:26:42 
	*/
	@ApiOperation(value = "su模型下载页详情", httpMethod = "POST", notes = "su模型详情")
    @RequestMapping(value = "/getSuModelDetails", method = {RequestMethod.POST})
    public JsonResp getSuModelDetails(
    		@ApiParam(name="modelQueryDetailsParam", value="Su模型详情参数", required=false)@RequestBody ModelQueryDetailsParam modelQueryDetailsParam) {
		Supplier<SuModelDetailsVO> businessHandler = () ->{
			try {
				QueryWrapper<TbSuModel> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq(TbSuModel.IS_VALID, IsValidEnum.YES.getKey())
		        	.eq(TbSuModel.ID, modelQueryDetailsParam.getModelId());
		        TbSuModel tbSuModel = tbSuModelService.getOne(queryWrapper);
		        if(tbSuModel != null) {
		        	SuModelDetailsVO suModelDetailsVO = new SuModelDetailsVO();
					suModelDetailsVO.setSuModelId(tbSuModel.getId().toString());
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
							if(tbAttachment.getId().equals(Long.valueOf(tbSuModel.getMainGraph()))) {
								suModelDetailsVO.setFileSize(tbAttachment.getAttachmentSize());
							}
						}
			        }
			        suModelDetailsVO.setUploadImg(uploadInfos);
					suModelDetailsVO.setVisitsNum(tbSuModel.getVisitsNum());
					suModelDetailsVO.setDownloadNum(tbSuModel.getDownloadNum());
					suModelDetailsVO.setCollectionNum(tbSuModel.getDownloadNum());
					suModelDetailsVO.setUpdatetTime(DateUtils.localDateTimeToString(tbSuModel.getUpdateTime(), DateUtils.DATE_FORMAT));
					suModelDetailsVO.setStyleName(tbSuModel.getStyleName());
					suModelDetailsVO.setTitle(tbSuModel.getTitle());
					suModelDetailsVO.setType(tbSuModel.getType());
					suModelDetailsVO.setPrice(tbSuModel.getPrice());
					suModelDetailsVO.setTextureMapping(tbSuModel.getTextureMapping());
					suModelDetailsVO.setSuVersion(tbSuModel.getVersion());
					return suModelDetailsVO;
		        }
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: getsuModelList 
	 * @Description: su模型列表查询
	 * @param homePageQueryParam
	 * @return JsonResult<IPage<HomePageListVO>>
	 * @author liyongxu
	 * @date 2020年8月4日 上午11:35:35 
	*/
	@ApiOperation(value = "su模型列表查询", httpMethod = "POST", notes = "su模型列表查询")
    @RequestMapping(value = "/getSuModelList", method = {RequestMethod.POST})
    public JsonResp getSuModelList(
    		@ApiParam(name="modelQueryParam", value="Su模型列表筛选参数", required=false)@RequestBody ModelQueryParam modelQueryParam) {
		Supplier<IPage<ModelListVO>> businessHandler = () ->{
			try {
				List<ModelListVO> modelList = new ArrayList<ModelListVO>();
				Page<TbSuModel> page = new Page<TbSuModel>(modelQueryParam.getPageNo(), modelQueryParam.getPageSize());
				IPage<ModelListVO> suModeListPage =  new Page<ModelListVO>(modelQueryParam.getPageNo(), modelQueryParam.getPageSize());
				IPage<TbSuModel> iPage = tbSuModelService.selectSuModelPages(page, modelQueryParam);
				for (TbSuModel tbSuModel : iPage.getRecords()) {
					ModelListVO modelListVO = new ModelListVO();
					modelListVO.setModelId(tbSuModel.getId().toString());
					TbAttachment tbAttachment = tbAttachmentService.getById(tbSuModel.getMainGraph());
			        if(tbAttachment != null) {
			        	modelListVO.setMainGraph(tbAttachment.getAttachmentPath());
			        }
					modelListVO.setCode(tbSuModel.getCode());
					modelListVO.setPrimaryClassName(tbSuModel.getPrimaryClassName());
					modelListVO.setSecondaryClassName(tbSuModel.getSecondaryClassName());
					modelListVO.setStyleName(tbSuModel.getStyleName());
					modelListVO.setTitle(tbSuModel.getTitle());
					modelListVO.setType(tbSuModel.getType());
					modelListVO.setPrice(tbSuModel.getPrice());
					modelListVO.setTextureMapping(tbSuModel.getTextureMapping());
					modelListVO.setStatus(tbSuModel.getStatus());
					modelListVO.setVisitsNum(tbSuModel.getVisitsNum());
					modelListVO.setDownloadNum(tbSuModel.getDownloadNum());
					modelList.add(modelListVO);
	    		}
				suModeListPage.setRecords(modelList);
				suModeListPage.setCurrent(iPage.getCurrent());
				suModeListPage.setPages(iPage.getPages());
				suModeListPage.setSize(iPage.getSize());
				suModeListPage.setTotal(iPage.getTotal());			
				return suModeListPage;
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }

	/**
	 * @Title: addsuModel 
	 * @Description: su模型新增
	 * @param modelQueryParam
	 * @return JsonResult
	 * @author liyongxu
	 * @date 2020年8月5日 下午3:18:15 
	*/
	@ApiOperation(value = "su模型新增", httpMethod = "POST", notes = "su模型新增")
    @RequestMapping(value = "/addSuModel", method = {RequestMethod.POST})
	public JsonResp addSuModel(
			@ApiParam(name="suModelAddParam", value="Su模型新增参数", required=false)@RequestBody SuModelAddParam suModelAddParam) {
		Supplier<String> businessHandler = () ->{
			try {
				tbSuModelService.addSuModel(suModelAddParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
	
	/**
	 * @Title: getsuModelInfo 
	 * @Description: su模型详情
	 * @param modelQueryDetailsParam
	 * @return JsonResult<ModelDetailsVO>
	 * @author liyongxu
	 * @date 2020年8月5日 下午4:09:54 
	*/
	@ApiOperation(value = "su模型详情", httpMethod = "POST", notes = "su模型详情")
    @RequestMapping(value = "/getSuModelInfo", method = {RequestMethod.POST})
    public JsonResp getSuModelInfo(
    		@ApiParam(name="modelQueryDetailsParam", value="Su模型详情参数", required=false)@RequestBody ModelQueryDetailsParam modelQueryDetailsParam) {
		Supplier<SuModelInfoVO> businessHandler = () ->{
			try {
				QueryWrapper<TbSuModel> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq(TbSuModel.IS_VALID, IsValidEnum.YES.getKey())
		        	.eq(TbSuModel.ID, modelQueryDetailsParam.getModelId());
		        TbSuModel tbSuModel = tbSuModelService.getOne(queryWrapper);
		        if(tbSuModel != null) {
		        	SuModelInfoVO suModelInfoVO = new SuModelInfoVO();
					suModelInfoVO.setModelId(tbSuModel.getId().toString());
					suModelInfoVO.setPrimaryClassId(tbSuModel.getPrimaryClassId().toString());
					suModelInfoVO.setPrimaryClassName(tbSuModel.getPrimaryClassName());
					suModelInfoVO.setSecondaryClassId(tbSuModel.getSecondaryClassId().toString());
					suModelInfoVO.setSecondaryClassName(tbSuModel.getSecondaryClassName());
					suModelInfoVO.setThreeClassId(tbSuModel.getThreeClassId().toString());
					suModelInfoVO.setThreeClassName(tbSuModel.getThreeClassName());
					suModelInfoVO.setStyleId(tbSuModel.getStyleId().toString());
					suModelInfoVO.setStyleName(tbSuModel.getStyleName());
					suModelInfoVO.setTitle(tbSuModel.getTitle());
					suModelInfoVO.setType(tbSuModel.getType());
					suModelInfoVO.setPrice(tbSuModel.getPrice());
					suModelInfoVO.setTextureMapping(tbSuModel.getTextureMapping());
					suModelInfoVO.setVersion(tbSuModel.getVersion());
					suModelInfoVO.setRemarks(tbSuModel.getRemarks());
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
			        suModelInfoVO.setUploadImg(uploadInfos);
					return suModelInfoVO;
		        }
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: updtesuModel 
	 * @Description: su模型编辑
	 * @param suModelUpdateParam
	 * @return JsonResult
	 * @author liyongxu
	 * @date 2020年8月5日 下午3:54:44 
	*/
	@ApiOperation(value = "su模型编辑", httpMethod = "POST", notes = "su模型编辑")
	@RequestMapping(value = "/updteSuModel", method = {RequestMethod.POST})
	public JsonResp updteSuModel(
			@ApiParam(name="suModelUpdateParam", value="Su模型编辑参数", required=false)@RequestBody SuModelUpdateParam suModelUpdateParam) {
		Supplier<String> businessHandler = () ->{
			try {
				tbSuModelService.updteSuModel(suModelUpdateParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
	
	/**
	 * @Title: updatesuModelStatus 
	 * @Description: 变更状态-Su模型
	 * @param modelQueryDetailsParam
	 * @return JsonResult<ModelDetailsVO>
	 * @author liyongxu
	 * @date 2020年8月5日 下午4:12:18 
	*/
	@ApiOperation(value = "变更状态-Su模型", httpMethod = "POST", notes = "变更状态-Su模型")
    @RequestMapping(value = "/updateSuModelStatus", method = {RequestMethod.POST})
    public JsonResp updateSuModelStatus(
    		@ApiParam(name="modelQueryDetailsParam", value="Su模型详情参数", required=false)@RequestBody ModelQueryDetailsParam modelQueryDetailsParam) {
		Supplier<String> businessHandler = () ->{
			try {
				TbSuModel tbSuModel = tbSuModelService.getById(modelQueryDetailsParam.getModelId());
				if(tbSuModel != null) {
					tbSuModel.setStatus(modelQueryDetailsParam.getStatus());
					tbSuModelService.updateById(tbSuModel);
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
