package com.pactera.znzmo.vo;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：BannerDetailsVO
 * @Description：轮播详情
 * @author liyongxu 
 * @date 2020年8月4日 上午11:21:33 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="轮播详情VO",description="轮播详情VO")
public class BannerDetailsVO {

	@ApiModelProperty(value="轮播Id", name="bannerId")
	private Long bannerId;
	
	@ApiModelProperty(value="主图地址id", name="mainGraphId")
	private String mainGraph;

	@ApiModelProperty(value="标题", name="title")
	private String title;

	@ApiModelProperty(value="类型", name="type")
    private Integer type;

	@ApiModelProperty(value="开始时间", name="startTime")
	private LocalDateTime startTime;
	
	@ApiModelProperty(value="结束时间", name="endTime")
	private LocalDateTime endTime;

	@ApiModelProperty(value="跳转链接", name="jumpLink")
	private String jumpLink;

	@ApiModelProperty(value="排序", name="sort")
	private String sort;
	
}
