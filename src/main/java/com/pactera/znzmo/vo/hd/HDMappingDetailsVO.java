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
	private String hdId;

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

	@ApiModelProperty(value="图片尺寸", name="imagesize")
    private String imageSize;

	@ApiModelProperty(value="文件格式", name="fileFormat")
    private String fileFormat;
	
	@ApiModelProperty(value="价格", name="price")
	private BigDecimal price;
}
