package com.pactera.znzmo.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：UserQueryParam
 * @Description：用户查询参数
 * @author liyongxu 
 * @date 2020年9月4日 上午10:08:29 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="用户查询参数",description="用户查询参数")
public class UserQueryParam {

	@ApiModelProperty(value="当前页码", name="pageNo")
	private Integer pageNo;
	
	@ApiModelProperty(value="页面数量", name="pageSize")
	private Integer pageSize;
	
	@ApiModelProperty(value="用户名称", name="name")
    private String name;
	
	@ApiModelProperty(value="用户类型", name="type")
	private String type;
}
