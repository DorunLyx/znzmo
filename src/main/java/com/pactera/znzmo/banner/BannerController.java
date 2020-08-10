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
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.enums.JsonResultEnum;
import com.pactera.znzmo.vo.BannerAddParam;
import com.pactera.znzmo.vo.BannerDetailsVO;
import com.pactera.znzmo.vo.BannerListVO;
import com.pactera.znzmo.vo.BannerQueryParam;
import com.pactera.znzmo.vo.BannerUpdateParam;
import com.pactera.znzmo.vo.ModelQueryDetailsParam;
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
	private TbAttachmentService tbAttachmentService;
	
	public static final Logger logger = LoggerFactory.getLogger(BannerController.class);

	/**
	 * @Title: getBannerList 
	 * @Description: 轮播管理查询
	 * @param bannerQueryParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月7日 下午5:12:52 
	*/
	@ApiOperation(value = "轮播管理查询", httpMethod = "POST", notes = "首页查询")
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
					bannerListVO.setBannerId(tbBanner.getId());
					bannerListVO.setStatus(tbBanner.getStatus());
					bannerListVO.setSort(tbBanner.getSort());
					bannerListVO.setType(tbBanner.getType());
					bannerListVO.setStartTime(tbBanner.getStartTime());
					bannerListVO.setEndTime(tbBanner.getEndTime());
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
				BannerDetailsVO bannerDetailsVO = new BannerDetailsVO();
				bannerDetailsVO.setBannerId(tbBanner.getId());
				bannerDetailsVO.setTitle(tbBanner.getTitle());
				bannerDetailsVO.setSort(tbBanner.getSort());
				bannerDetailsVO.setType(tbBanner.getType());
				bannerDetailsVO.setStartTime(tbBanner.getStartTime());
				bannerDetailsVO.setEndTime(tbBanner.getEndTime());
				TbAttachment attachmentList = tbAttachmentService.getById(tbBanner.getAttachmentId());
				bannerDetailsVO.setMainGraph(attachmentList.getAttachmentPath());
				return bannerDetailsVO;
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
				tbBanner.setStatus(modelQueryDetailsParam.getStatus());
				tbBannerService.updateById(tbBanner);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
	
}
