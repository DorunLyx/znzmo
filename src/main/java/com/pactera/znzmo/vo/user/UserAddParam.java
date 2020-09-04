package com.pactera.znzmo.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：UserAddParam
 * @Description：用户新增参数
 * @author liyongxu 
 * @date 2020年9月4日 上午10:19:49 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="用户新增参数",description="用户新增参数")
public class UserAddParam {

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
	
	@ApiModelProperty(value="密码", name="password")
    private String password;
}
