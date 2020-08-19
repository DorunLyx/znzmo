package com.pactera.znzmo.vo.homepage;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：HomePageSimplifyData
 * @Description：首页精简查询数据
 * @author liyongxu 
 * @date 2020年8月12日 上午11:40:29 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="首页对象数据",description="首页对象数据")
public class HomePageSimplifyData {
	
	@ApiModelProperty(value="业务id", name="reId")
	private Long reId;
	
	@ApiModelProperty(value="业务类型", name="reType")
	private Integer reType;
	
	@ApiModelProperty(value="主图地址", name="mainGraphId")
	private String mainGraph;

	@ApiModelProperty(value="标题", name="title")
	private String title;

	@ApiModelProperty(value="金币", name="price")
	private BigDecimal price;
	
	@ApiModelProperty(value="模板类型", name="type")
	private Integer type;
	
}
