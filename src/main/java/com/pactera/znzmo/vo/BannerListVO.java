package com.pactera.znzmo.vo;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：HomePageListVO
 * @Description：首页数据VO
 * @author liyongxu 
 * @date 2020年8月4日 上午11:21:33 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="首页数据VO",description="首页数据VO")
public class BannerListVO {
	
	@ApiModelProperty(value="轮播Id", name="bannerId")
	private Long bannerId;

	@ApiModelProperty(value="状态", name="status")
    private Integer status;
	
	@ApiModelProperty(value="排序", name="sort")
	private String sort;
	
	@ApiModelProperty(value="类型", name="type")
	private Integer type;
	
	@ApiModelProperty(value="开始时间", name="startTime")
	private LocalDateTime startTime;
	
	@ApiModelProperty(value="结束时间", name="endTime")
	private LocalDateTime endTime;
	
	@ApiModelProperty(value="图片", name="mainGraph")
    private String mainGraph;

}