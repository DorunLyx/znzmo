package com.pactera.znzmo.vo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：OrderDetailsParam
 * @Description：订单详情参数
 * @author liyongxu 
 * @date 2020年8月10日 上午11:23:48 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="订单详情参数",description="3订单详情参数")
public class OrderDetailsParam {

	@ApiModelProperty(value="订单id", name="orderId")
    private Long orderId;
	
}
