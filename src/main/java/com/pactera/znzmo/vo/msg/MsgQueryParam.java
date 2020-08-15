package com.pactera.znzmo.vo.msg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：ModelQueryParam.java
 * @Description：系统消息查询参数
 * @author 何亮 
 * @date 2020年8月4日 上午11:23:48 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="系统消息查询参数",description="系统消息查询参数")
public class MsgQueryParam {

	@ApiModelProperty(value="当前页码", name="pageNo")
	private Integer pageNo;
	
	@ApiModelProperty(value="页面数量", name="pageSize")
	private Integer pageSize;
	
	@ApiModelProperty(value="标题", name="title")
    private String title;
	

}
