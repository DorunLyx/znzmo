package com.pactera.znzmo.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：DownloadExcelParam
 * @Description：下载Excel参数
 * @author liyongxu 
 * @date 2019年12月10日 下午3:05:57 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="下载Excel参数",description="下载Excel参数")
public class DownloadExcelParam {
	
	@ApiModelProperty(value="下载类型(1.资产导入,2.资产Sku导入,3.供应商导入,4.品牌导入)", name="type")
	private String type;

}
