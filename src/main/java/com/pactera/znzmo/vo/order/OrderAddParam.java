package com.pactera.znzmo.vo.order;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：OrderAddParam
 * @Description：订单新增参数
 * @author liyongxu 
 * @date 2020年8月10日 下午3:19:47 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="订单新增参数",description="订单新增参数")
public class OrderAddParam {

	@ApiModelProperty(value="业务id", name="reId")
	private String reId;
	
	@ApiModelProperty(value="业务类型", name="type")
	private Integer type;
	
	@ApiModelProperty(value="用户id", name="userId")
	private Long userId;
	
	@ApiModelProperty(value="用户名称", name="userName")
	private String userName;

	@ApiModelProperty(value="下单时间", name="orderTime")
    private LocalDateTime orderTime;
	
}
