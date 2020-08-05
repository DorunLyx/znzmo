package com.pactera.znzmo.hd;

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
 * 高清贴图表
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbHdMapping implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 主图
     */
    private String mainGraph;

    /**
     * 编号
     */
    private String code;

    /**
     * 分类id
     */
    private Long primaryClassId;

    /**
     * 分类名称
     */
    private String primaryClassName;

    /**
     * 分类id
     */
    private Long secondaryClassId;

    /**
     * 分类名称
     */
    private String secondaryClassName;

    /**
     * 设计风格id
     */
    private Long styleId;

    /**
     * 风格
     */
    private String styleName;

    /**
     * 标题
     */
    private String title;

    /**
     * 贴图类型
     */
    private Integer mappingType;

    /**
     * 贴图价格
     */
    private BigDecimal mappingPrice;

    /**
     * 是否品牌贴图
     */
    private Integer brandMapping;

    /**
     * 贴图品牌
     */
    private String mappingName;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 浏览次数
     */
    private Integer visitsNum;

    /**
     * 下载次数
     */
    private Integer downloadNum;

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

    public static final String MAIN_GRAPH = "main_graph";

    public static final String CODE = "code";

    public static final String PRIMARY_CLASS_ID = "primary_class_id";

    public static final String PRIMARY_CLASS_NAME = "primary_class_name";

    public static final String SECONDARY_CLASS_ID = "secondary_class_id";

    public static final String SECONDARY_CLASS_NAME = "secondary_class_name";

    public static final String STYLE_ID = "style_id";

    public static final String STYLE_NAME = "style_name";

    public static final String TITLE = "title";

    public static final String MAPPING_TYPE = "mapping_type";

    public static final String MAPPING_PRICE = "mapping_price";

    public static final String BRAND_MAPPING = "brand_mapping";

    public static final String MAPPING_NAME = "mapping_name";

    public static final String STATUS = "status";

    public static final String VISITS_NUM = "visits_num";

    public static final String DOWNLOAD_NUM = "download_num";

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
