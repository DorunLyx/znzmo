package com.pactera.znzmo.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：UploadInfo
 * @Description：上传信息
 * @author liyongxu 
 * @date 2020年8月6日 上午11:20:37 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="上传信息",description="上传信息")
public class UploadInfo {

	@ApiModelProperty(value="类型(0-上传模型/附件，1-主图/封面，2-上传角度)", name="type")
	private Integer type;
	
    @ApiModelProperty(value="原名称", name="realName")
    private String realName;
    
    @ApiModelProperty(value="原路径", name="realPath")
    private String realPath;
    
    @ApiModelProperty(value="文件名称", name="fileName")
    private String fileName;
    
    @ApiModelProperty(value="文件地址", name="file")
    private String file;

    @ApiModelProperty(value="url", name="url")
    private String url;
    
    @ApiModelProperty(value="文件后缀", name="fileSuffix")
    private String fileSuffix;
    
    @ApiModelProperty(value="文件大小", name="sizes")
    private String sizes;
    
    @ApiModelProperty(value="图片尺寸", name="pictureSize")
    private String pictureSize;
}
