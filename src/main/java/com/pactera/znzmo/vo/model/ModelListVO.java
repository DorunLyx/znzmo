package com.pactera.znzmo.vo.model;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：ModelListVO
 * @Description：3d模型列表
 * @author liyongxu 
 * @date 2020年8月4日 上午11:21:33 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="3d模型列表VO",description="3d模型列表VO")
public class ModelListVO {

	@ApiModelProperty(value="3d模板Id", name="modelId")
	private String modelId;

	@ApiModelProperty(value="主图地址", name="mainGraph")
    private String mainGraph;

	@ApiModelProperty(value="编号", name="code")
    private String code;

	@ApiModelProperty(value="一级分类名称", name="primaryClassName")
    private String primaryClassName;
	
	@ApiModelProperty(value="二级分类名称", name="secondaryClassName")
	private String secondaryClassName;

	@ApiModelProperty(value="风格", name="styleName")
    private String styleName;

	@ApiModelProperty(value="标题", name="title")
    private String title;

	@ApiModelProperty(value="类型", name="type")
    private Integer type;

	@ApiModelProperty(value="价格", name="price")
    private BigDecimal price;

	@ApiModelProperty(value="材质贴图", name="textureMapping")
    private Integer textureMapping;

	@ApiModelProperty(value="灯光效果", name="lightingEffects")
    private String lightingEffects;

	@ApiModelProperty(value="状态", name="status")
    private Integer status;

	@ApiModelProperty(value="浏览次数", name="visitsNum")
    private Integer visitsNum;

	@ApiModelProperty(value="下载次数", name="downloadNum")
    private Integer downloadNum;
	
	@ApiModelProperty(value="上传用户", name="uploadUser")
	private String uploadUser;
	
	@ApiModelProperty(value="上传时间", name="uploadTime")
	private String uploadTime;
	
}
