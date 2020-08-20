package com.pactera.znzmo.database;

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
import com.pactera.znzmo.vo.common.UploadInfo;
import com.pactera.znzmo.vo.database.DatabaseAddParam;
import com.pactera.znzmo.vo.database.DatabaseUpdateParam;
import com.pactera.znzmo.vo.drawing.DrawingDetailsVO;
import com.pactera.znzmo.vo.model.ModelListVO;
import com.pactera.znzmo.vo.model.ModelQueryDetailsParam;
import com.pactera.znzmo.vo.model.ModelQueryParam;
import com.pactera.znzmo.web.BaseController;
import com.pactera.znzmo.web.JsonResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName：DatabaseController
 * @Description：资料库API
 * @author liyongxu 
 * @date 2020年8月13日 上午11:30:26 
 * @version 1.0.0 
 */
@Api(tags = "资料库API", value = "资料库API")
@RestController
@RequestMapping(value = "/database")
public class DatabaseController extends BaseController{
	
	@Autowired
	private TbDatabaseService tbDatabaseService;
	
	@Autowired
	private TbAttachmentService tbAttachmentService;
	
	public static final Logger logger = LoggerFactory.getLogger(DatabaseController.class);

	/**
	 * @Title: getDatabaseList 
	 * @Description: 资料库管理列表查询
	 * @param modelQueryParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月13日 上午11:48:28 
	*/
	@ApiOperation(value = "资料库管理列表查询", httpMethod = "POST", notes = "资料库管理列表查询")
    @RequestMapping(value = "/getDatabaseList", method = {RequestMethod.POST})
    public JsonResp getDatabaseList(
    		@ApiParam(name="modelQueryParam", value="资料库列表筛选参数", required=false)@RequestBody ModelQueryParam modelQueryParam) {
		Supplier<IPage<ModelListVO>> businessHandler = () ->{
			try {
				List<ModelListVO> modelList = new ArrayList<ModelListVO>();
				Page<TbDatabase> page = new Page<TbDatabase>(modelQueryParam.getPageNo(), modelQueryParam.getPageSize());
				IPage<ModelListVO> modeListPage =  new Page<ModelListVO>(modelQueryParam.getPageNo(), modelQueryParam.getPageSize());
				IPage<TbDatabase> iPage = tbDatabaseService.selectDatabasePages(page, modelQueryParam);
				for (TbDatabase tbDrawing : iPage.getRecords()) {
					ModelListVO modelListVO = new ModelListVO();
					modelListVO.setModelId(tbDrawing.getId());
					modelListVO.setMainGraph(tbDrawing.getMainGraph());
					modelListVO.setCode(tbDrawing.getCode());
					modelListVO.setPrimaryClassName(tbDrawing.getPrimaryClassName());
					modelListVO.setSecondaryClassName(tbDrawing.getSecondaryClassName());
					modelListVO.setStyleName(tbDrawing.getStyleName());
					modelListVO.setTitle(tbDrawing.getTitle());
					modelListVO.setType(tbDrawing.getType());
					modelListVO.setPrice(tbDrawing.getPrice());
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
	 * @Title: addDatabase 
	 * @Description: 资料库新增
	 * @param databaseAddParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月13日 下午2:13:30 
	*/
	@ApiOperation(value = "资料库新增", httpMethod = "POST", notes = "资料库新增")
    @RequestMapping(value = "/addDatabase", method = {RequestMethod.POST})
	public JsonResp addDatabase(
			@ApiParam(name="databaseAddParam", value="资料库新增参数", required=false)@RequestBody DatabaseAddParam databaseAddParam) {
		Supplier<String> businessHandler = () ->{
			try {
				tbDatabaseService.addDatabase(databaseAddParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
	
	/**
	 * @Title: getDatabaseInfo 
	 * @Description: 资料库详情
	 * @param modelQueryDetailsParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月13日 下午2:13:37 
	*/
	@ApiOperation(value = "资料库详情", httpMethod = "POST", notes = "资料库详情")
    @RequestMapping(value = "/getDatabaseInfo", method = {RequestMethod.POST})
    public JsonResp getDatabaseInfo(
    		@ApiParam(name="modelQueryDetailsParam", value="资料库详情参数", required=false)@RequestBody ModelQueryDetailsParam modelQueryDetailsParam) {
		Supplier<DrawingDetailsVO> businessHandler = () ->{
			try {
				QueryWrapper<TbDatabase> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq(TbDatabase.IS_VALID, IsValidEnum.YES.getKey())
		        	.eq(TbDatabase.ID, modelQueryDetailsParam.getModelId());
				TbDatabase tbDatabase = tbDatabaseService.getOne(queryWrapper);
				if(tbDatabase != null) {
					DrawingDetailsVO drawingDetailsVO = new DrawingDetailsVO();
					drawingDetailsVO.setDrawingId(tbDatabase.getId());
					drawingDetailsVO.setMainGraph(tbDatabase.getMainGraph());
					drawingDetailsVO.setPrimaryClassId(tbDatabase.getPrimaryClassId());
					drawingDetailsVO.setPrimaryClassName(tbDatabase.getPrimaryClassName());
					drawingDetailsVO.setSecondaryClassId(tbDatabase.getSecondaryClassId());
					drawingDetailsVO.setSecondaryClassName(tbDatabase.getSecondaryClassName());
					drawingDetailsVO.setStyleId(tbDatabase.getStyleId());
					drawingDetailsVO.setStyleName(tbDatabase.getStyleName());
					drawingDetailsVO.setTitle(tbDatabase.getTitle());
					drawingDetailsVO.setType(tbDatabase.getType());
					drawingDetailsVO.setPrice(tbDatabase.getPrice());
					drawingDetailsVO.setRemarks(tbDatabase.getRemarks());
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
					drawingDetailsVO.setUploadImg(uploadInfos);
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
	 * @Title: updteDatabase 
	 * @Description: 资料库编辑
	 * @param databaseUpdateParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月13日 下午2:13:46 
	*/
	@ApiOperation(value = "资料库编辑", httpMethod = "POST", notes = "资料库编辑")
	@RequestMapping(value = "/updteDatabase", method = {RequestMethod.POST})
	public JsonResp updteDatabase(
			@ApiParam(name="databaseUpdateParam", value="资料库编辑参数", required=false)@RequestBody DatabaseUpdateParam databaseUpdateParam) {
		Supplier<String> businessHandler = () ->{
			try {
				tbDatabaseService.updteDatabase(databaseUpdateParam);
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
	 * @Description: 变更状态-资料库
	 * @param modelQueryDetailsParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月13日 下午2:18:47 
	*/
	@ApiOperation(value = "变更状态-资料库", httpMethod = "POST", notes = "变更状态-资料库")
    @RequestMapping(value = "/updateDrawingStatus", method = {RequestMethod.POST})
    public JsonResp updateDrawingStatus(
    		@ApiParam(name="modelQueryDetailsParam", value="资料库详情参数", required=false)@RequestBody ModelQueryDetailsParam modelQueryDetailsParam) {
		Supplier<String> businessHandler = () ->{
			try {
				TbDatabase tbDatabase = tbDatabaseService.getById(modelQueryDetailsParam.getModelId());
				if(tbDatabase != null) {
					tbDatabase.setStatus(modelQueryDetailsParam.getStatus());
					tbDatabaseService.updateById(tbDatabase);
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
