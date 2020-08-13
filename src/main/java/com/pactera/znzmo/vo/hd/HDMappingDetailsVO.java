package com.pactera.znzmo.vo.hd;

import java.math.BigDecimal;
import java.util.List;

import com.pactera.znzmo.vo.common.UploadInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：HDMappingDetailsVO
 * @Description：HD贴图详情
 * @author liyongxu 
 * @date 2020年8月4日 上午11:21:33 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="HD贴图详情VO",description="HD贴图详情VO")
public class HDMappingDetailsVO {

	@ApiModelProperty(value="hdId", name="hdId")
	private Long hdId;

	@ApiModelProperty(value="主图地址", name="mainGraph")
    private String mainGraph;

	@ApiModelProperty(value="一级分类id", name="primaryclassId")
    private Long primaryClassId;

	@ApiModelProperty(value="一级分类名称", name="primaryClassName")
    private String primaryClassName;
	
	@ApiModelProperty(value="二级分类id", name="secondaryClassId")
	private Long secondaryClassId;
	
	@ApiModelProperty(value="二级分类名称", name="secondaryClassName")
	private String secondaryClassName;

	@ApiModelProperty(value="设计风格id", name="styleId")
    private Long styleId;

	@ApiModelProperty(value="风格", name="styleName")
    private String styleName;

	@ApiModelProperty(value="标题", name="title")
    private String title;

	@ApiModelProperty(value="贴图类型", name="type")
    private Integer type;

	@ApiModelProperty(value="价格", name="price")
    private BigDecimal price;

	@ApiModelProperty(value="备注", name="remarks")
    private String remarks;
	
    @ApiModelProperty(value="上传文件", name="uploadImg")
    private List<UploadInfo> uploadImg;
}
