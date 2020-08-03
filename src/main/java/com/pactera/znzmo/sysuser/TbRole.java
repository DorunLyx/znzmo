package com.pactera.znzmo.sysuser;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色管理表
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbRole implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键标识
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 创建者
     */
    private Long creatId;

    /**
     * 创建时间
     */
    private LocalDateTime creatDate;

    /**
     * 修改者
     */
    private Long updateId;

    /**
     * 修改时间
     */
    private LocalDateTime updateDate;

    /**
     * 是否有效(0--有效；1--无效)
     */
    private Integer isValid;


    public static final String ID = "id";

    public static final String CODE = "code";

    public static final String NAME = "name";

    public static final String DESCRIPTION = "description";

    public static final String SEQ = "seq";

    public static final String CREAT_ID = "creat_id";

    public static final String CREAT_DATE = "creat_date";

    public static final String UPDATE_ID = "update_id";

    public static final String UPDATE_DATE = "update_date";

    public static final String IS_VALID = "is_valid";

}
