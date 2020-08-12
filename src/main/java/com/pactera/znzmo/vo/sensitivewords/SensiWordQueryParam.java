/**
 * 
 */
package com.pactera.znzmo.vo.sensitivewords;

import com.pactera.znzmo.vo.MsgQueryParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 *
 */
@Data
@ApiModel(value="敏感词查询参数",description="敏感词查询参数")
public class SensiWordQueryParam {
	@ApiModelProperty(value="当前页码", name="pageNo")
	private Integer pageNo;
	
	@ApiModelProperty(value="页面数量", name="pageSize")
	private Integer pageSize;
	
	@ApiModelProperty(value="标题", name="title")
    private String title;
	
}
