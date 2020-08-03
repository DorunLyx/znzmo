package com.pactera.znzmo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 数据字典表
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbDictionaryData implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 数据编码
     */
    private String code;

    /**
     * 数据名称
     */
    private String name;

    /**
     * 数据类型（关联外键）
     */
    private String dataType;

    /**
     * 数据value值
     */
    private String value;

    /**
     * 数据描述
     */
    private String description;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 是否有子级（0-无；1-有）
     */
    private Integer hasChild;

    /**
     * 是否可用（0-不可用；1-可用）
     */
    private Integer disabled;

    /**
     * 级别
     */
    private Integer level;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    public static final String ID = "id";

    public static final String CODE = "code";

    public static final String NAME = "name";

    public static final String DATA_TYPE = "data_type";

    public static final String VALUE = "value";

    public static final String DESCRIPTION = "description";

    public static final String PARENT_ID = "parent_id";

    public static final String HAS_CHILD = "has_child";

    public static final String DISABLED = "disabled";

    public static final String LEVEL = "level";

    public static final String SORT = "sort";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

}
