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
 * 日志记录表
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbLog implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 操作人id
     */
    private Long operatorId;

    /**
     * 操作人账号
     */
    private String operatorAccount;

    /**
     * 操作人名称
     */
    private String operatorName;

    /**
     * 标题
     */
    private String title;

    /**
     * 模块
     */
    private String module;

    /**
     * IP地址
     */
    private String ip;

    /**
     * URL路径
     */
    private String url;

    /**
     * 操作事件
     */
    private String event;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    public static final String ID = "id";

    public static final String OPERATOR_ID = "operator_id";

    public static final String OPERATOR_ACCOUNT = "operator_account";

    public static final String OPERATOR_NAME = "operator_name";

    public static final String TITLE = "title";

    public static final String MODULE = "module";

    public static final String IP = "ip";

    public static final String URL = "url";

    public static final String EVENT = "event";

    public static final String CONTENT = "content";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

}
