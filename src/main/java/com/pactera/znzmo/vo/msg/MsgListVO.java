package com.pactera.znzmo.vo.msg;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：MsgListVO
 * @Description：系统消息列表
 * @author liyongxu
 * @date 2020年8月4日 上午11:21:33
 * @version 1.0.0
 */
@Data
@ApiModel(value = "系统消息列表VO", description = "系统消息列表VO")
public class MsgListVO {

	@ApiModelProperty(value = "消息Id", name = "msgId")
	private Long msgId;
	@ApiModelProperty(value = "标题", name = "title")
	private String title;
	@ApiModelProperty(value = "内容", name = "content")
	private String content;
	@ApiModelProperty(value = "发布用户", name = "createAccount")
	private String createAccount;
	@ApiModelProperty(value = "状态", name = "status")
	private Integer status;
	@ApiModelProperty(value="发布时间",name="createTime")
	private LocalDateTime createFTime;

}
