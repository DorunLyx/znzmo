/**
 * 
 */
package com.pactera.znzmo.banner;

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
import com.pactera.znzmo.drawing.TbDrawingScheme;
import com.pactera.znzmo.drawing.TbDrawingSchemeService;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.enums.JsonResultEnum;
import com.pactera.znzmo.enums.ReTypeEnum;
import com.pactera.znzmo.hd.TbHdMapping;
import com.pactera.znzmo.hd.TbHdMappingService;
import com.pactera.znzmo.model.TbThreedModel;
import com.pactera.znzmo.model.TbThreedModelService;
import com.pactera.znzmo.sumodel.TbSuModel;
import com.pactera.znzmo.sumodel.TbSuModelService;
import com.pactera.znzmo.util.DateUtils;
import com.pactera.znzmo.util.StringUtils;
import com.pactera.znzmo.vo.banner.BannerAddParam;
import com.pactera.znzmo.vo.banner.BannerDetailsVO;
import com.pactera.znzmo.vo.banner.BannerListVO;
import com.pactera.znzmo.vo.banner.BannerQueryParam;
import com.pactera.znzmo.vo.banner.BannerUpdateParam;
import com.pactera.znzmo.vo.banner.ModularBannerQueryParam;
import com.pactera.znzmo.vo.homepage.HomePageBannerData;
import com.pactera.znzmo.vo.homepage.HomePageDrawingData;
import com.pactera.znzmo.vo.homepage.HomePageHDData;
import com.pactera.znzmo.vo.homepage.HomePageListVO;
import com.pactera.znzmo.vo.homepage.HomePageModelData;
import com.pactera.znzmo.vo.homepage.HomePageOverviewData;
import com.pactera.znzmo.vo.homepage.HomePageSimplifyData;
import com.pactera.znzmo.vo.homepage.HomePageSuModelData;
import com.pactera.znzmo.vo.model.ModelQueryDetailsParam;
import com.pactera.znzmo.web.BaseController;
import com.pactera.znzmo.web.JsonResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName：BannerController
 * @Description：首页及展示管理
 * @author liyongxu 
 * @date 2020年8月4日 上午10:58:55 
 * @version 1.0.0 
 */
@Api(tags = "首页及展示管理API", value = "首页及展示管理API")
@RestController
@RequestMapping(value = "/banner")
public class BannerController extends BaseController{
	
	@Autowired
	private TbBannerService tbBannerService;
	
	@Autowired
	private TbThreedModelService tbThreedModelService;
	
	@Autowired
	private TbSuModelService tbSuModelService;
	
	@Autowired
	private TbHdMappingService tbHdMappingService;

	@Autowired
	private TbDrawingSchemeService tbDrawingSchemeService;
	
	@Autowired
	private TbAttachmentService tbAttachmentService;
	
	public static final Logger logger = LoggerFactory.getLogger(BannerController.class);

	/**
	 * @Title: getHomePageList 
	 * @Description: 首页查询查询)
	 * @param bannerQueryParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月12日 上午11:31:34 
	*/
	@ApiOperation(value = "首页查询", httpMethod = "POST", notes = "首页查询")
    @RequestMapping(value = "/getHomePageList", method = {RequestMethod.POST})
    public JsonResp getHomePageList() {
		Supplier<HomePageListVO> businessHandler = () ->{
			try {
				HomePageListVO homePageListVO = new HomePageListVO();
				//首页轮播
				List<HomePageBannerData> homePageBannerDataList = getBannerData(null);
				//首页总览数据
				HomePageOverviewData homePageOverviewData = getOverviewData();
				//首页3d模型数据
				HomePageModelData homePageModelData = get3dModelData();
				//首页Su模型展示
				HomePageSuModelData homePageSuModelData = getSuModelData();
				//首页图纸方案展示
				HomePageDrawingData homePageDrawingData= getDrawingData();
				//首页高清贴图展示
				HomePageHDData  homePageHDData = getHdData();
				homePageListVO.setHomePageBannerData(homePageBannerDataList);
				homePageListVO.setHomePageOverviewData(homePageOverviewData);
				homePageListVO.setHomePageModelData(homePageModelData);
				homePageListVO.setHomePageSuModelData(homePageSuModelData);
				homePageListVO.setHomePageDrawingData(homePageDrawingData);
				homePageListVO.setHomePageHDData(homePageHDData);
				return homePageListVO;
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }

	/**
	 * @Title: getBannerData 
	 * @Description: 首页轮播
	 * @return List<HomePageBannerData>
	 * @author liyongxu
	 * @date 2020年8月12日 下午3:41:04 
	*/
	private List<HomePageBannerData> getBannerData(String type) {
		List<HomePageBannerData> homePageBannerDataList = new ArrayList<>();
		QueryWrapper<TbBanner> queryWrapper = new QueryWrapper<TbBanner>();
		if(StringUtils.isNotEmpty(type)) {
			queryWrapper.eq(TbBanner.TYPE,type);
		}
		queryWrapper.orderByDesc(TbBanner.START_TIME)
		        .last("limit 1,10");
		List<TbBanner> tbBannerList = tbBannerService.list(queryWrapper);
		for (TbBanner tbBanner : tbBannerList) {
			HomePageBannerData homePageBannerData = new HomePageBannerData();
			TbAttachment attachmentList = tbAttachmentService.getById(tbBanner.getAttachmentId());
			homePageBannerData.setMainGraph(attachmentList.getAttachmentPath());
			homePageBannerData.setJumpLink(tbBanner.getJumpLink());
			homePageBannerData.setSort(tbBanner.getSort());
			homePageBannerData.setStartTime(tbBanner.getStartTime());
			homePageBannerData.setEndTime(tbBanner.getEndTime());
			homePageBannerDataList.add(homePageBannerData);
		}
		return homePageBannerDataList;
	}
	
	/**
	 * @Title: getOverviewData 
	 * @Description: 首页总览数据
	 * @return List<HomePageOverviewData>
	 * @author liyongxu
	 * @date 2020年8月19日 上午10:46:43 
	*/
	private HomePageOverviewData getOverviewData() {
		HomePageOverviewData homePageOverviewData = new HomePageOverviewData();
		homePageOverviewData.setModelData(get3dModelList(null));
		homePageOverviewData.setSuModelData(getSuModelList(null));
		homePageOverviewData.setDrawingData(getDrawingList(null));
		homePageOverviewData.setHdData(getHdList(null));
		return homePageOverviewData;
	}
	
	/**
	 * @Title: get3dModelData 
	 * @Description: 3d模型首页数据
	 * @return HomePageModelData
	 * @author liyongxu
	 * @date 2020年8月19日 上午10:58:39 
	*/
	private HomePageModelData get3dModelData() {
		HomePageModelData homePageModelData = new HomePageModelData();
		homePageModelData.setAllData(get3dModelList(null));
		homePageModelData.setLivingRoomData(get3dModelList(null));
		homePageModelData.setCeilingLampData(get3dModelList(null));
		homePageModelData.setBedclothesData(get3dModelList(null));
		homePageModelData.setModelFourData(get3dModelList(null));
		homePageModelData.setCabinetData(get3dModelList(null));
		homePageModelData.setFurnishingsData(get3dModelList(null));
		homePageModelData.setTeahouseStudyData(get3dModelList(null));
		return homePageModelData;
	}
	
	/**
	 * @Title: getSuModelData 
	 * @Description: su模型数据
	 * @return HomePageSuModelData
	 * @author liyongxu
	 * @date 2020年8月19日 上午11:12:19 
	*/
	private HomePageSuModelData getSuModelData() {
		HomePageSuModelData homePageSuModelData = new HomePageSuModelData();
		homePageSuModelData.setLivingRoomData(getSuModelList(null));
		homePageSuModelData.setSofatableData(getSuModelList(null));
		homePageSuModelData.setCommercialStreetData(getSuModelList(null));
		homePageSuModelData.setLandscapeHousingData(getSuModelList(null));
		homePageSuModelData.setLandscapeSketchData(getSuModelList(null));
		return homePageSuModelData;
	}
	
	/**
	 * @Title: getDrawingData 
	 * @Description: 图纸方案数据
	 * @return HomePageDrawingData
	 * @author liyongxu
	 * @date 2020年8月19日 上午11:16:58 
	*/
	private HomePageDrawingData getDrawingData() {
		HomePageDrawingData homePageDrawingData = new HomePageDrawingData();
		homePageDrawingData.setSpaceData(getDrawingList(null));
		homePageDrawingData.setHomeDesignData(getDrawingList(null));
		return homePageDrawingData;
	}

	/**
	 * @Title: getHdData 
	 * @Description: 高清贴图
	 * @return HomePageHDData
	 * @author liyongxu
	 * @date 2020年8月19日 上午11:17:01 
	*/
	private HomePageHDData getHdData() {
		HomePageHDData homePageHDData = new HomePageHDData();
		homePageHDData.setMappingData(getHdList(null));
		return homePageHDData;
	}
	
	/**
	 * @Title: get3dModelList 
	 * @Description: 3d模型
	 * @return List<HomePageSimplifyData>
	 * @author liyongxu
	 * @date 2020年8月12日 下午3:59:18 
	*/
	private List<HomePageSimplifyData> get3dModelList(String classify) {
		List<HomePageSimplifyData> homePageModelDataList = new ArrayList<>();
		QueryWrapper<TbThreedModel> queryWrapper = new QueryWrapper<TbThreedModel>();
		if(StringUtils.isNotEmpty(classify)) {
			queryWrapper.eq(TbThreedModel.PRIMARY_CLASS_ID, classify);
		}
		queryWrapper.orderByDesc(TbThreedModel.UPDATE_TIME)
			.last("limit 1,10");
		List<TbThreedModel> tb3dModelList = tbThreedModelService.list(queryWrapper);
		for (TbThreedModel tbThreedModel : tb3dModelList) {
			HomePageSimplifyData homePageSimplifyData = new HomePageSimplifyData();
			homePageSimplifyData.setReId(tbThreedModel.getId().toString());
			homePageSimplifyData.setReType(ReTypeEnum.MODEL.getKey());
			TbAttachment tbAttachment = tbAttachmentService.getById(tbThreedModel.getMainGraph());
	        if(tbAttachment != null) {
	        	homePageSimplifyData.setMainGraph(tbAttachment.getAttachmentPath());
	        }
			homePageSimplifyData.setTitle(tbThreedModel.getTitle());
			homePageSimplifyData.setPrice(tbThreedModel.getPrice());
			homePageSimplifyData.setType(tbThreedModel.getType());
			homePageModelDataList.add(homePageSimplifyData);
		}
		return homePageModelDataList;
	}
	
	/**
	 * @Title: getSuModelList
	 * @Description: su模型
	 * @return List<HomePageSimplifyData>
	 * @author liyongxu
	 * @date 2020年8月12日 下午4:04:02 
	*/
	private List<HomePageSimplifyData> getSuModelList(String classify) {
		List<HomePageSimplifyData> homePageSuModelDataList = new ArrayList<>();
		QueryWrapper<TbSuModel> queryWrapper = new QueryWrapper<TbSuModel>();
		if(StringUtils.isNotEmpty(classify)) {
			queryWrapper.eq(TbSuModel.PRIMARY_CLASS_ID, classify);
		}
		queryWrapper.orderByDesc(TbSuModel.UPDATE_TIME)
		        .last("limit 1,10");
		List<TbSuModel> tbSuModelList = tbSuModelService.list(queryWrapper);
		for (TbSuModel tbSuModel : tbSuModelList) {
			HomePageSimplifyData homePageSimplifyData = new HomePageSimplifyData();
			homePageSimplifyData.setReId(tbSuModel.getId().toString());
			homePageSimplifyData.setReType(ReTypeEnum.SUMODEL.getKey());
			TbAttachment tbAttachment = tbAttachmentService.getById(tbSuModel.getMainGraph());
	        if(tbAttachment != null) {
	        	homePageSimplifyData.setMainGraph(tbAttachment.getAttachmentPath());
	        }
			homePageSimplifyData.setTitle(tbSuModel.getTitle());
			homePageSimplifyData.setPrice(tbSuModel.getPrice());
			homePageSimplifyData.setType(tbSuModel.getType());
			homePageSuModelDataList.add(homePageSimplifyData);
		}
		return homePageSuModelDataList;
	}
	
	/**
	 * @Title: getDrawingList
	 * @Description: 图纸方案
	 * @return List<HomePageSimplifyData>
	 * @author liyongxu
	 * @date 2020年8月12日 下午4:12:14 
	*/
	private List<HomePageSimplifyData> getDrawingList(String classify) {
		List<HomePageSimplifyData> homePageDrawingDataList = new ArrayList<>();
		QueryWrapper<TbDrawingScheme> queryWrapper = new QueryWrapper<TbDrawingScheme>();
		if(StringUtils.isNotEmpty(classify)) {
			queryWrapper.eq(TbDrawingScheme.PRIMARY_CLASS_ID, classify);
		}
		queryWrapper.orderByDesc(TbDrawingScheme.UPDATE_TIME)
		        .last("limit 1,10");
		List<TbDrawingScheme> tbDrawingList = tbDrawingSchemeService.list(queryWrapper);
		for (TbDrawingScheme tbDrawingScheme : tbDrawingList) {
			HomePageSimplifyData homePageSimplifyData = new HomePageSimplifyData();
			homePageSimplifyData.setReId(tbDrawingScheme.getId().toString());
			homePageSimplifyData.setReType(ReTypeEnum.SUMODEL.getKey());
			TbAttachment tbAttachment = tbAttachmentService.getById(tbDrawingScheme.getMainGraph());
	        if(tbAttachment != null) {
	        	homePageSimplifyData.setMainGraph(tbAttachment.getAttachmentPath());
	        }
			homePageSimplifyData.setTitle(tbDrawingScheme.getTitle());
			homePageSimplifyData.setPrice(tbDrawingScheme.getPrice());
			homePageSimplifyData.setType(tbDrawingScheme.getType());
			homePageDrawingDataList.add(homePageSimplifyData);
		}
		return homePageDrawingDataList;
	}

	/**
	 * @Title: getHdList
	 * @Description: 高清贴图
	 * @return List<HomePageSimplifyData>
	 * @author liyongxu
	 * @date 2020年8月12日 下午4:09:05 
	*/
	private List<HomePageSimplifyData> getHdList(String classify) {
		List<HomePageSimplifyData> homePageHDDataList = new ArrayList<>();
		QueryWrapper<TbHdMapping> queryWrapper = new QueryWrapper<TbHdMapping>();
		if(StringUtils.isNotEmpty(classify)) {
			queryWrapper.eq(TbHdMapping.PRIMARY_CLASS_ID, classify);
		}
		queryWrapper.orderByDesc(TbHdMapping.UPDATE_TIME)
		        .last("limit 1,10");
		List<TbHdMapping> tbHdList = tbHdMappingService.list(queryWrapper);
		for (TbHdMapping tbHdMapping : tbHdList) {
			HomePageSimplifyData homePageSimplifyData = new HomePageSimplifyData();
			homePageSimplifyData.setReId(tbHdMapping.getId().toString());
			homePageSimplifyData.setReType(ReTypeEnum.HD.getKey());
			TbAttachment tbAttachment = tbAttachmentService.getById(tbHdMapping.getMainGraph());
	        if(tbAttachment != null) {
	        	homePageSimplifyData.setMainGraph(tbAttachment.getAttachmentPath());
	        }
			homePageSimplifyData.setTitle(tbHdMapping.getTitle());
			homePageHDDataList.add(homePageSimplifyData);
		}
		return homePageHDDataList;
	}
	
	/**
	 * @Title: getModularBannerList 
	 * @Description: 模块轮播查询
	 * @param modularBannerQueryParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月19日 上午11:48:53 
	*/
	@ApiOperation(value = "模块轮播查询", httpMethod = "POST", notes = "模块轮播查询")
    @RequestMapping(value = "/getModularBannerList", method = {RequestMethod.POST})
    public JsonResp getModularBannerList(
    		@ApiParam(name="bannerQueryParam", value="首页列表筛选参数", required=false)@RequestBody ModularBannerQueryParam modularBannerQueryParam) {
		Supplier<List<HomePageBannerData>> businessHandler = () ->{
			try {
				return getBannerData(modularBannerQueryParam.getType().toString());
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: getBannerList 
	 * @Description: 轮播管理查询
	 * @param bannerQueryParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月7日 下午5:12:52 
	*/
	@ApiOperation(value = "轮播管理查询", httpMethod = "POST", notes = "轮播管理查询")
    @RequestMapping(value = "/getBannerList", method = {RequestMethod.POST})
    public JsonResp getBannerList(
    		@ApiParam(name="bannerQueryParam", value="首页列表筛选参数", required=false)@RequestBody BannerQueryParam bannerQueryParam) {
		Supplier<IPage<BannerListVO>> businessHandler = () ->{
			try {
				List<BannerListVO> bannerList = new ArrayList<BannerListVO>();
				Page<TbBanner> page = new Page<TbBanner>(bannerQueryParam.getPageNo(), bannerQueryParam.getPageSize());
				IPage<BannerListVO> modeListPage =  new Page<BannerListVO>(bannerQueryParam.getPageNo(), bannerQueryParam.getPageSize());
				IPage<TbBanner> iPage = tbBannerService.selectBannerPages(page, bannerQueryParam);
				for (TbBanner tbBanner : iPage.getRecords()) {
					BannerListVO bannerListVO = new BannerListVO();
					bannerListVO.setBannerId(tbBanner.getId().toString());
					bannerListVO.setStatus(tbBanner.getStatus());
					bannerListVO.setSort(tbBanner.getSort());
					bannerListVO.setType(tbBanner.getType());
					bannerListVO.setStartTime(DateUtils.localDateTimeToString(tbBanner.getStartTime(), DateUtils.DATE_FORMAT));
					bannerListVO.setEndTime(DateUtils.localDateTimeToString(tbBanner.getEndTime(), DateUtils.DATE_FORMAT));
			        TbAttachment attachmentList = tbAttachmentService.getById(tbBanner.getAttachmentId());
					bannerListVO.setMainGraph(attachmentList.getAttachmentPath());
					bannerList.add(bannerListVO);
	    		}
				modeListPage.setRecords(bannerList);
				modeListPage.setCurrent(iPage.getCurrent());
				modeListPage.setPages(iPage.getPages());
				modeListPage.setSize(iPage.getSize());
				modeListPage.setTotal(iPage.getTotal());			
				return modeListPage;
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }

	/**
	 * @Title: addBanner 
	 * @Description: 轮播新增
	 * @param bannerAddParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月7日 下午5:14:20 
	*/
	@ApiOperation(value = "轮播新增", httpMethod = "POST", notes = "HD贴图新增")
    @RequestMapping(value = "/addBanner", method = {RequestMethod.POST})
	public JsonResp addBanner(
			@ApiParam(name="bannerAddParam", value="轮播新增参数", required=false)@RequestBody BannerAddParam bannerAddParam) {
		Supplier<String> businessHandler = () ->{
			try {
				tbBannerService.addBanner(bannerAddParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}

	/**
	 * @Title: getBannerInfo 
	 * @Description: 轮播详情
	 * @param modelQueryDetailsParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月7日 下午5:27:21 
	*/
	@ApiOperation(value = "轮播详情", httpMethod = "POST", notes = "轮播详情")
    @RequestMapping(value = "/getBannerInfo", method = {RequestMethod.POST})
    public JsonResp getBannerInfo(
    		@ApiParam(name="modelQueryDetailsParam", value="轮播详情参数", required=false)@RequestBody ModelQueryDetailsParam modelQueryDetailsParam) {
		Supplier<BannerDetailsVO> businessHandler = () ->{
			try {
				QueryWrapper<TbBanner> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq(TbBanner.IS_VALID, IsValidEnum.YES.getKey())
		        	.eq(TbBanner.ID, modelQueryDetailsParam.getModelId());
				TbBanner tbBanner = tbBannerService.getOne(queryWrapper);
				if(tbBanner != null) {
					BannerDetailsVO bannerDetailsVO = new BannerDetailsVO();
					bannerDetailsVO.setBannerId(tbBanner.getId());
					bannerDetailsVO.setTitle(tbBanner.getTitle());
					bannerDetailsVO.setSort(tbBanner.getSort());
					bannerDetailsVO.setType(tbBanner.getType());
					bannerDetailsVO.setStartTime(DateUtils.localDateTimeToString(tbBanner.getStartTime(), DateUtils.DATE_FORMAT));
					bannerDetailsVO.setEndTime(DateUtils.localDateTimeToString(tbBanner.getEndTime(), DateUtils.DATE_FORMAT));
					TbAttachment attachmentList = tbAttachmentService.getById(tbBanner.getAttachmentId());
					bannerDetailsVO.setMainGraph(attachmentList.getAttachmentPath());
					return bannerDetailsVO;
				}
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: updteBanner 
	 * @Description: 轮播编辑
	 * @param bannerUpdateParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月7日 下午5:29:48 
	*/
	@ApiOperation(value = "轮播编辑", httpMethod = "POST", notes = "轮播编辑")
	@RequestMapping(value = "/updteBanner", method = {RequestMethod.POST})
	public JsonResp updteBanner(
			@ApiParam(name="bannerUpdateParam", value="轮播编辑参数", required=false)@RequestBody BannerUpdateParam bannerUpdateParam) {
		Supplier<String> businessHandler = () ->{
			try {
				tbBannerService.updteBanner(bannerUpdateParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
	
	/**
	 * @Title: updateBannerStatus 
	 * @Description: 变更状态-轮播
	 * @param modelQueryDetailsParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月7日 下午5:15:41 
	*/
	@ApiOperation(value = "变更状态-轮播", httpMethod = "POST", notes = "变更状态-轮播")
    @RequestMapping(value = "/updateBannerStatus", method = {RequestMethod.POST})
    public JsonResp updateBannerStatus(
    		@ApiParam(name="modelQueryDetailsParam", value="轮播详情参数", required=false)@RequestBody ModelQueryDetailsParam modelQueryDetailsParam) {
		Supplier<String> businessHandler = () ->{
			try {
				TbBanner tbBanner = tbBannerService.getById(modelQueryDetailsParam.getModelId());
				if(tbBanner != null) {
					tbBanner.setStatus(modelQueryDetailsParam.getStatus());
					tbBannerService.updateById(tbBanner);
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
