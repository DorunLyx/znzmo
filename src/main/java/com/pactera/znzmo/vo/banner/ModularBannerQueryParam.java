package com.pactera.znzmo.vo.banner;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：ModularBannerQueryParam
 * @Description：模块轮播查询参数
 * @author liyongxu 
 * @date 2020年8月19日 上午11:49:54 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="模块轮播查询参数",description="模块轮播查询参数")
public class ModularBannerQueryParam {
	
	@ApiModelProperty(value="类型", name="type")
	private Integer type;
	
}
