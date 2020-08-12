package com.pactera.znzmo.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：HomePageListVO
 * @Description：首页数据VO
 * @author liyongxu 
 * @date 2020年8月4日 上午11:21:33 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="首页数据VO",description="首页数据VO")
public class HomePageListVO {
	
	@ApiModelProperty(value="首页轮播展示数据", name="homePageBannerData")
	private List<HomePageBannerData> homePageBannerData;
	
	@ApiModelProperty(value="首页展示数据", name="homePageSimplifyData")
	private List<HomePageSimplifyData> homePageSimplifyData;
	
	@ApiModelProperty(value="首页3d模型数据", name="homePageModelData")
	private List<HomePageSimplifyData> homePageModelData;
	
	@ApiModelProperty(value="首页Su模型展示", name="homePageSuModelData")
	private List<HomePageSimplifyData> homePageSuModelData;
	
	@ApiModelProperty(value="首页高清贴图展示", name="homePageHDData")
	private List<HomePageSimplifyData> homePageHDData;
	
	@ApiModelProperty(value="首页图纸方案展示", name="homePageDrawingData")
	private List<HomePageSimplifyData> homePageDrawingData;
	
}
