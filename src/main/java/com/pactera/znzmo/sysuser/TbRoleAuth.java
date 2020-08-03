package com.pactera.znzmo.sysuser;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 权限角色关联表
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbRoleAuth implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键标识
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色标识
     */
    private Long roleId;

    /**
     * 权限标识
     */
    private Long authId;


    public static final String ID = "id";

    public static final String ROLE_ID = "role_id";

    public static final String AUTH_ID = "auth_id";

}
