package com.pactera.znzmo.financialstat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 财务统计表
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbFinancialStatistics implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 总收益
     */
    private BigDecimal totalRevenue;

    /**
     * 提现总额
     */
    private BigDecimal totalWithdrawal;

    /**
     * 当账户总额
     */
    private BigDecimal totalAccountAmount;

    /**
     * 平台总收益
     */
    private BigDecimal totalPlatformRevenue;

    /**
     * 3D模型总收入
     */
    private BigDecimal modelTotalRevenue;

    /**
     * SU模型总收入
     */
    private BigDecimal suTotalRevenue;

    /**
     * 图纸总收入
     */
    private BigDecimal drawingTotalRevenue;

    /**
     * 资料库总收入
     */
    private BigDecimal databaseTotalRevenue;

    /**
     * 贴图总收入
     */
    private BigDecimal mappingTotalRevenue;

    /**
     * 订单交易汇总
     */
    private BigDecimal orderTransactionSum;

    /**
     * 年度VIP用户数
     */
    @TableField("annual_VIP_users")
    private BigDecimal annualVipUsers;

    /**
     * 每月新增用户
     */
    private BigDecimal monthlyNewUsers;

    /**
     * 每月新增VIP
     */
    @TableField("monthly_new_VIP")
    private BigDecimal monthlyNewVip;

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

    public static final String TOTAL_AMOUNT = "total_amount";

    public static final String TOTAL_REVENUE = "total_revenue";

    public static final String TOTAL_WITHDRAWAL = "total_withdrawal";

    public static final String TOTAL_ACCOUNT_AMOUNT = "total_account_amount";

    public static final String TOTAL_PLATFORM_REVENUE = "total_platform_revenue";

    public static final String MODE_TOTAL_REVENUE = "model_total_revenue";

    public static final String SU_TOTAL_REVENUE = "su_total_revenue";

    public static final String DRAWING_TOTAL_REVENUE = "drawing_total_revenue";

    public static final String DATABASE_TOTAL_REVENUE = "database_total_revenue";

    public static final String MAPPING_TOTAL_REVENUE = "mapping_total_revenue";

    public static final String ORDER_TRANSACTION_SUM = "order_transaction_sum";

    public static final String ANNUAL_VIP_USERS = "annual_VIP_users";

    public static final String MONTHLY_NEW_USERS = "monthly_new_users";

    public static final String MONTHLY_NEW_VIP = "monthly_new_VIP";

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
