package com.pactera.znzmo.vo.msg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：MsgQueryParam
 * @Description：3d模型查询详情参数
 * @author liyongxu 
 * @date 2020年8月4日 上午11:23:48 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="系統消息查询详情参数",description="系統消息查询详情参数")
public class MsgQueryDetailsParam {

	@ApiModelProperty(value="msgid", name="msgId")
    private Long msgId;
	
	@ApiModelProperty(value="状态", name="status")
	private Integer status;
}
