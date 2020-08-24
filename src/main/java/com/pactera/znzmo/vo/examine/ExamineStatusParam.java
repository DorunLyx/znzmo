package com.pactera.znzmo.vo.examine;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：ExamineStatusParam
 * @Description：审核参数
 * @author liyongxu 
 * @date 2020年8月10日 上午11:23:48 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="审核参数",description="审核参数")
public class ExamineStatusParam {

	@ApiModelProperty(value="审核id", name="examineId")
    private String examineId;
	
	@ApiModelProperty(value="状态", name="status")
	private Integer status;
}
