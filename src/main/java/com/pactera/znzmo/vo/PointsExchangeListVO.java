package com.pactera.znzmo.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@ApiModel(value="积分兑换列表VO",description="积分兑换列表VO")
public class PointsExchangeListVO {

	@ApiModelProperty(value="积分兑换Id", name="pointsExchangeId")
	private Long pointsExchangeId;

	@ApiModelProperty(value="用户名称", name="userName")
	private String userName;

	@ApiModelProperty(value="用户Id", name="userId")
	private Long userId;
	
	@ApiModelProperty(value="积分兑换套餐", name="exchangePackage")
    private Integer exchangePackage;
	
	@ApiModelProperty(value="兑换时间", name="exchangeTime")
	private LocalDateTime exchangeTime;
	
	@ApiModelProperty(value="消费积分", name="consumePoints")
	private BigDecimal consumePoints;
	
	@ApiModelProperty(value="当前积分", name="currentPoints")
	private BigDecimal currentPoints;
	
	@ApiModelProperty(value="总积分", name="totalPoints")
	private BigDecimal totalPoints;
	
}
