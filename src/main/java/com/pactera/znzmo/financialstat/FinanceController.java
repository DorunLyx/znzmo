package com.pactera.znzmo.financialstat;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pactera.znzmo.enums.IsValidEnum;
import com.pactera.znzmo.vo.ProfitQueryParam;
import com.pactera.znzmo.web.BaseController;
import com.pactera.znzmo.web.JsonResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName：ProfitController
 * @Description：收益管理API 
 * @author liyongxu 
 * @date 2020年8月11日 下午3:21:56 
 * @version 1.0.0 
 */
@Api(tags = "收益管理API", value = "收益管理API")
@RestController
@RequestMapping(value = "/finance")
public class FinanceController extends BaseController{
	
	@Autowired
	private TbFinancialStatisticsService tbFinancialStatisticsService;
	
	public static final Logger logger = LoggerFactory.getLogger(FinanceController.class);

	@ApiOperation(value = "收益统计列表查询", httpMethod = "POST", notes = "收益统计列表查询")
    @RequestMapping(value = "/getFinanceManageList", method = {RequestMethod.POST})
    public JsonResp getFinanceManageList(
    		@ApiParam(name="profitQueryParam", value="收益统计列表筛选参数", required=false)@RequestBody ProfitQueryParam profitQueryParam) {
		Supplier<TbFinancialStatistics> businessHandler = () -> {
			try {
				QueryWrapper<TbFinancialStatistics> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq(TbFinancialStatistics.IS_VALID, IsValidEnum.YES.getKey());
		        return tbFinancialStatisticsService.getOne(queryWrapper);
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
}
