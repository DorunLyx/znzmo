package com.pactera.znzmo.vo.drawing;

import java.math.BigDecimal;
import java.util.List;

import com.pactera.znzmo.vo.common.UploadInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：DrawingDetailsVO
 * @Description：图纸方案详情VO
 * @author liyongxu 
 * @date 2020年8月4日 上午11:21:33 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="图纸方案详情VO",description="图纸方案详情VO")
public class DrawingDetailsVO {

	@ApiModelProperty(value="图纸Id", name="drawingId")
	private String drawingId;

	@ApiModelProperty(value="主图地址", name="mainGraph")
    private String mainGraph;

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
	
	@ApiModelProperty(value="图纸版本", name="version")
	private String version;

	@ApiModelProperty(value="文件格式", name="fileFormat")
    private String fileFormat;
	
	@ApiModelProperty(value="价格", name="price")
	private BigDecimal price;
}
