package com.pactera.znzmo.vo.personal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：DownloadRecordsQueryParam
 * @Description：我的下载查询参数
 * @author liyongxu 
 * @date 2020年8月26日 上午11:01:31 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="我的下载查询参数",description="我的下载查询参数")
public class DownloadRecordsQueryParam {

	@ApiModelProperty(value="当前页码", name="pageNo")
	private Integer pageNo;
	
	@ApiModelProperty(value="页面数量", name="pageSize")
	private Integer pageSize;
	
	@ApiModelProperty(value="用户id", name="userId")
	private String userId;
	
	@ApiModelProperty(value="分类", name="type")
	private String type;

	@ApiModelProperty(value="开始时间", name="startTime")
	private String startTime;
	
	@ApiModelProperty(value="结束时间", name="endTime")
	private String endTime;
	
}
