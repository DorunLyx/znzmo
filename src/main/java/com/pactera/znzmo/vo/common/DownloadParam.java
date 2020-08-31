package com.pactera.znzmo.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：DownloadParam
 * @Description：下载次数参数
 * @author liyongxu 
 * @date 2020年8月31日 上午11:02:23 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="下载次数参数",description="下载次数参数")
public class DownloadParam {
	
	@ApiModelProperty(value="类型(1.3d模型,2.su模型,3.图纸方案,4.高清贴图,5.资料库)", name="type")
	private Integer type;
	
	@ApiModelProperty(value="下载版本", name="downloadtype")
	private String downloadtype;
	
	@ApiModelProperty(value="业务id", name="reId")
	private String reId;

}
