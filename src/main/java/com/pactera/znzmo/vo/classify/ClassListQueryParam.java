package com.pactera.znzmo.vo.classify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：ClassListQueryParam
 * @Description：TODO(这里用一句话描述这个类的作用) 
 * @author liyongxu 
 * @date 2020年8月19日 下午4:34:39 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="分类查询参数",description="分类查询参数")
public class ClassListQueryParam {
	
	@ApiModelProperty(value="id", name="id")
	private String id;
	
	@ApiModelProperty(value="父级id", name="pId")
	private String pId;
	
	@ApiModelProperty(value="分类类型", name="type")
	private String type;
	
	@ApiModelProperty(value="级别", name="level")
	private String level;
	
}
