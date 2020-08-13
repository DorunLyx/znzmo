package com.pactera.znzmo.vo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：ModelQueryParam
 * @Description：3d模型查询详情参数
 * @author liyongxu 
 * @date 2020年8月4日 上午11:23:48 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="3d模型查询详情参数",description="3d模型查询详情参数")
public class ModelQueryDetailsParam {

	@ApiModelProperty(value="模型id", name="modelId")
    private Long modelId;
	
	@ApiModelProperty(value="状态", name="status")
	private Integer status;
}
