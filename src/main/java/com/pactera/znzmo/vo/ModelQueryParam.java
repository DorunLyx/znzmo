package com.pactera.znzmo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：ModelQueryParam
 * @Description：3d模型查询VO
 * @author liyongxu 
 * @date 2020年8月4日 上午11:23:48 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="3d模型查询Vo",description="3d模型查询Vo")
public class ModelQueryParam {

	@ApiModelProperty(value="当前页码", name="pageNo")
	private Integer pageNo;
	
	@ApiModelProperty(value="页面数量", name="pageSize")
	private Integer pageSize;
	
	@ApiModelProperty(value="关键字", name="keyword")
    private String keyword;
	
	@ApiModelProperty(value="设计风格id", name="styleId")
    private Long styleId;

	@ApiModelProperty(value="一级分类id", name="primaryclassId")
    private Long primaryClassId;
	
	@ApiModelProperty(value="二级分类id", name="secondaryClassId")
	private Long secondaryClassId;
}
