package com.pactera.znzmo.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：PointsAddParam
 * @Description：积分新增参数
 * @author liyongxu 
 * @date 2020年8月10日 下午3:19:47 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="积分新增参数",description="积分新增参数")
public class PointsAddParam {

	@ApiModelProperty(value="业务类型", name="type")
	private Integer type;
	
	@ApiModelProperty(value="用户id", name="userId")
	private Long userId;
	
	@ApiModelProperty(value="用户名称", name="userName")
	private String userName;
	
	@ApiModelProperty(value="积分数", name="pointsNum")
	private BigDecimal pointsNum;
	
	@ApiModelProperty(value="", name="remarks")
	private String remarks;

	@ApiModelProperty(value="积分时间", name="pointsTime")
    private LocalDateTime pointsTime;
	
}
