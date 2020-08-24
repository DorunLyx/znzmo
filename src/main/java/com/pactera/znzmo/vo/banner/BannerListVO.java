package com.pactera.znzmo.vo.banner;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：BannerListVO
 * @Description：Banner数据VO
 * @author liyongxu 
 * @date 2020年8月4日 上午11:21:33 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="Banner数据VO",description="Banner数据VO")
public class BannerListVO {
	
	@ApiModelProperty(value="轮播Id", name="bannerId")
	private String bannerId;

	@ApiModelProperty(value="状态", name="status")
    private Integer status;
	
	@ApiModelProperty(value="排序", name="sort")
	private String sort;
	
	@ApiModelProperty(value="类型", name="type")
	private Integer type;
	
	@ApiModelProperty(value="开始时间", name="startTime")
	private String startTime;
	
	@ApiModelProperty(value="结束时间", name="endTime")
	private String endTime;
	
	@ApiModelProperty(value="图片", name="mainGraph")
    private String mainGraph;

}
