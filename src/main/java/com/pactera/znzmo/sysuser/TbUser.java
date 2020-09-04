package com.pactera.znzmo.sysuser;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户管理表
 * </p>
 *
 * @author liyongxu
 * @since 2020-09-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbUser implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键标识
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户头像
     */
    private String logourl;

    /**
     * 性别（0-男；1-女；2-保密）
     */
    private Integer sex;

    /**
     * 出生日期
     */
    private LocalDateTime birthday;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 家庭电话
     */
    private String homePhone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * QQ号绑定
     */
    private Long qqId;

    /**
     * 微信号绑定
     */
    private String wechatId;

    /**
     * 支付宝号
     */
    private String alipayId;

    /**
     * 用户类型
     */
    private Integer type;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录ip
     */
    private String lastLoginIp;

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

    public static final String REAL_NAME = "real_name";

    public static final String PASSWORD = "password";

    public static final String LOGOURL = "logourl";

    public static final String SEX = "sex";

    public static final String BIRTHDAY = "birthday";

    public static final String MOBILE = "mobile";

    public static final String HOME_PHONE = "home_phone";

    public static final String EMAIL = "email";

    public static final String QQ_ID = "qq_id";

    public static final String WECHAT_ID = "wechat_id";

    public static final String ALIPAY_ID = "alipay_id";

    public static final String TYPE = "type";

    public static final String LAST_LOGIN_TIME = "last_login_time";

    public static final String LAST_LOGIN_IP = "last_login_ip";

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
