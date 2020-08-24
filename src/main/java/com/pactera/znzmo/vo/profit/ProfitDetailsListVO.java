package com.pactera.znzmo.vo.profit;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：ProfitDetailsListVO
 * @Description：收益明细列表VO
 * @author liyongxu 
 * @date 2020年8月11日 下午5:03:37 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="收益明细列表VO",description="收益明细列表VO")
public class ProfitDetailsListVO {

	@ApiModelProperty(value="收益明细Id", name="profitDetailsId")
	private String profitDetailsId;

	@ApiModelProperty(value="用户名称", name="userName")
	private String userName;

	@ApiModelProperty(value="用户Id", name="userId")
	private String userId;
	
	@ApiModelProperty(value="手机号", name="mobile")
    private String mobile;
	
	@ApiModelProperty(value="金额", name="amount")
	private BigDecimal amount;
	
	@ApiModelProperty(value="操作时间", name="operationTime")
	private String operationTime;
	
	@ApiModelProperty(value="提现单号", name="withdrawalCode")
	private String withdrawalCode;
	
	@ApiModelProperty(value="备注", name="remarks")
	private String remarks;
	
}
