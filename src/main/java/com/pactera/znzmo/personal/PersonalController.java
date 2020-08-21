/**
 * 
 */
package com.pactera.znzmo.personal;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pactera.znzmo.sysuser.TbUserAssets;
import com.pactera.znzmo.sysuser.TbUserAssetsService;
import com.pactera.znzmo.web.BaseController;
import com.pactera.znzmo.web.JsonResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName：PersonalController
 * @Description：个人中心API
 * @author liyongxu 
 * @date 2020年8月21日 上午11:30:39 
 * @version 1.0.0 
 */
@Api(tags = "个人中心API", value = "个人中心API")
@RestController
@RequestMapping(value = "/personal")
public class PersonalController extends BaseController{
	
	@Autowired
	private TbUserAssetsService tbUserAssetsService;
	
	public static final Logger logger = LoggerFactory.getLogger(PersonalController.class);

	/**
	 * @Title: getUserAssets 
	 * @Description: 我的资产查询
	 * @param userId
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月21日 下午2:02:37 
	*/
	@ApiOperation(value = "我的资产查询", httpMethod = "POST", notes = "我的资产查询")
    @RequestMapping(value = "/getUserAssets", method = {RequestMethod.POST})
    public JsonResp getUserAssets(
    		@ApiParam(name="userId", value="用户id", required=false)@RequestParam Long userId) {
		Supplier<TbUserAssets> businessHandler = () ->{
			try {
				QueryWrapper<TbUserAssets> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq(TbUserAssets.USER_ID, userId);
				TbUserAssets tbUserAssets = tbUserAssetsService.getOne(queryWrapper);
				return tbUserAssets;
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
}
