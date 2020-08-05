package com.pactera.znzmo.vo;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：ModelAddParam
 * @Description：3d模型新增参数
 * @author liyongxu 
 * @date 2020年8月4日 上午11:21:33 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="3d模型新增参数",description="3d模型新增参数")
public class ModelUpdateParam {
	
	@ApiModelProperty(value="3d模型id", name="modelId")
	private Long modelId;

	@ApiModelProperty(value="上传模型ids", name="uploadModelIds")
	private List<String> uploadModelIds;
	
	@ApiModelProperty(value="主图地址id", name="mainGraphId")
    private String mainGraph;

	@ApiModelProperty(value="角度图ids", name="angleGraphIds")
    private List<String> angleGraphIds;
	
	@ApiModelProperty(value="标题", name="title")
	private String title;
	
	@ApiModelProperty(value="设计风格id", name="styleId")
	private Long styleId;
	
	@ApiModelProperty(value="风格", name="styleName")
	private String styleName;

	@ApiModelProperty(value="一级分类id", name="primaryclassId")
    private Long primaryClassId;

	@ApiModelProperty(value="一级分类名称", name="primaryClassName")
    private String primaryClassName;
	
	@ApiModelProperty(value="二级分类id", name="secondaryClassId")
	private Long secondaryClassId;
	
	@ApiModelProperty(value="二级分类名称", name="secondaryClassName")
	private String secondaryClassName;

	@ApiModelProperty(value="模型类型", name="modelType")
    private Integer modelType;

	@ApiModelProperty(value="模型价格", name="modelPrice")
    private BigDecimal modelPrice;

	@ApiModelProperty(value="材质贴图", name="textureMapping")
    private Integer textureMapping;

	@ApiModelProperty(value="灯光效果", name="lightingEffects")
    private String lightingEffects;

	@ApiModelProperty(value="备注", name="remarks")
    private String remarks;
	
}
