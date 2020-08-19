package com.pactera.znzmo.vo.homepage;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：HomePageModelData
 * @Description：首页3d模型数据
 * @author liyongxu 
 * @date 2020年8月19日 上午10:01:42 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="首页3d模型数据",description="首页3d模型数据")
public class HomePageModelData {
	
	@ApiModelProperty(value="全部", name="allData")
	private List<HomePageSimplifyData> allData;

	@ApiModelProperty(value="客厅", name="livingRoomData")
	private List<HomePageSimplifyData> livingRoomData;
	
	@ApiModelProperty(value="吊灯", name="ceilingLampData")
	private List<HomePageSimplifyData> ceilingLampData;
	
	@ApiModelProperty(value="床具", name="bedclothesData")
	private List<HomePageSimplifyData> bedclothesData;
	
	@ApiModelProperty(value="沙发", name="sofaData")
	private List<HomePageSimplifyData> modelFourData;
	
	@ApiModelProperty(value="柜子", name="cabinetData")
	private List<HomePageSimplifyData> cabinetData;
	
	@ApiModelProperty(value="陈设摆件", name="furnishingsData")
	private List<HomePageSimplifyData> furnishingsData;
	
	@ApiModelProperty(value="书房茶室", name="teahouseStudyData")
	private List<HomePageSimplifyData> teahouseStudyData;
	
}
