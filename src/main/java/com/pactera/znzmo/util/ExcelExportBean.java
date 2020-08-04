 package com.pactera.znzmo.util;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：ExcelExportBean
 * @Description：TODO(这里用一句话描述这个类的作用) 
 * @author liyongxu 
 * @date 2020年8月4日 上午9:55:54 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="excel导出信息",description="excel导出信息")
public class ExcelExportBean {

	@ApiModelProperty(value="访问相对路径",name="accessRelativeUrl")
	private String accessRelativeUrl;

	@ApiModelProperty(value="访问绝对路径",name="accessAbsoluteUrl")
	private String accessAbsoluteUrl;

	@ApiModelProperty(value="物理绝对路径",name="physicAbsoluteUrl")
	private String physicAbsoluteUrl;

	@ApiModelProperty(value="文件名称",name="errcode")
	private String fileName;
}
