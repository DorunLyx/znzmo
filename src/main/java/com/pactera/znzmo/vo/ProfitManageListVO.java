package com.pactera.znzmo.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：ProfitDetailsListVO
 * @Description：收益统计列表VO
 * @author liyongxu 
 * @date 2020年8月11日 下午3:51:24 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="收益统计列表VO",description="收益统计列表VO")
public class ProfitManageListVO {

	@ApiModelProperty(value="收益统计Id", name="profitId")
	private Long profitId;

	@ApiModelProperty(value="用户名称", name="userName")
	private String userName;

	@ApiModelProperty(value="用户Id", name="userId")
	private Long userId;
	
	@ApiModelProperty(value="手机号", name="mobile")
    private String mobile;
	
	@ApiModelProperty(value="总收益", name="totalRevenue")
	private BigDecimal totalRevenue;
	
	@ApiModelProperty(value="账户余额", name="accountBalance")
	private BigDecimal accountBalance;
	
	@ApiModelProperty(value="提现金额", name="withdrawalAmount")
	private BigDecimal withdrawalAmount;
	
}
