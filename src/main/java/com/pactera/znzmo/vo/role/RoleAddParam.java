package com.pactera.znzmo.vo.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：RoleAddParam
 * @Description：角色新增参数 
 * @author liyongxu 
 * @date 2020年9月3日 下午3:13:27 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="角色新增参数",description="角色新增参数")
public class RoleAddParam {

	@ApiModelProperty(value="角色名称", name="name")
	private String name;
	
	@ApiModelProperty(value="角色描述", name="description")
	private String description;
	
}
