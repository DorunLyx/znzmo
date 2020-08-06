/**
 * 
 */
package com.pactera.znzmo.banner;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pactera.znzmo.vo.HomePageListVO;
import com.pactera.znzmo.vo.HomePageQueryParam;
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
	
	public static final Logger logger = LoggerFactory.getLogger(BannerController.class);

	/**
	 * @Title: getHomeList 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param homePageQueryParam
	 * @return JsonResult<IPage<HomePageListVO>>
	 * @author liyongxu
	 * @date 2020年8月4日 上午11:35:42 
	*/
	@ApiOperation(value = "首页查询", httpMethod = "POST", notes = "首页查询")
    @RequestMapping(value = "/getHomeList", method = {RequestMethod.POST})
    public JsonResp getHomeList(
    		@ApiParam(name="assetListParam", value="资产列表筛选参数", required=false)@RequestBody HomePageQueryParam homePageQueryParam) {
		Supplier<IPage<HomePageListVO>> businessHandler = () ->{
			try {
				IPage<HomePageListVO> iPage = tbBannerService.selectHomePages(homePageQueryParam);
				return iPage;
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }

}
