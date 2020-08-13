package com.pactera.znzmo.vo.homepage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：HomePageQueryParam
 * @Description：首页查询VO
 * @author liyongxu 
 * @date 2020年8月4日 上午11:23:48 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="资产查询Vo",description="资产查询Vo")
public class HomePageQueryParam {

	@ApiModelProperty(value="当前页码", name="pageNo")
	private Integer pageNo;
	
	@ApiModelProperty(value="页面数量", name="pageSize")
	private Integer pageSize;
	
	@ApiModelProperty(value="类型", name="type")
	private Integer type;

	@ApiModelProperty(value="状态", name="status")
    private Integer status;
	
}
