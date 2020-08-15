package com.pactera.znzmo.drawing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 图纸方案表
 * </p>
 *
 * @author liyongxu
 * @since 2020-08-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbDrawingScheme implements Serializable {

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
     * 图纸名称
     */
    private String name;

    /**
     * 标题
     */
    private String title;

    /**
     * 模型类型
     */
    private Integer type;

    /**
     * 模型价格
     */
    private BigDecimal price;

    /**
     * 材质贴图
     */
    private Integer textureMapping;

    /**
     * 标签
     */
    private String tag;

    /**
     * 设计时间
     */
    private LocalDateTime designTime;

    /**
     * 项目位置
     */
    private Integer location;

    /**
     * 名师合辑
     */
    private String famousCollection;

    /**
     * 意向价格
     */
    private BigDecimal intentionlPrice;

    /**
     * 选择版本
     */
    private String version;

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
     * 简介
     */
    private String synopsis;

    /**
     * 正文
     */
    private String text;

    /**
     * 备注
     */
    private String remarks;

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

    public static final String NAME = "name";

    public static final String TITLE = "title";

    public static final String TYPE = "type";

    public static final String PRICE = "price";

    public static final String TEXTURE_MAPPING = "texture_mapping";

    public static final String TAG = "tag";

    public static final String DESIGN_TIME = "design_time";

    public static final String LOCATION = "location";

    public static final String FAMOUS_COLLECTION = "famous_collection";

    public static final String INTENTIONL_PRICE = "intentionl_price";

    public static final String VERSION = "version";

    public static final String STATUS = "status";

    public static final String VISITS_NUM = "visits_num";

    public static final String DOWNLOAD_NUM = "download_num";

    public static final String SYNOPSIS = "synopsis";

    public static final String TEXT = "text";

    public static final String REMARKS = "remarks";

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
