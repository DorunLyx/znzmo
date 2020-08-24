package com.pactera.znzmo.vo.order;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：OrderListVO
 * @Description：订单列表VO
 * @author liyongxu 
 * @date 2020年8月10日 下午3:03:31 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="订单列表VO",description="订单列表VO")
public class OrderListVO {

	@ApiModelProperty(value="订单Id", name="orderId")
	private String orderId;

	@ApiModelProperty(value="编号", name="code")
    private String code;

	@ApiModelProperty(value="标题", name="title")
    private String title;
	
	@ApiModelProperty(value="价格", name="price")
	private BigDecimal price;

	@ApiModelProperty(value="类型", name="type")
    private Integer type;
	
	@ApiModelProperty(value="购买用户", name="userName")
	private String userName;

	@ApiModelProperty(value="用户Id", name="userId")
	private String userId;
	
	@ApiModelProperty(value="下单时间", name="orderTime")
	private String orderTime;
	
}
