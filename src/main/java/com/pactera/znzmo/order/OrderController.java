package com.pactera.znzmo.order;

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
import com.pactera.znzmo.vo.model.ModelQueryDetailsParam;
import com.pactera.znzmo.vo.order.OrderAddParam;
import com.pactera.znzmo.vo.order.OrderDetailsParam;
import com.pactera.znzmo.vo.order.OrderDetailsVO;
import com.pactera.znzmo.vo.order.OrderListVO;
import com.pactera.znzmo.vo.order.OrderQueryParam;
import com.pactera.znzmo.web.BaseController;
import com.pactera.znzmo.web.JsonResp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName：OrderController
 * @Description：订单管理
 * @author liyongxu 
 * @date 2020年8月10日 下午3:00:29 
 * @version 1.0.0 
 */
@Api(tags = "订单管理API", value = "订单管理API")
@RestController
@RequestMapping(value = "/order")
public class OrderController extends BaseController{
	
	@Autowired
	private TbOrderService tbOrderService;
	
	public static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	/**
	 * @Title: getOrderList
	 * @Description: 订单列表查询
	 * @param orderQueryParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月10日 下午3:11:51 
	*/
	@ApiOperation(value = "订单列表查询", httpMethod = "POST", notes = "订单列表查询")
    @RequestMapping(value = "/getOrderList", method = {RequestMethod.POST})
    public JsonResp getOrderList(
    		@ApiParam(name="orderQueryParam", value="订单列表筛选参数", required=false)@RequestBody OrderQueryParam orderQueryParam) {
		Supplier<IPage<OrderListVO>> businessHandler = () -> {
			try {
				List<OrderListVO> modelList = new ArrayList<OrderListVO>();
				Page<TbOrder> page = new Page<TbOrder>(orderQueryParam.getPageNo(), orderQueryParam.getPageSize());
		        IPage<OrderListVO> orderListPage =  new Page<OrderListVO>(orderQueryParam.getPageNo(), orderQueryParam.getPageSize());
		        IPage<TbOrder> iPage = tbOrderService.selectOrderPages(page, orderQueryParam);
				for (TbOrder tbOrder : iPage.getRecords()) {
					OrderListVO orderListVO = new OrderListVO();
					orderListVO.setOrderId(tbOrder.getId());
					orderListVO.setCode(tbOrder.getOrderCode());
					orderListVO.setTitle(tbOrder.getTitle());
					orderListVO.setType(tbOrder.getType());
					orderListVO.setPrice(tbOrder.getPrice());
					orderListVO.setUserId(tbOrder.getUserId());
					orderListVO.setUserName(tbOrder.getUserName());
					orderListVO.setOrderTime(tbOrder.getOrderTime());
					modelList.add(orderListVO);
	    		}
				orderListPage.setRecords(modelList);
				orderListPage.setCurrent(iPage.getCurrent());
				orderListPage.setPages(iPage.getPages());
				orderListPage.setSize(iPage.getSize());
				orderListPage.setTotal(iPage.getTotal());	
		        return orderListPage;
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }

	/**
	 * @Title: addOrder 
	 * @Description: 订单新增
	 * @param orderAddParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月10日 下午3:29:23 
	*/
	@ApiOperation(value = "订单新增", httpMethod = "POST", notes = "订单新增")
    @RequestMapping(value = "/addOrder", method = {RequestMethod.POST})
	public JsonResp addOrder(
			@ApiParam(name="orderAddParam", value="订单新增参数", required=false)@RequestBody OrderAddParam orderAddParam) {
		Supplier<String> businessHandler = () ->{
			try {
				tbOrderService.addOrder(orderAddParam);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
	}
	
	/**
	 * @Title: getOrderInfo 
	 * @Description: 订单详情
	 * @param orderDetailsParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月10日 下午3:29:59 
	*/
	@ApiOperation(value = "订单详情", httpMethod = "POST", notes = "订单详情")
    @RequestMapping(value = "/getOrderInfo", method = {RequestMethod.POST})
    public JsonResp getOrderInfo(
    		@ApiParam(name="orderDetailsParam", value="订单详情参数", required=false)@RequestBody OrderDetailsParam orderDetailsParam) {
		Supplier<OrderDetailsVO> businessHandler = () ->{
			try {
				return tbOrderService.getOrderInfo(orderDetailsParam);
			} catch (Exception e) {
				throwException(e);
			}
			return null;
		};
		return handleRequest(businessHandler);
    }
	
	/**
	 * @Title: updateOrderStatus 
	 * @Description: 变更状态-订单
	 * @param modelQueryDetailsParam
	 * @return JsonResp
	 * @author liyongxu
	 * @date 2020年8月10日 下午3:38:26 
	*/
	@ApiOperation(value = "变更状态-订单", httpMethod = "POST", notes = "变更状态-订单")
    @RequestMapping(value = "/updateOrderStatus", method = {RequestMethod.POST})
    public JsonResp updateOrderStatus(
    		@ApiParam(name="modelQueryDetailsParam", value="订单参数", required=false)@RequestBody ModelQueryDetailsParam modelQueryDetailsParam) {
		Supplier<String> businessHandler = () ->{
			try {
				TbOrder tbOrder = tbOrderService.getById(modelQueryDetailsParam.getModelId());
				tbOrder.setIsValid(modelQueryDetailsParam.getStatus());
				tbOrderService.updateById(tbOrder);
				return JsonResultEnum.ok.getValue();
			} catch (Exception e) {
				throwException(e);
				logger.error(e.getMessage(),e);
			}
			return JsonResultEnum.fail.getValue();
		};
		return handleRequest(businessHandler);
    }
	
}
