package com.pactera.znzmo.vo;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：HomePageBannerData
 * @Description：首页轮播数据
 * @author liyongxu 
 * @date 2020年8月12日 上午11:40:29 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="首页轮播数据",description="首页轮播数据")
public class HomePageBannerData {
	
	@ApiModelProperty(value="主图地址", name="mainGraphId")
	private String mainGraph;

	@ApiModelProperty(value="开始时间", name="startTime")
	private LocalDateTime startTime;
	
	@ApiModelProperty(value="结束时间", name="endTime")
	private LocalDateTime endTime;

	@ApiModelProperty(value="跳转链接", name="jumpLink")
	private String jumpLink;

	@ApiModelProperty(value="排序", name="sort")
	private String sort;
	
}
