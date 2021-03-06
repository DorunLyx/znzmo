package com.pactera.znzmo.vo.model;

import java.math.BigDecimal;
import java.util.List;

import com.pactera.znzmo.vo.common.UploadInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：ModelDetailsVO
 * @Description：3d模型详情VO
 * @author liyongxu 
 * @date 2020年8月24日 下午4:08:46 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="3d模型详情VO",description="3d模型详情VO")
public class ModelDetailsVO {

	@ApiModelProperty(value="3d模板Id", name="modelId")
	private String modelId;

    @ApiModelProperty(value="上传文件", name="uploadImg")
    private List<UploadInfo> uploadImg;

	@ApiModelProperty(value="标题", name="title")
    private String title;
	
	@ApiModelProperty(value="浏览次数", name="visitsNum")
    private Integer visitsNum;
	
	@ApiModelProperty(value="下载次数", name="downloadNum")
	private Integer downloadNum;
	
	@ApiModelProperty(value="收藏次数", name="collectionNum")
	private Integer collectionNum;
	
	@ApiModelProperty(value="更新时间", name="updatetTime")
	private String updatetTime;
	
	@ApiModelProperty(value="文件大小", name="fileSize")
	private String fileSize;

	@ApiModelProperty(value="风格", name="styleName")
    private String styleName;

	@ApiModelProperty(value="材质贴图", name="textureMapping")
    private Integer textureMapping;
	
	@ApiModelProperty(value="模型类型", name="type")
    private Integer type;

	@ApiModelProperty(value="灯光效果", name="lightingEffects")
    private String lightingEffects;
	
	@ApiModelProperty(value="模型价格", name="price")
	private BigDecimal price;
	
	@ApiModelProperty(value="MAX版本", name="MAXVersion")
	private String MAXVersion;
}
