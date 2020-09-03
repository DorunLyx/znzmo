package com.pactera.znzmo.vo.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：RoleQueryParam
 * @Description：角色查询参数
 * @author liyongxu 
 * @date 2020年9月3日 下午2:47:48 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="角色查询参数",description="角色查询参数")
public class RoleQueryParam {

	@ApiModelProperty(value="当前页码", name="pageNo")
	private Integer pageNo;
	
	@ApiModelProperty(value="页面数量", name="pageSize")
	private Integer pageSize;
	
	@ApiModelProperty(value="角色名称", name="name")
    private String name;
}
