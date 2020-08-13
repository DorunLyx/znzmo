package com.pactera.znzmo.vo.profit;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：ProfitQueryParam
 * @Description：收益查询参数 
 * @author liyongxu 
 * @date 2020年8月11日 下午3:44:03 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="收益查询参数",description="积分查询参数")
public class ProfitQueryParam {

	@ApiModelProperty(value="当前页码", name="pageNo")
	private Integer pageNo;
	
	@ApiModelProperty(value="页面数量", name="pageSize")
	private Integer pageSize;
	
	@ApiModelProperty(value="类型", name="type")
	private Integer type;
	
	@ApiModelProperty(value="关键词", name="keyWord")
    private String keyWord;

	@ApiModelProperty(value="开始时间", name="startTime")
	private LocalDateTime startTime;
	
	@ApiModelProperty(value="结束时间", name="endTime")
	private LocalDateTime endTime;
}
