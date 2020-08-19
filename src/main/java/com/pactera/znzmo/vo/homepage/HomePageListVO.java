package com.pactera.znzmo.vo.homepage;

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
	
	@ApiModelProperty(value="首页总览数据", name="homePageOverviewData")
	private HomePageOverviewData homePageOverviewData;
	
	@ApiModelProperty(value="首页3d模型数据", name="homePageModelData")
	private HomePageModelData homePageModelData;
	
	@ApiModelProperty(value="首页Su模型展示", name="homePageSuModelData")
	private HomePageSuModelData homePageSuModelData;
	
	@ApiModelProperty(value="首页图纸方案展示", name="homePageDrawingData")
	private HomePageDrawingData homePageDrawingData;
	
	@ApiModelProperty(value="首页高清贴图展示", name="homePageHDData")
	private HomePageHDData homePageHDData;
	
}
