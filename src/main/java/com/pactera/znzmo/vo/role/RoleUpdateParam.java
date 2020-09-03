package com.pactera.znzmo.vo.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：RoleUpdateParam
 * @Description：角色编辑参数 
 * @author liyongxu 
 * @date 2020年9月3日 下午3:13:27 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="角色编辑参数",description="角色编辑参数")
public class RoleUpdateParam {

	@ApiModelProperty(value="角色id", name="roleId")
	private String roleId;
	
	@ApiModelProperty(value="角色名称", name="name")
	private String name;
	
	@ApiModelProperty(value="角色描述", name="description")
	private String description;
	
}
