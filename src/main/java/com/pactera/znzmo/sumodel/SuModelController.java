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
import com.pactera.znzmo.util.DataUtils;
import com.pactera.znzmo.vo.ModelListVO;
import com.pactera.znzmo.vo.ModelQueryDetailsParam;
import com.pactera.znzmo.vo.ModelQueryParam;
import com.pactera.znzmo.vo.SuModelAddParam;
import com.pactera.znzmo.vo.SuModelDetailsVO;
import com.pactera.znzmo.vo.SuModelUpdateParam;
import com.pactera.znzmo.vo.UploadInfo;
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
	@ApiOperation(value = "su模型列表查询", httpMethod = "POST", notes = "su模型列表查询")
    @RequestMapping(value = "/getSuModelList", method = {RequestMethod.POST})
    public JsonResp getSuModelList(
    		@ApiParam(name="modelQueryParam", value="Su模型列表筛选参数", required=false)@RequestBody ModelQueryParam modelQueryParam) {
		Supplier<IPage<ModelListVO>> businessHandler = () ->{
			try {
				List<ModelListVO> modelList = new ArrayList<ModelListVO>();
				Page<TbSuModel> page = new Page<TbSuModel>(modelQueryParam.getPageNo(), modelQueryParam.getPageSize());
				IPage<ModelListVO> modeListPage =  new Page<ModelListVO>(modelQueryParam.getPageNo(), modelQueryParam.getPageSize());
				IPage<TbSuModel> iPage = tbSuModelService.selectSuModelPages(page, modelQueryParam);
				for (TbSuModel tbSuModel : iPage.getRecords()) {
					ModelListVO modelListVO = new ModelListVO();
					modelListVO.setModelId(tbSuModel.getId());
					modelListVO.setMainGraph(tbSuModel.getMainGraph());
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
		Supplier<SuModelDetailsVO> businessHandler = () ->{
			try {
				QueryWrapper<TbSuModel> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq(TbSuModel.IS_VALID, IsValidEnum.YES.getKey())
		        	.eq(TbSuModel.ID, modelQueryDetailsParam.getModelId());
		        TbSuModel tbSuModel = tbSuModelService.getOne(queryWrapper);
		        SuModelDetailsVO suModelDetailsVO = new SuModelDetailsVO();
				suModelDetailsVO.setModelId(tbSuModel.getId());
				suModelDetailsVO.setMainGraph(tbSuModel.getMainGraph());
				suModelDetailsVO.setPrimaryClassId(tbSuModel.getPrimaryClassId());
				suModelDetailsVO.setPrimaryClassName(tbSuModel.getPrimaryClassName());
				suModelDetailsVO.setSecondaryClassId(tbSuModel.getSecondaryClassId());
				suModelDetailsVO.setSecondaryClassName(tbSuModel.getSecondaryClassName());
				suModelDetailsVO.setThreeClassId(tbSuModel.getThreeClassId());
				suModelDetailsVO.setThreeClassName(tbSuModel.getThreeClassName());
				suModelDetailsVO.setStyleId(tbSuModel.getStyleId());
				suModelDetailsVO.setStyleName(tbSuModel.getStyleName());
				suModelDetailsVO.setTitle(tbSuModel.getTitle());
				suModelDetailsVO.setType(tbSuModel.getType());
				suModelDetailsVO.setPrice(tbSuModel.getPrice());
				suModelDetailsVO.setTextureMapping(tbSuModel.getTextureMapping());
				suModelDetailsVO.setVersion(tbSuModel.getVersion());
				suModelDetailsVO.setRemarks(tbSuModel.getRemarks());
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
		        suModelDetailsVO.setUploadImg(uploadInfos);
				return suModelDetailsVO;
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
				tbSuModel.setStatus(modelQueryDetailsParam.getStatus());
				tbSuModelService.updateById(tbSuModel);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
	
}
