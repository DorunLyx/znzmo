package com.pactera.znzmo.classify;

import java.util.List;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pactera.znzmo.vo.profit.ProfitQueryParam;
import com.pactera.znzmo.web.BaseController;
import com.pactera.znzmo.web.JsonResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName：ClassController
 * @Description：分类管理API
 * @author liyongxu 
 * @date 2020年8月12日 下午5:01:08 
 * @version 1.0.0 
 */
@Api(tags = "分类管理API", value = "分类管理API")
@RestController
@RequestMapping(value = "/class")
public class ClassController extends BaseController{
	
	@Autowired
	private TbClassService tbClassService;
	
	public static final Logger logger = LoggerFactory.getLogger(ClassController.class);

	/**
	 * @Title: getClassList 
	 * @Description: 分类
	 * @param profitQueryParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月12日 下午5:02:53 
	*/
	@ApiOperation(value = "分类查询", httpMethod = "POST", notes = "分类查询")
    @RequestMapping(value = "/getClassList", method = {RequestMethod.POST})
    public JsonResp getClassList(
    		@ApiParam(name="profitQueryParam", value="收益统计列表筛选参数", required=false)@RequestBody ProfitQueryParam profitQueryParam) {
		Supplier<List<TbClass>> businessHandler = () -> {
			try {
		        return tbClassService.list();
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
}
