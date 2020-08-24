package com.pactera.znzmo.points;

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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pactera.znzmo.enums.JsonResultEnum;
import com.pactera.znzmo.util.DateUtils;
import com.pactera.znzmo.vo.points.PointsAddParam;
import com.pactera.znzmo.vo.points.PointsExchangeListVO;
import com.pactera.znzmo.vo.points.PointsQueryParam;
import com.pactera.znzmo.web.BaseController;
import com.pactera.znzmo.web.JsonResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName：PointsController
 * @Description：积分管理API 
 * @author liyongxu 
 * @date 2020年8月11日 上午10:04:24 
 * @version 1.0.0 
 */
@Api(tags = "积分管理API", value = "积分管理API")
@RestController
@RequestMapping(value = "/points")
public class PointsController extends BaseController{
	
	@Autowired
	private TbPointsService tbPointsService;
	
	@Autowired
	private TbPointsDetailsService tbPointsDetailsService;
	
	public static final Logger logger = LoggerFactory.getLogger(PointsController.class);

	/**
	 * @Title: getPointsExchangeList 
	 * @Description: 积分兑换列表查询
	 * @param pointsQueryParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月11日 上午10:47:37 
	*/
	@ApiOperation(value = "积分兑换列表查询", httpMethod = "POST", notes = "积分兑换列表查询")
    @RequestMapping(value = "/getPointsExchangeList", method = {RequestMethod.POST})
    public JsonResp getPointsExchangeList(
    		@ApiParam(name="pointsQueryParam", value="积分兑换列表筛选参数", required=false)@RequestBody PointsQueryParam pointsQueryParam) {
		Supplier<IPage<PointsExchangeListVO>> businessHandler = () -> {
			try {
				List<PointsExchangeListVO> pointsExchangeList = new ArrayList<PointsExchangeListVO>();
				Page<TbPointsDetails> page = new Page<TbPointsDetails>(pointsQueryParam.getPageNo(), pointsQueryParam.getPageSize());
		        IPage<PointsExchangeListVO> pointsExchangeListPage =  new Page<PointsExchangeListVO>(pointsQueryParam.getPageNo(), pointsQueryParam.getPageSize());
		        IPage<TbPointsDetails> iPage = tbPointsDetailsService.selectPointsExchangePages(page, pointsQueryParam);
				for (TbPointsDetails tbPointsDetails : iPage.getRecords()) {
					PointsExchangeListVO pointsExchangeListVO = new PointsExchangeListVO();
					pointsExchangeListVO.setPointsExchangeId(tbPointsDetails.getId().toString());
					pointsExchangeListVO.setUserId(tbPointsDetails.getUserId().toString());
					pointsExchangeListVO.setUserName(tbPointsDetails.getUserName());
					pointsExchangeListVO.setExchangePackage(tbPointsDetails.getExchangePackage());
					pointsExchangeListVO.setExchangeTime(DateUtils.localDateTimeToString(tbPointsDetails.getExchangeTime(), DateUtils.DATE_FORMAT));
					pointsExchangeListVO.setConsumePoints(tbPointsDetails.getConsumePoints());
					pointsExchangeListVO.setCurrentPoints(tbPointsDetails.getCurrentPoints());
					pointsExchangeList.add(pointsExchangeListVO);
	    		}
				pointsExchangeListPage.setRecords(pointsExchangeList);
				pointsExchangeListPage.setCurrent(iPage.getCurrent());
				pointsExchangeListPage.setPages(iPage.getPages());
				pointsExchangeListPage.setSize(iPage.getSize());
				pointsExchangeListPage.setTotal(iPage.getTotal());	
		        return pointsExchangeListPage;
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }

	/**
	 * @Title: getPointsManageList 
	 * @Description: 积分管理列表查询
	 * @param pointsQueryParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月11日 上午11:05:39 
	*/
	@ApiOperation(value = "积分管理列表查询", httpMethod = "POST", notes = "积分管理列表查询")
    @RequestMapping(value = "/getPointsManageList", method = {RequestMethod.POST})
    public JsonResp getPointsManageList(
    		@ApiParam(name="pointsQueryParam", value="积分兑换列表筛选参数", required=false)@RequestBody PointsQueryParam pointsQueryParam) {
		Supplier<IPage<PointsExchangeListVO>> businessHandler = () -> {
			try {
				List<PointsExchangeListVO> pointsExchangeList = new ArrayList<PointsExchangeListVO>();
				Page<TbPoints> page = new Page<TbPoints>(pointsQueryParam.getPageNo(), pointsQueryParam.getPageSize());
		        IPage<PointsExchangeListVO> pointsExchangeListPage =  new Page<PointsExchangeListVO>(pointsQueryParam.getPageNo(), pointsQueryParam.getPageSize());
		        IPage<TbPoints> iPage = tbPointsService.selectPointsManagePages(page, pointsQueryParam);
				for (TbPoints tbPoints : iPage.getRecords()) {
					PointsExchangeListVO pointsExchangeListVO = new PointsExchangeListVO();
					pointsExchangeListVO.setPointsExchangeId(tbPoints.getId().toString());
					pointsExchangeListVO.setUserId(tbPoints.getUserId().toString());
					pointsExchangeListVO.setUserName(tbPoints.getUserName());
					pointsExchangeListVO.setConsumePoints(tbPoints.getConsumePoints());
					pointsExchangeListVO.setTotalPoints(tbPoints.getTotalPoints());
					pointsExchangeListVO.setCurrentPoints(tbPoints.getCurrentPoints());
					pointsExchangeList.add(pointsExchangeListVO);
	    		}
				pointsExchangeListPage.setRecords(pointsExchangeList);
				pointsExchangeListPage.setCurrent(iPage.getCurrent());
				pointsExchangeListPage.setPages(iPage.getPages());
				pointsExchangeListPage.setSize(iPage.getSize());
				pointsExchangeListPage.setTotal(iPage.getTotal());	
		        return pointsExchangeListPage;
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: addPoints
	 * @Description: 积分新增
	 * @param pointsAddParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月10日 下午3:29:23 
	*/
	@ApiOperation(value = "积分新增", httpMethod = "POST", notes = "积分新增")
    @RequestMapping(value = "/addPoints", method = {RequestMethod.POST})
	public JsonResp addPoints(
			@ApiParam(name="pointsAddParam", value="订单新增参数", required=false)@RequestBody PointsAddParam pointsAddParam) {
		Supplier<String> businessHandler = () ->{
			try {
				tbPointsService.addPoints(pointsAddParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
	
}
