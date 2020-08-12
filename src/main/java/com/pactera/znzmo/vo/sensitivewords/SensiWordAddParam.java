/**
 * 
 */
package com.pactera.znzmo.vo.sensitivewords;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 *
 */
@Data
@ApiModel(value = "敏感词新增参数", description = "敏感词新增参数")
public class SensiWordAddParam {
	@ApiModelProperty(value = "业务id", name = "senId")
	private String senId;

	@ApiModelProperty(value = "敏感词", name = "name")
	private String name;
	@ApiModelProperty(value = "代替内容", name = "showContent")
	private String showContent;

	@ApiModelProperty(value = "用户id", name = "createId")
	private Long createId;

	@ApiModelProperty(value = "用户账号", name = "createAccount")
	private String createAccount;
	
	@ApiModelProperty(value = "用户名称", name = "createName")
	private String createName;

	@ApiModelProperty(value = "创建时间", name = "createTime")
	private LocalDateTime createTime;

}
