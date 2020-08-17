package com.pactera.znzmo.vo.classify;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName：ClassifyQueryDetailsParam
 * @Description：分类查询详情参数
 * @author liyongxu 
 * @date 2020年8月17日 上午10:19:26 
 * @version 1.0.0 
 */
@Data
@ApiModel(value="分类查询详情参数",description="分类查询详情参数")
public class ClassifyQueryDetailsParam {

	@ApiModelProperty(value="分类id", name="classifyId")
    private Long classifyId;
	
}
