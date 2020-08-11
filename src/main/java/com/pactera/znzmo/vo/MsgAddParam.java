/**
 * 
 */
package com.pactera.znzmo.vo;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author heliang
 *
 */
@Data
@ApiModel(value = "系统消息新增参数", description = "系统消息新增参数")
public class MsgAddParam {
	@ApiModelProperty(value = "业务id", name = "msgId")
	private String msgId;

	@ApiModelProperty(value = "标题", name = "title")
	private String title;
	@ApiModelProperty(value = "内容", name = "content")
	private String content;

	@ApiModelProperty(value = "用户id", name = "createId")
	private Long createId;

	@ApiModelProperty(value = "用户账号", name = "createAccount")
	private String createAccount;
	
	@ApiModelProperty(value = "用户名称", name = "createName")
	private String createName;

	@ApiModelProperty(value = "创建时间", name = "createTime")
	private LocalDateTime createTime;

}
