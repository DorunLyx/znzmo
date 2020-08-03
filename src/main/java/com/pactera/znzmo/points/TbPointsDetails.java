package com.pactera.znzmo.points;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 积分明细表
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbPointsDetails implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 积分表id
     */
    private Long pointsId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 消耗积分
     */
    private BigDecimal consumePoints;

    /**
     * 当前积分
     */
    private BigDecimal currentPoints;

    /**
     * 总积分
     */
    private BigDecimal totalPoints;

    /**
     * 积分兑换套餐
     */
    private Integer exchangePackage;

    /**
     * 兑换时间
     */
    private LocalDateTime exchangeTime;

    /**
     * 是否有效(0.无效,1.有效)
     */
    private Integer isValid;

    /**
     * 创建人id
     */
    private Long createId;

    /**
     * 创建账号
     */
    private String createAccount;

    /**
     * 创建人名称
     */
    private String createName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人id
     */
    private Long updateId;

    /**
     * 更新人账号
     */
    private String updateAccount;

    /**
     * 更新人名称
     */
    private String updateName;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    public static final String ID = "id";

    public static final String POINTS_ID = "points_id";

    public static final String USER_NAME = "user_name";

    public static final String USER_ID = "user_id";

    public static final String CONSUME_POINTS = "consume_points";

    public static final String CURRENT_POINTS = "current_points";

    public static final String TOTAL_POINTS = "total_points";

    public static final String EXCHANGE_PACKAGE = "exchange_package";

    public static final String EXCHANGE_TIME = "exchange_time";

    public static final String IS_VALID = "is_valid";

    public static final String CREATE_ID = "create_id";

    public static final String CREATE_ACCOUNT = "create_account";

    public static final String CREATE_NAME = "create_name";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_ID = "update_id";

    public static final String UPDATE_ACCOUNT = "update_account";

    public static final String UPDATE_NAME = "update_name";

    public static final String UPDATE_TIME = "update_time";

}
