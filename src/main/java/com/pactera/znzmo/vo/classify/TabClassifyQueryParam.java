package com.pactera.znzmo.vo.classify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：TabClassifyQueryParam
 * @Description：标签分类查询参数
 * @author liyongxu 
 * @date 2020年9月3日 上午11:04:32 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="标签分类查询参数",description="标签分类查询参数")
public class TabClassifyQueryParam {

	@ApiModelProperty(value="标签名称", name="name")
    private String name;
	
}
