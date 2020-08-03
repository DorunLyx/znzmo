package com.pactera.znzmo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 权限资源管理表
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbAuth implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键标识
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 权限编码
     */
    private String code;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限资源路径
     */
    private String url;

    /**
     * 父级id
     */
    private String parentid;

    /**
     * 资源图标
     */
    private String icon;

    /**
     * 权限资源描述
     */
    private String description;

    /**
     * 资源类别(1按钮权限，2页面权限)
     */
    private Integer resourceType;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 是否有效(0--有效；1--无效)
     */
    private Integer isValid;


    public static final String ID = "id";

    public static final String CODE = "code";

    public static final String NAME = "name";

    public static final String URL = "url";

    public static final String PARENTID = "parentid";

    public static final String ICON = "icon";

    public static final String DESCRIPTION = "description";

    public static final String RESOURCE_TYPE = "resource_type";

    public static final String SEQ = "seq";

    public static final String IS_VALID = "is_valid";

}
