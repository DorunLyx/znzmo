package com.pactera.znzmo.vo.hd;

import java.math.BigDecimal;
import java.util.List;

import com.pactera.znzmo.vo.common.UploadInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：HDMappingInfoVO
 * @Description：HD贴图编辑信息VO
 * @author liyongxu 
 * @date 2020年8月24日 下午6:02:28 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="HD贴图编辑信息VO",description="HD贴图编辑信息VO")
public class HDMappingInfoVO {

	@ApiModelProperty(value="hdId", name="hdId")
	private String hdId;

	@ApiModelProperty(value="一级分类id", name="primaryclassId")
    private String primaryClassId;

	@ApiModelProperty(value="一级分类名称", name="primaryClassName")
    private String primaryClassName;
	
	@ApiModelProperty(value="二级分类id", name="secondaryClassId")
	private String secondaryClassId;
	
	@ApiModelProperty(value="二级分类名称", name="secondaryClassName")
	private String secondaryClassName;

	@ApiModelProperty(value="设计风格id", name="styleId")
    private String styleId;

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
