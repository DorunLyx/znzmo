package com.pactera.znzmo.vo.database;

import java.math.BigDecimal;
import java.util.List;

import com.pactera.znzmo.vo.common.UploadInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：DatabaseUpdateParam
 * @Description：资料库编辑参数
 * @author liyongxu 
 * @date 2020年8月13日 下午2:14:46 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="资料库编辑参数",description="资料库编辑参数")
public class DatabaseUpdateParam {

	@ApiModelProperty(value="资料库Id", name="databaseId")
	private String databaseId;
	
	@ApiModelProperty(value="主图地址id", name="mainGraphId")
	private String mainGraph;

    @ApiModelProperty(value="上传文件", name="uploadImg")
    private List<UploadInfo> uploadImg;

	@ApiModelProperty(value="标题", name="title")
	private String title;
	
	@ApiModelProperty(value="标签", name="tag")
	private String tag;
	
	@ApiModelProperty(value="风格id", name="styleId")
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

	@ApiModelProperty(value="资料类型", name="type")
    private Integer type;

	@ApiModelProperty(value="价格", name="price")
    private BigDecimal price;

	@ApiModelProperty(value="图纸版本", name="version")
    private String version;
	
	@ApiModelProperty(value="设计时间", name="designTime")
	private String designTime;
	
	@ApiModelProperty(value="项目位置", name="location")
	private Integer location;
	
	@ApiModelProperty(value="简介", name="synopsis")
	private String synopsis;
	
	@ApiModelProperty(value="正文介绍", name="text")
	private String text;

	@ApiModelProperty(value="备注", name="remarks")
    private String remarks;
	
}
