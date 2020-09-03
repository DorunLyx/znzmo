package com.pactera.znzmo.classify;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author liyongxu
 * @since 2020-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbTabClass implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * tab名称
     */
    private String name;

    /**
     * 分类id
     */
    private Long classId;

    /**
     * 分类名称
     */
    private String className;


    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String CLASS_ID = "class_id";

    public static final String CLASS_NAME = "class_name";

}
