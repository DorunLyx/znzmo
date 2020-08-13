package com.pactera.znzmo.vo.points;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：PointsQueryParam
 * @Description：积分查询参数
 * @author liyongxu 
 * @date 2020年8月11日 上午11:23:48 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="积分查询参数",description="积分查询参数")
public class PointsQueryParam {

	@ApiModelProperty(value="当前页码", name="pageNo")
	private Integer pageNo;
	
	@ApiModelProperty(value="页面数量", name="pageSize")
	private Integer pageSize;
	
	@ApiModelProperty(value="用户名", name="userName")
    private String userName;

	@ApiModelProperty(value="开始时间", name="startTime")
	private LocalDateTime startTime;
	
	@ApiModelProperty(value="结束时间", name="endTime")
	private LocalDateTime endTime;
}
