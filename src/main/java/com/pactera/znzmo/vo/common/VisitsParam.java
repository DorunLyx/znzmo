package com.pactera.znzmo.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：VisitsParam
 * @Description：TODO(这里用一句话描述这个类的作用) 
 * @author liyongxu 
 * @date 2020年8月31日 上午11:02:23 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="浏览参数",description="浏览参数")
public class VisitsParam {
	
	@ApiModelProperty(value="类型(1.3d模型,2.su模型,3.图纸方案,4.高清贴图,5.资料库)", name="type")
	private Integer type;
	
	@ApiModelProperty(value="业务id", name="reId")
	private String reId;

}
