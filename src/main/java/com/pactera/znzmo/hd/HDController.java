package com.pactera.znzmo.hd;

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
import com.pactera.znzmo.vo.hd.HDMappingAddParam;
import com.pactera.znzmo.vo.hd.HDMappingDetailsVO;
import com.pactera.znzmo.vo.hd.HDMappingInfoVO;
import com.pactera.znzmo.vo.hd.HDMappingUpdateParam;
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
 * @ClassName：HDController
 * @Description：HD贴图Api
 * @author liyongxu 
 * @date 2020年8月7日 上午15:35:16 
 * @version 1.0.0 
 */
@Api(tags = "HD贴图API", value = "HD贴图API")
@RestController
@RequestMapping(value = "/HdMapping")
public class HDController extends BaseController{
	
	@Autowired
	private TbHdMappingService tbHdMappingService;
	
	@Autowired
	private TbAttachmentService tbAttachmentService;
	
	public static final Logger logger = LoggerFactory.getLogger(HDController.class);

	/**
	 * @Title: getHdMappingPage 
	 * @Description: HD贴图首页查询
	 * @param modelQueryParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月24日 下午5:52:49 
	*/
	@ApiOperation(value = "HD贴图首页查询", httpMethod = "POST", notes = "HD贴图首页查询")
	@RequestMapping(value = "/getHdMappingPage", method = {RequestMethod.POST})
	public JsonResp getHdMappingPage(
			@ApiParam(name="modelQueryParam", value="Su模型列表筛选参数", required=false)@RequestBody ModelQueryParam modelQueryParam) {
		Supplier<IPage<HomePageSimplifyData>> businessHandler = () ->{
			try {
				List<HomePageSimplifyData> homePageHdList = new ArrayList<HomePageSimplifyData>();
				Page<TbHdMapping> page = new Page<TbHdMapping>(modelQueryParam.getPageNo(), modelQueryParam.getPageSize());
				IPage<HomePageSimplifyData> hdListPage =  new Page<HomePageSimplifyData>(modelQueryParam.getPageNo(), modelQueryParam.getPageSize());
				IPage<TbHdMapping> iPage = tbHdMappingService.selectHdMappingPages(page, modelQueryParam);
				for (TbHdMapping tbHdMapping : iPage.getRecords()) {
					HomePageSimplifyData homePageSimplifyData = new HomePageSimplifyData();
					homePageSimplifyData.setReId(tbHdMapping.getId().toString());
					homePageSimplifyData.setReType(ReTypeEnum.MODEL.getKey());
					TbAttachment tbAttachment = tbAttachmentService.getById(tbHdMapping.getMainGraph());
			        if(tbAttachment != null) {
			        	homePageSimplifyData.setMainGraph(tbAttachment.getAttachmentPath());
			        }
					homePageSimplifyData.setTitle(tbHdMapping.getTitle());
					homePageSimplifyData.setPrice(tbHdMapping.getPrice());
					homePageSimplifyData.setType(tbHdMapping.getType());
					homePageHdList.add(homePageSimplifyData);
				}
				hdListPage.setRecords(homePageHdList);
				hdListPage.setCurrent(iPage.getCurrent());
				hdListPage.setPages(iPage.getPages());
				hdListPage.setSize(iPage.getSize());
				hdListPage.setTotal(iPage.getTotal());			
				return hdListPage;
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
	}
	
	/**
	 * @Title: getHdMappingDetails 
	 * @Description: HD贴图下载页详情
	 * @param modelQueryDetailsParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月24日 下午6:01:51 
	*/
	@ApiOperation(value = "HD贴图下载页详情", httpMethod = "POST", notes = "HD贴图下载页详情")
    @RequestMapping(value = "/getHdMappingDetails", method = {RequestMethod.POST})
    public JsonResp getHdMappingDetails(
    		@ApiParam(name="modelQueryDetailsParam", value="HD贴图详情参数", required=false)@RequestBody ModelQueryDetailsParam modelQueryDetailsParam) {
		Supplier<HDMappingDetailsVO> businessHandler = () ->{
			try {
				QueryWrapper<TbHdMapping> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq(TbHdMapping.IS_VALID, IsValidEnum.YES.getKey())
		        	.eq(TbHdMapping.ID, modelQueryDetailsParam.getModelId());
				TbHdMapping tbHdMapping = tbHdMappingService.getOne(queryWrapper);
				if(tbHdMapping != null) {
					HDMappingDetailsVO hdMappingDetailsVO = new HDMappingDetailsVO();
					hdMappingDetailsVO.setHdId(tbHdMapping.getId().toString());
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
							if(tbAttachment.getId().equals(Long.valueOf(tbHdMapping.getMainGraph()))) {
								hdMappingDetailsVO.setFileSize(tbAttachment.getAttachmentSize());
								hdMappingDetailsVO.setImageSize(tbAttachment.getPictureSize());
								hdMappingDetailsVO.setFileFormat(tbAttachment.getSuffix());
							}
						}
					}
					hdMappingDetailsVO.setUploadImg(uploadInfos);
					hdMappingDetailsVO.setTitle(tbHdMapping.getTitle());
					hdMappingDetailsVO.setVisitsNum(tbHdMapping.getVisitsNum());
					hdMappingDetailsVO.setDownloadNum(tbHdMapping.getDownloadNum());
					hdMappingDetailsVO.setCollectionNum(tbHdMapping.getDownloadNum());
					hdMappingDetailsVO.setUpdatetTime(DateUtils.localDateTimeToString(tbHdMapping.getUpdateTime(), DateUtils.DATE_FORMAT));
					hdMappingDetailsVO.setPrice(tbHdMapping.getPrice());
					return hdMappingDetailsVO;
				}
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: getHdMappingList 
	 * @Description: HD贴图列表查询
	 * @param modelQueryParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月7日 下午3:38:28 
	*/
	@ApiOperation(value = "HD贴图列表查询", httpMethod = "POST", notes = "HD贴图列表查询")
    @RequestMapping(value = "/getHdMappingList", method = {RequestMethod.POST})
    public JsonResp getHdMappingList(
    		@ApiParam(name="modelQueryParam", value="Su模型列表筛选参数", required=false)@RequestBody ModelQueryParam modelQueryParam) {
		Supplier<IPage<ModelListVO>> businessHandler = () ->{
			try {
				List<ModelListVO> modelList = new ArrayList<ModelListVO>();
				Page<TbHdMapping> page = new Page<TbHdMapping>(modelQueryParam.getPageNo(), modelQueryParam.getPageSize());
				IPage<ModelListVO> modeListPage =  new Page<ModelListVO>(modelQueryParam.getPageNo(), modelQueryParam.getPageSize());
				IPage<TbHdMapping> iPage = tbHdMappingService.selectHdMappingPages(page, modelQueryParam);
				for (TbHdMapping tbHdMapping : iPage.getRecords()) {
					ModelListVO modelListVO = new ModelListVO();
					modelListVO.setModelId(tbHdMapping.getId().toString());
					TbAttachment tbAttachment = tbAttachmentService.getById(tbHdMapping.getMainGraph());
			        if(tbAttachment != null) {
			        	modelListVO.setMainGraph(tbAttachment.getAttachmentPath());
			        }
					modelListVO.setCode(tbHdMapping.getCode());
					modelListVO.setPrimaryClassName(tbHdMapping.getPrimaryClassName());
					modelListVO.setSecondaryClassName(tbHdMapping.getSecondaryClassName());
					modelListVO.setStyleName(tbHdMapping.getStyleName());
					modelListVO.setTitle(tbHdMapping.getTitle());
					modelListVO.setType(tbHdMapping.getType());
					modelListVO.setPrice(tbHdMapping.getPrice());
					modelListVO.setStatus(tbHdMapping.getStatus());
					modelListVO.setVisitsNum(tbHdMapping.getVisitsNum());
					modelListVO.setDownloadNum(tbHdMapping.getDownloadNum());
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
	 * @Title: addHdMapping 
	 * @Description: HD贴图新增
	 * @param hdMappingAddParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月7日 下午3:48:16 
	*/
	@ApiOperation(value = "HD贴图新增", httpMethod = "POST", notes = "HD贴图新增")
    @RequestMapping(value = "/addHdMapping", method = {RequestMethod.POST})
	public JsonResp addHdMapping(
			@ApiParam(name="hdMappingAddParam", value="HD贴图新增参数", required=false)@RequestBody HDMappingAddParam hdMappingAddParam) {
		Supplier<String> businessHandler = () ->{
			try {
				tbHdMappingService.addHdMapping(hdMappingAddParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
	
	/**
	 * @Title: getHdMappingInfo 
	 * @Description: HD贴图详情
	 * @param modelQueryDetailsParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月7日 下午3:48:40 
	*/
	@ApiOperation(value = "HD贴图详情", httpMethod = "POST", notes = "HD贴图详情")
    @RequestMapping(value = "/getHdMappingInfo", method = {RequestMethod.POST})
    public JsonResp getHdMappingInfo(
    		@ApiParam(name="modelQueryDetailsParam", value="HD贴图详情参数", required=false)@RequestBody ModelQueryDetailsParam modelQueryDetailsParam) {
		Supplier<HDMappingInfoVO> businessHandler = () ->{
			try {
				QueryWrapper<TbHdMapping> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq(TbHdMapping.IS_VALID, IsValidEnum.YES.getKey())
		        	.eq(TbHdMapping.ID, modelQueryDetailsParam.getModelId());
				TbHdMapping tbHdMapping = tbHdMappingService.getOne(queryWrapper);
				if(tbHdMapping != null) {
					HDMappingInfoVO hDMappingInfoVO = new HDMappingInfoVO();
					hDMappingInfoVO.setHdId(tbHdMapping.getId().toString());
					hDMappingInfoVO.setPrimaryClassId(tbHdMapping.getPrimaryClassId().toString());
					hDMappingInfoVO.setPrimaryClassName(tbHdMapping.getPrimaryClassName());
					hDMappingInfoVO.setSecondaryClassId(tbHdMapping.getSecondaryClassId().toString());
					hDMappingInfoVO.setSecondaryClassName(tbHdMapping.getSecondaryClassName());
					hDMappingInfoVO.setStyleId(tbHdMapping.getStyleId().toString());
					hDMappingInfoVO.setStyleName(tbHdMapping.getStyleName());
					hDMappingInfoVO.setTitle(tbHdMapping.getTitle());
					hDMappingInfoVO.setType(tbHdMapping.getType());
					hDMappingInfoVO.setPrice(tbHdMapping.getPrice());
					hDMappingInfoVO.setRemarks(tbHdMapping.getRemarks());
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
					hDMappingInfoVO.setUploadImg(uploadInfos);
					return hDMappingInfoVO;
				}
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: updteHdMapping 
	 * @Description: HD贴图编辑
	 * @param hDMappingUpdateParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月7日 下午3:49:49 
	*/
	@ApiOperation(value = "HD贴图编辑", httpMethod = "POST", notes = "HD贴图编辑")
	@RequestMapping(value = "/updteHdMapping", method = {RequestMethod.POST})
	public JsonResp updteHdMapping(
			@ApiParam(name="hDMappingUpdateParam", value="HD贴图编辑参数", required=false)@RequestBody HDMappingUpdateParam hDMappingUpdateParam) {
		Supplier<String> businessHandler = () ->{
			try {
				tbHdMappingService.updteHdMapping(hDMappingUpdateParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
	
	/**
	 * @Title: updateHdMappingStatus 
	 * @Description: 变更状态-HD贴图
	 * @param modelQueryDetailsParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月7日 下午4:08:46 
	*/
	@ApiOperation(value = "变更状态-HD贴图", httpMethod = "POST", notes = "变更状态-HD贴图")
    @RequestMapping(value = "/updateHdMappingStatus", method = {RequestMethod.POST})
    public JsonResp updateHdMappingStatus(
    		@ApiParam(name="modelQueryDetailsParam", value="HD贴图详情参数", required=false)@RequestBody ModelQueryDetailsParam modelQueryDetailsParam) {
		Supplier<String> businessHandler = () ->{
			try {
				TbHdMapping tbHdMapping = tbHdMappingService.getById(modelQueryDetailsParam.getModelId());
				if(tbHdMapping != null) {
					tbHdMapping.setStatus(modelQueryDetailsParam.getStatus());
					tbHdMappingService.updateById(tbHdMapping);
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
