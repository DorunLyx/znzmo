package com.pactera.znzmo.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 公共附件表
 * </p>
 *
 * @author liyongxu
 * @since 2020-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbAttachment implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关联外键id
     */
    private Long relationId;

    /**
     * 关联业务类型
     */
    private Integer reType;

    /**
     * 上传版本
     */
    private String uploadVersion;

    /**
     * 图片类型
     */
    private Integer imgType;

    /**
     * 附件名称
     */
    private String attachmentName;

    /**
     * 附件物理路径
     */
    private String physicalPath;

    /**
     * 附件路径
     */
    private String attachmentPath;

    /**
     * 后缀
     */
    private String suffix;

    /**
     * 附件别名
     */
    private String aliasName;

    /**
     * 附件大小
     */
    private String attachmentSize;

    /**
     * 附件描述
     */
    private String attachmentDesc;

    /**
     * 图片尺寸
     */
    private String pictureSize;

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

    public static final String RELATION_ID = "relation_id";

    public static final String RE_TYPE = "re_type";

    public static final String UPLOAD_VERSION = "upload_version";

    public static final String IMG_TYPE = "img_type";

    public static final String ATTACHMENT_NAME = "attachment_name";

    public static final String PHYSICAL_PATH = "physical_path";

    public static final String ATTACHMENT_PATH = "attachment_path";

    public static final String SUFFIX = "suffix";

    public static final String ALIAS_NAME = "alias_name";

    public static final String ATTACHMENT_SIZE = "attachment_size";

    public static final String ATTACHMENT_DESC = "attachment_desc";

    public static final String PICTURE_SIZE = "picture_size";

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
