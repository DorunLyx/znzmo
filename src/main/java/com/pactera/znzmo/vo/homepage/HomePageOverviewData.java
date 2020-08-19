package com.pactera.znzmo.vo.homepage;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：HomePageOverviewData
 * @Description：首页总览数据
 * @author liyongxu 
 * @date 2020年8月19日 上午10:35:52 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="首页总览数据",description="首页总览数据")
public class HomePageOverviewData {
	
	@ApiModelProperty(value="3d模型", name="modelData")
	private List<HomePageSimplifyData> modelData;

	@ApiModelProperty(value="su模型", name="suModelData")
	private List<HomePageSimplifyData> suModelData;
	
	@ApiModelProperty(value="图纸方案", name="drawingData")
	private List<HomePageSimplifyData> drawingData;
	
	@ApiModelProperty(value="高清贴图", name="hdData")
	private List<HomePageSimplifyData> hdData;
	
}
