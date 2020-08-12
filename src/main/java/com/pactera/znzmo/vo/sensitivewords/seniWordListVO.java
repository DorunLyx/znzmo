/**
 * 
 */
package com.pactera.znzmo.vo.sensitivewords;

import java.time.LocalDateTime;

import com.pactera.znzmo.vo.MsgListVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 *
 */
@Data
@ApiModel(value = "敏感词列表VO", description = "敏感词列表VO")
public class seniWordListVO {
	@ApiModelProperty(value = "Id", name = "Id")
	private Long Id;
	@ApiModelProperty(value = "敏感词", name = "name")
	private String name;
	@ApiModelProperty(value = "替换内容", name = "showContent")
	private String showContent;
	@ApiModelProperty(value = "发布用户", name = "createAccount")
	private String createAccount;
	@ApiModelProperty(value = "状态", name = "status")
	private Integer status;
	@ApiModelProperty(value="发布时间",name="createTime")
	private LocalDateTime createFTime;
}
