package com.pactera.znzmo.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：UserUpdateParam
 * @Description：用户编辑参数
 * @author liyongxu 
 * @date 2020年9月4日 上午10:47:35 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="用户编辑参数",description="用户编辑参数")
public class UserUpdateParam {
	
	@ApiModelProperty(value="用户id", name="userId")
	private String userId;

	@ApiModelProperty(value="用户名称", name="userName")
	private String userName;
	
	@ApiModelProperty(value="姓名", name="realName")
	private String realName;
	
	@ApiModelProperty(value="性别（0-男；1-女；2-保密）", name="sex")
	private Integer sex;
	
	@ApiModelProperty(value="出生日期", name="birthday")
	private String birthday;
	
	@ApiModelProperty(value="手机号", name="mobile")
	private String mobile;

	@ApiModelProperty(value="邮箱", name="email")
    private String email;

	@ApiModelProperty(value="用户类型", name="type")
    private Integer type;
}
