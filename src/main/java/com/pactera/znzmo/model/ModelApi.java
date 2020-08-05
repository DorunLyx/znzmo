/**
 * 
 */
package com.pactera.znzmo.model;

import java.util.ArrayList;
import java.util.List;

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
import com.pactera.znzmo.util.JsonResult;
import com.pactera.znzmo.vo.ModelListVO;
import com.pactera.znzmo.vo.ModelQueryParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName：BannerController
 * @Description：3D模型
 * @author liyongxu 
 * @date 2020年8月4日 上午10:58:55 
 * @version 1.0.0 
 */
@Api(tags = "3D模型API", value = "3D模型API")
@RestController
@RequestMapping(value = "/api/3dmodel")
public class ModelApi {
	
	@Autowired
	private Tb3dModelService tb3dModelService;
	
	public static final Logger logger = LoggerFactory.getLogger(ModelApi.class);

	/**
	 * @Title: get3DModelList 
	 * @Description: 3D模型查询
	 * @param homePageQueryParam
	 * @return JsonResult<IPage<HomePageListVO>>
	 * @author liyongxu
	 * @date 2020年8月4日 上午11:35:35 
	*/
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "3D模型查询", httpMethod = "POST", notes = "3D模型查询")
    @RequestMapping(value = "/get3DModelList", method = {RequestMethod.POST})
    public JsonResult<IPage<ModelListVO>> get3DModelList(
    		@ApiParam(name="modelQueryParam", value="3d模型列表筛选参数", required=false)@RequestBody ModelQueryParam modelQueryParam) {
		List<ModelListVO> modelList = new ArrayList<ModelListVO>();
		Page<Tb3dModel> page = new Page<Tb3dModel>(modelQueryParam.getPageNo(), modelQueryParam.getPageSize());
        IPage<ModelListVO> modeListPage =  new Page<ModelListVO>(modelQueryParam.getPageNo(), modelQueryParam.getPageSize());
		try {
			IPage<Tb3dModel> iPage = tb3dModelService.select3DModelPages(page, modelQueryParam);
			for (Tb3dModel tb3dModel : iPage.getRecords()) {
				ModelListVO modelListVO = new ModelListVO();
				modelListVO.setModelId(tb3dModel.getId());
				modelListVO.setMainGraph(tb3dModel.getMainGraph());
				modelListVO.setCode(tb3dModel.getCode());
				modelListVO.setPrimaryClassId(tb3dModel.getPrimaryClassId());
				modelListVO.setPrimaryClassName(tb3dModel.getPrimaryClassName());
				modelListVO.setSecondaryClassId(tb3dModel.getSecondaryClassId());
				modelListVO.setSecondaryClassName(tb3dModel.getSecondaryClassName());
				modelListVO.setStyleId(tb3dModel.getStyleId());
				modelListVO.setStyleName(tb3dModel.getStyleName());
				modelListVO.setTitle(tb3dModel.getTitle());
				modelListVO.setModelType(tb3dModel.getModelType());
				modelListVO.setModelPrice(tb3dModel.getModelPrice());
				modelListVO.setTextureMapping(tb3dModel.getTextureMapping());
				modelListVO.setLightingEffects(tb3dModel.getLightingEffects());
				modelListVO.setStatus(tb3dModel.getStatus());
				modelListVO.setVisitsNum(tb3dModel.getVisitsNum());
				modelListVO.setDownloadNum(tb3dModel.getDownloadNum());
				modelList.add(modelListVO);
    		}
			modeListPage.setRecords(modelList);
			modeListPage.setCurrent(iPage.getCurrent());
			modeListPage.setPages(iPage.getPages());
			modeListPage.setSize(iPage.getSize());
			modeListPage.setTotal(iPage.getTotal());			
			return JsonResult.ok(modeListPage);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JsonResult.fail(String.valueOf(JsonResultEnum.fail.getKey()), JsonResultEnum.fail.getValue());
		}
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
	public JsonResult add3DModel(
			@ApiParam(name="assetListParam", value="资产列表筛选参数", required=false)@RequestBody ModelQueryParam modelQueryParam) {
		try {
			return JsonResult.ok();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JsonResult.fail(String.valueOf(JsonResultEnum.fail.getKey()), JsonResultEnum.fail.getValue());
		}
	}
}
