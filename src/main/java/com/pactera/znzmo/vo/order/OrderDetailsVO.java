package com.pactera.znzmo.vo.order;

import java.math.BigDecimal;
import java.util.List;

import com.pactera.znzmo.vo.common.UploadInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：OrderDetailsVO
 * @Description：订单详情VO
 * @author liyongxu 
 * @date 2020年8月10日 下午3:33:22 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="订单详情VO",description="订单详情VO")
public class OrderDetailsVO {

	@ApiModelProperty(value="订单Id", name="orderId")
	private String orderId;
	
	@ApiModelProperty(value="编号", name="code")
    private String code;

	@ApiModelProperty(value="标题", name="title")
    private String title;
	
	@ApiModelProperty(value="价格", name="price")
	private BigDecimal price;

	@ApiModelProperty(value="类型", name="type")
    private Integer type;
	
	@ApiModelProperty(value="购买用户", name="userName")
	private String userName;

	@ApiModelProperty(value="用户Id", name="userId")
	private String userId;
	
	@ApiModelProperty(value="下单时间", name="orderTime")
	private String orderTime;

	@ApiModelProperty(value="主图地址", name="mainGraph")
    private String mainGraph;

	@ApiModelProperty(value="设计风格id", name="styleId")
    private String styleId;

	@ApiModelProperty(value="风格", name="styleName")
    private String styleName;
	
	@ApiModelProperty(value="一级分类id", name="primaryclassId")
    private String primaryClassId;

	@ApiModelProperty(value="一级分类名称", name="primaryClassName")
    private String primaryClassName;
	
	@ApiModelProperty(value="二级分类id", name="secondaryClassId")
	private String secondaryClassId;
	
	@ApiModelProperty(value="二级分类名称", name="secondaryClassName")
	private String secondaryClassName;

	@ApiModelProperty(value="材质贴图", name="textureMapping")
    private Integer textureMapping;

	@ApiModelProperty(value="灯光效果", name="lightingEffects")
    private String lightingEffects;

	@ApiModelProperty(value="备注", name="remarks")
    private String remarks;
	
    @ApiModelProperty(value="上传文件", name="uploadImg")
    private List<UploadInfo> uploadImg;
}
