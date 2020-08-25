package com.pactera.znzmo.vo.su;

import java.math.BigDecimal;
import java.util.List;

import com.pactera.znzmo.vo.common.UploadInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：SuModelDetailsVO
 * @Description：su模型下载页详情VO
 * @author liyongxu 
 * @date 2020年8月24日 下午5:27:56 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="su模型下载页详情VO",description="su模型下载页详情VO")
public class SuModelDetailsVO {

	@ApiModelProperty(value="su模板Id", name="suModelId")
	private String suModelId;

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
	
	@ApiModelProperty(value="su版本", name="suVersion")
	private String suVersion;
	
	@ApiModelProperty(value="模型价格", name="price")
	private BigDecimal price;
}
