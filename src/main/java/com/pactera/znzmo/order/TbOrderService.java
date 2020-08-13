package com.pactera.znzmo.order;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pactera.znzmo.vo.order.OrderAddParam;
import com.pactera.znzmo.vo.order.OrderDetailsParam;
import com.pactera.znzmo.vo.order.OrderDetailsVO;
import com.pactera.znzmo.vo.order.OrderQueryParam;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
public interface TbOrderService extends IService<TbOrder> {

	/**
	 * @Title: selectOrderPages 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param page
	 * @param orderQueryParam
	 * @return IPage<TbOrder>
	 * @author liyongxu
	 * @date 2020年8月10日 下午3:12:30 
	*/
	IPage<TbOrder> selectOrderPages(Page<TbOrder> page, OrderQueryParam orderQueryParam);

	/**
	 * @Title: addOrder 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param orderAddParam void
	 * @author liyongxu
	 * @date 2020年8月10日 下午3:22:53 
	*/
	void addOrder(OrderAddParam orderAddParam);

	/**
	 * @Title: getOrderInfo 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param orderDetailsParam
	 * @return OrderDetailsVO
	 * @author liyongxu
	 * @date 2020年8月10日 下午3:37:34 
	*/
	OrderDetailsVO getOrderInfo(OrderDetailsParam orderDetailsParam);

}
