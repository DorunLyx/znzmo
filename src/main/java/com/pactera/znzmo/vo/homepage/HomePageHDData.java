package com.pactera.znzmo.vo.homepage;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：HomePageHDData
 * @Description：首页贴图数据
 * @author liyongxu 
 * @date 2020年8月19日 上午10:15:13 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="首页贴图数据",description="首页贴图数据")
public class HomePageHDData {

	@ApiModelProperty(value="贴图", name="mappingData")
	private List<HomePageSimplifyData> mappingData;
	
}
