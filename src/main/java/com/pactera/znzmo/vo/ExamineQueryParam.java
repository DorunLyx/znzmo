package com.pactera.znzmo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：ExamineQueryParam
 * @Description：审核管理查询参数
 * @author liyongxu 
 * @date 2020年8月10日 上午11:23:48 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="审核管理查询参数",description="审核管理查询参数")
public class ExamineQueryParam {

	@ApiModelProperty(value="当前页码", name="pageNo")
	private Integer pageNo;
	
	@ApiModelProperty(value="页面数量", name="pageSize")
	private Integer pageSize;
	
	@ApiModelProperty(value="分类id", name="classId")
	private Long classId;
	
	@ApiModelProperty(value="类型", name="type")
	private Integer type;

	@ApiModelProperty(value="状态", name="status")
    private Integer status;

	@ApiModelProperty(value="关键字", name="keyword")
    private String keyword;
	
}
