package com.pactera.znzmo.vo.banner;

import java.time.LocalDateTime;
import java.util.List;

import com.pactera.znzmo.vo.common.UploadInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：BannerUpdateParam
 * @Description：轮播编辑参数
 * @author liyongxu 
 * @date 2020年8月4日 上午11:21:33 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="轮播编辑参数",description="轮播编辑参数")
public class BannerUpdateParam {
	
	@ApiModelProperty(value="轮播Id", name="bannerId")
	private Long bannerId;
	
	@ApiModelProperty(value="主图地址id", name="mainGraphId")
	private String mainGraph;

    @ApiModelProperty(value="上传文件", name="uploadImg")
    private List<UploadInfo> uploadImg;

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
