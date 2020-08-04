package com.pactera.znzmo.vo;

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

	@ApiModelProperty(value="资产Id", name="assetId")
    private String assetId;

}
