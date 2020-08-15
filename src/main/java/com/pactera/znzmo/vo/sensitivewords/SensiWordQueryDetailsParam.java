/**
 * 
 */
package com.pactera.znzmo.vo.sensitivewords;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lenovo
 *
 */
@Data
@ApiModel(value="敏感词参数",description="敏感词参数")
public class SensiWordQueryDetailsParam {

	@ApiModelProperty(value="id", name="id")
    private Long id;
	

}
