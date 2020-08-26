package com.pactera.znzmo.vo.personal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：DownloadRecordsAddParam
 * @Description：下载记录新增参数
 * @author liyongxu 
 * @date 2020年8月26日 上午11:55:03 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="下载记录新增参数",description="下载记录新增参数")
public class DownloadRecordsAddParam {

	@ApiModelProperty(value="用户id", name="userId")
	private String userId;
	
	@ApiModelProperty(value="业务类型", name="reType")
	private Integer reType;

	@ApiModelProperty(value="业务id", name="reId")
    private String reId;
	
}
