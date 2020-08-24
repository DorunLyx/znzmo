package com.pactera.znzmo.vo.classify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：ClassifyUpdateVO
 * @Description：分类编辑VO
 * @author liyongxu 
 * @date 2020年8月17日 上午10:21:13 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="分类编辑VO",description="分类编辑VO")
public class ClassifyUpdateParam {

	@ApiModelProperty(value="分类Id", name="classifyId")
	private String classifyId;

	@ApiModelProperty(value="分类名称", name="classifyName")
	private String classifyName;
	
	@ApiModelProperty(value="父级id", name="pId")
	private String pId;
	
	@ApiModelProperty(value="父级名称", name="pName")
	private String pName;
	
	@ApiModelProperty(value="级别", name="level")
	private String level;
	
	@ApiModelProperty(value="排序", name="sort")
	private Integer sort;
}
