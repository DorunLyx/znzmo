package com.pactera.znzmo.vo.role;

import java.util.List;

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
@ApiModel(value="角色权限新增参数",description="角色权限新增参数")
public class RoleAuthAddParam {

	@ApiModelProperty(value="角色id", name="roleId")
	private String roleId;
	
	@ApiModelProperty(value="权限ids", name="authIdList")
	private List<String> authIdList;
	
}
