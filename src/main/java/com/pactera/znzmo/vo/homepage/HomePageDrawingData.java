package com.pactera.znzmo.vo.homepage;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：HomePageDrawingData
 * @Description：首页图纸方案数据
 * @author liyongxu 
 * @date 2020年8月19日 上午10:13:16 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="首页图纸方案数据",description="首页图纸方案数据")
public class HomePageDrawingData {

	@ApiModelProperty(value="室内空间", name="spaceData")
	private List<HomePageSimplifyData> spaceData;
	
	@ApiModelProperty(value="家居", name="homeDesignData")
	private List<HomePageSimplifyData> homeDesignData;
	
}
