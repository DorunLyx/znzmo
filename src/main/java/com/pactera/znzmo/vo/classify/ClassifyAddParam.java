package com.pactera.znzmo.vo.classify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：ClassifyAddParam
 * @Description：分类新增参数
 * @author liyongxu 
 * @date 2020年8月17日 上午10:09:52 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="分类新增参数",description="分类新增参数")
public class ClassifyAddParam {

	@ApiModelProperty(value="分类名称", name="classifyName")
	private String classifyName;
	
	@ApiModelProperty(value="父级id", name="pId")
	private String pId;
	
	@ApiModelProperty(value="级别", name="level")
	private String level;
	
	@ApiModelProperty(value="排序", name="sort")
	private Integer sort;
}
