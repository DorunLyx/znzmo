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
@ApiModel(value="log数据VO",description="log数据VO")
public class LogListVO {
	@ApiModelProperty(value="logId", name="logId")
	private Long logId;

	@ApiModelProperty(value="操作员", name="operatorId")
    private Long operatorId;
	
	@ApiModelProperty(value="ip", name="ip")
	private String ip;
	
	@ApiModelProperty(value="事件", name="event")
	private String event;
	
	@ApiModelProperty(value="开始时间", name="startTime")
	private LocalDateTime startTime;
	
	@ApiModelProperty(value="结束时间", name="endTime")
	private LocalDateTime endTime;
	
	@ApiModelProperty(value="内容", name="content")
    private String content;

}
