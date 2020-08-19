package com.pactera.znzmo.vo.homepage;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：HomePageSuModelData
 * @Description：首页su模型数据
 * @author liyongxu 
 * @date 2020年8月19日 上午10:09:39 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="首页su模型数据",description="首页su模型数据")
public class HomePageSuModelData {

	@ApiModelProperty(value="家居客厅", name="livingRoomData")
	private List<HomePageSimplifyData> livingRoomData;
	
	@ApiModelProperty(value="沙发茶几", name="sofatableData")
	private List<HomePageSimplifyData> sofatableData;

	@ApiModelProperty(value="商业街", name="commercialStreetData")
	private List<HomePageSimplifyData> commercialStreetData;
	
	@ApiModelProperty(value="住宅景观", name="landscapeHousingData")
	private List<HomePageSimplifyData> landscapeHousingData;

	@ApiModelProperty(value="景观小品", name="landscapeSketchData")
	private List<HomePageSimplifyData> landscapeSketchData;
}
