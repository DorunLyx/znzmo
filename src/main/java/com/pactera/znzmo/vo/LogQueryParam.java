/**
 * 
 */
package com.pactera.znzmo.vo;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 *
 */
@Data
@ApiModel(value="日志查询参数",description="日志查询参数")
public class LogQueryParam {
	@ApiModelProperty(value="当前页码", name="pageNo")
	private Integer pageNo;
	
	@ApiModelProperty(value="页面数量", name="pageSize")
	private Integer pageSize;
	
	@ApiModelProperty(value="标题", name="title")
	private String title;

	@ApiModelProperty(value="类型", name="type")
    private Integer type;

	@ApiModelProperty(value="开始时间", name="startTime")
	private LocalDateTime startTime;
	
}
