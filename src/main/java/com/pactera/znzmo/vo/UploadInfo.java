package com.pactera.znzmo.vo;

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

	@ApiModelProperty(value="类型", name="type")
	private String type;
	
    @ApiModelProperty(value="原名称", name="realName")
    private String realName;
    
    @ApiModelProperty(value="文件名称", name="fileName")
    private String fileName;
    
    @ApiModelProperty(value="文件地址", name="file")
    private String file;
    
    @ApiModelProperty(value="原路径", name="realPath")
    private String realPath;

    @ApiModelProperty(value="url", name="url")
    private String url;

}
