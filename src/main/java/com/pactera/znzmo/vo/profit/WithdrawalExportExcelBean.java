package com.pactera.znzmo.vo.profit;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @ClassName：WithdrawalExportExcelBean
 * @Description：提现导出Bean
 * @author liyongxu 
 * @date 2020年8月11日 下午5:44:16 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="提现导出Bean",description="提现导出Bean")
public class WithdrawalExportExcelBean {

	@Excel(name = "提现单号", width = 20)
	private String withdrawalCode;

	@Excel(name = "提现金额", width = 20)
	private BigDecimal amount;
	
	@Excel(name = "提现用户", width = 20)
	private String userName;
	
	@Excel(name = "提现手机号", width = 20)
    private String mobile;
	
	@Excel(name = "提现时间", width = 20)
	private LocalDateTime operationTime;
	
	@Excel(name = "备注", width = 20)
	private String remarks;
	
}
