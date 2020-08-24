package com.pactera.znzmo.vo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：OrderQueryParam
 * @Description：订单查询参数
 * @author liyongxu 
 * @date 2020年8月10日 上午11:23:48 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="订单查询参数",description="订单查询参数")
public class OrderQueryParam {

	@ApiModelProperty(value="当前页码", name="pageNo")
	private Integer pageNo;
	
	@ApiModelProperty(value="页面数量", name="pageSize")
	private Integer pageSize;
	
	@ApiModelProperty(value="关键字", name="keyword")
    private String keyword;
	
	@ApiModelProperty(value="类型", name="type")
    private Integer type;

	@ApiModelProperty(value="开始时间", name="startTime")
	private String startTime;
	
	@ApiModelProperty(value="结束时间", name="endTime")
	private String endTime;
}
