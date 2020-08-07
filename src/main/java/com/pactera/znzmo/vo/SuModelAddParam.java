package com.pactera.znzmo.vo;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：SuModelAddParam
 * @Description：su模型新增参数
 * @author liyongxu 
 * @date 2020年8月4日 上午11:21:33 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="su模型新增参数",description="su模型新增参数")
public class SuModelAddParam {
	
	@ApiModelProperty(value="主图地址id", name="mainGraphId")
	private String mainGraph;

    @ApiModelProperty(value="上传文件", name="uploadImg")
    private List<UploadInfo> uploadImg;

	@ApiModelProperty(value="标题", name="title")
	private String title;
	
	@ApiModelProperty(value="风格id", name="styleId")
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
	
	@ApiModelProperty(value="三级分类id", name="threeClassId")
	private Long threeClassId;
	
	@ApiModelProperty(value="三级分类名称", name="threeClassName")
	private String threeClassName;

	@ApiModelProperty(value="模型类型", name="type")
    private Integer type;

	@ApiModelProperty(value="模型价格", name="price")
    private BigDecimal price;

	@ApiModelProperty(value="材质贴图", name="textureMapping")
    private Integer textureMapping;

	@ApiModelProperty(value="选择版本", name="version")
    private String version;

	@ApiModelProperty(value="备注", name="remarks")
    private String remarks;
	
}
