package com.pactera.znzmo.profit;

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
 * 收益明细表
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbProfitDetails implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 收益类型
     */
    private Integer profitType;

    /**
     * 操作时间
     */
    private LocalDateTime operationTime;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 提现单号
     */
    private Long orderId;

    /**
     * 单号编码
     */
    private String orderCode;

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

    public static final String USER_NAME = "user_name";

    public static final String USER_ID = "user_id";

    public static final String PROFIT_TYPE = "profit_type";

    public static final String OPERATION_TIME = "operation_time";

    public static final String AMOUNT = "amount";

    public static final String MOBILE = "mobile";

    public static final String REMARKS = "remarks";

    public static final String ORDER_ID = "order_id";

    public static final String ORDER_CODE = "order_code";

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
