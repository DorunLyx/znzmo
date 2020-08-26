package com.pactera.znzmo.vo.personal;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：DownloadRecordsVO
 * @Description：我的下载列表VO
 * @author liyongxu 
 * @date 2020年8月26日 上午11:01:08 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="我的下载列表VO",description="我的下载列表VO")
public class DownloadRecordsVO {

	@ApiModelProperty(value="审核Id", name="examineId")
	private String examineId;

	@ApiModelProperty(value="主图地址", name="mainGraph")
    private String mainGraph;

	@ApiModelProperty(value="编号", name="code")
    private String code;
	
	@ApiModelProperty(value="分类", name="className")
	private String className;

	@ApiModelProperty(value="一级分类名称", name="primaryClassName")
    private String primaryClassName;

	@ApiModelProperty(value="风格", name="styleName")
    private String styleName;

	@ApiModelProperty(value="标题", name="title")
    private String title;

	@ApiModelProperty(value="类型", name="type")
    private Integer type;
	
	@ApiModelProperty(value="上传用户", name="uploadUser")
	private String uploadUser;
	
	@ApiModelProperty(value="上传时间", name="uploadTime")
	private String uploadTime;

	@ApiModelProperty(value="价格", name="price")
    private BigDecimal price;
	
	@ApiModelProperty(value="状态", name="status")
    private Integer status;
	
}
