
package com.pactera.znzmo.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @ClassName：Constants
 * @Description：TODO(这里用一句话描述这个类的作用)
 * @author LiuGuiChao
 * @date 2019年11月4日 上午11:25:22
 * @version 1.0.0
 */
@Component
public class Constants {

	private static final Logger LOGGER = LogManager.getLogger(Constants.class);
	
	/**
	* @Fields SUB_LIST_SIZE : LIST截取长度
	*/
	public static final Integer SUB_LIST_SIZE = 2000;

	/** 默认页码 1 */
    public static final Integer PAGENUM = 1;

    /** 默认行数 10 */
    public static final Integer PAGESIZE = 10;

    /** 默认页码 1 */
    public static final String PAGE_NUM = "1";

    /** 默认行数 10 */
    public static final String PAGE_SIZE = "10";

    /** isDel 是否删除 0 未删除  */
    public static final Integer NODEL = 0;

    /** isDel 是否删除 1 已删除 */
    public static final Integer DEL = 1;

    /** DISABLED 是否禁用 0 禁用; */
    public static final Integer DISABLED = 0;

    /** ENABLED 是否禁用 1 启用; */
    public static final Integer ENABLED = 1;

    /** SCHEDULE 进度0; */
    public static final Integer SCHEDULE = 0;

    /** Number 进度0; */
    public static final Integer  NUMBER = 0;
    /** UN_REVIEW 未审核状态码 0 ; */
    public static final Integer UN_REVIEW = 0;

    /** IN_REVIEW 审核中状态码 1 ; */
    public static final Integer IN_REVIEW = 1;

    /** PASS_REVIEW 审核通过状态码 2 ; */
    public static final Integer PASS_REVIEW = 2;

    /** UNPASS_REVIEW 审核不通过状态码 3; */
    public static final Integer UNPASS_REVIEW = 3;

    /** IN_TRANSFERS 调拨中状态码 4 ; */
    public static final Integer IN_TRANSFERS = 4;

    /** COMPLETE_TRANSFERS 调拨完成状态码 5 ; */
    public static final Integer COMPLETE_TRANSFERS = 5;



    /** UN_REVIEW_EXPLAIN 未审核状态 ; */
    public static final String UN_REVIEW_EXPLAIN = "待审批";

    /** IN_REVIEW_EXPLAIN 审核中状态 ;*/
    public static final String IN_REVIEW_EXPLAIN = "审批中";

    /** PASS_REVIEW_EXPLAIN 审核通过状态; */
    public static final String PASS_REVIEW_EXPLAIN = "审批通过";

    /** UNPASS_REVIEW_EXPLAIN 审核不通过状态; */
    public static final String UNPASS_REVIEW_EXPLAIN = "审批不通过";

    /** IN_TRANSFERS_EXPLAIN 调拨中状态; */
    public static final String IN_TRANSFERS_EXPLAIN = "调拨中";

    /** COMPLETE_TRANSFERS_EXPLAIN 调拨完成状态; */
    public static final String COMPLETE_TRANSFERS_EXPLAIN = "调拨完成";

    /** IN_TEANSFER 调拨中 1 ; */
    public static final Integer IN_TEANSFER = 1;

    /** AL_TEANSFER 调拨完成 2 ; */
    public static final Integer AL_TEANSFER = 2;

    /** UN_CALL_IN 待调入 3; */
    public static final Integer UN_CALL_IN = 3;

    /** AL_CALL_IN 已调入 4 ; */
    public static final Integer AL_CALL_IN = 4;

    /** IN_TEANSFER 调拨中 1 ; */
    public static final String IN_TEANSFER_EXPLAIN = "调拨中";

    /** AL_TEANSFER 调拨完成 2 ; */
    public static final String AL_TEANSFER_EXPLAIN = "调拨完成";

    /** UN_CALL_IN 待调入 3; */
    public static final String UN_CALL_IN_EXPLAIN = "待调入";

    /** AL_CALL_IN 已调入 4 ; */
    public static final String AL_CALL_IN_EXPLAIN = "已调入";

    /** APPLICATION_FORM 调拨中 1 ; */
    public static final Integer APPLICATION_FORM = 0;

    /** CALL_IN 调入 1 ; */
    public static final Integer CALL_IN = 2;

    /** CALL_OUT 掉出 2 ; */
    public static final Integer CALL_OUT = 1;

    /** APPLICATION_FORM_EXPLAIN 申请单 1 ; */
    public static final String APPLICATION_FORM_EXPLAIN = "申请单";

    /** CALL_IN_EXPLAIN 调出 1 ; */
    public static final String CALL_IN_EXPLAIN = "调出";

    /** CALL_OUT_EXPLAIN 调入 2 ; */
    public static final String CALL_OUT_EXPLAIN = "调入";

    /** CALL_IN 未验收 0 ; */
    public static final Integer UN_CHECK = 3;

    /** CALL_OUT 已验收 1 ; */
    public static final Integer IS_CHECK = 4;

    /** CALL_OUT 已调出 2 ; */
    public static final Integer IS_CALLOUT = 2;

    /** UN_CHECK_EXPLAIN 未验收 0 ; */
    public static final String UN_CHECK_EXPLAIN = "未校验";

    /** IS_CHECK_EXPLAIN 已验收 1 ; */
    public static final String IS_CHECK_EXPLAIN = "已校验";

    /** IS_CHECK_EXPLAIN 已调出 2 ; */
    public static final String IS_CALLOUT_EXPLAIN = "已调出";

    /** UN_INVENTORY 未盘点 1; */
    public static final Integer UN_INVENTORY = 0;

    /** IN_INVENTORY 盘点中 1 ; */
    public static final Integer IN_INVENTORY = 1;

    /** AL_INVENTORY 盘点完成 2 ; */
    public static final Integer AL_INVENTORY = 2;



    /**
     * @Fields RESULT_FLAG : 返回是否成功标识--success
     */
     public static final String RESULT_FLAG = "success";

	 /**
     * 上传默认目录
     */
    public static final String UPLOAD_ROOT = "/files";

    /**
     * 上传默认目录
     */
    public static String UPLOAD_ROOT_ASSET = "assets";
 
    /**
     * 文件上传模式(server/nas)
     */
    public static String UPLOAD_MODE = "server";

    /**
     * 文件上传NAS盘目录
     */
    public static String UPLOAD_NAS_DIR = "/opt/upload";

    /**
     *  配置文件中上传赋值
     */
    public static String ACCESS_PATH = "";
    public static String UPLOAD_FOLDER = "";

    /**
     * 文件NAS访问根目录
     */
    public static String UPLOAD_NAS_ACCESS = "/nas";

    /**
     * EXCEL_TEMPLATE_COLUMN_COUNT : excel模板列数量
     */
     public static final Integer EXCEL_INIT_TEMPLATE_COLUMN_COUNT = 12;

    /**
     * EXCEL_TEMPLATE_COLUMN_COUNT : skuExcel模板列数量
     */
    public static final Integer SKU_EXCEL_INIT_TEMPLATE_COLUMN_COUNT = 8;

    /**
     * EXCEL_TEMPLATE_COLUMN_COUNT : SupplierExcel模板列数量
     */
    public static final Integer SUPPLIER_EXCEL_INIT_TEMPLATE_COLUMN_COUNT = 6;

    /**
     * EXCEL_TEMPLATE_COLUMN_COUNT : brnadExcel模板列数量
     */
    public static final Integer BRAND_EXCEL_INIT_TEMPLATE_COLUMN_COUNT = 5;

    /**
     * EXCEL_TEMPLATE_COLUMN_COUNT : scrapAssetExcel模板列数量
     */
    public static final Integer SCRAPASSET_EXCEL_INIT_TEMPLATE_COLUMN_COUNT = 2;

     /**
     * EXCEL_TEMPLATE_DEFAULT_SHEET : excel模板默认工作表 0 未起始表
     */
     public static final Integer EXCEL_TEMPLATE_DEFAULT_SHEET = 0;

     /**
     * @Fields EXCEL_TEMPLATE_TITLE_ROW : 模板默认行
     */
     public static final Integer EXCEL_TEMPLATE_TITLE_ROW = 2;

     /**
      * 方法描述：读取常量配置
      * 创建人：admin
      */
     synchronized static public void loadConfig() {
     	LOGGER.info("读取配置文件中的常量 Begin...");
         InputStream is = Constants.class.getResourceAsStream("/config.properties");
         Properties properties = new Properties();
         try {
         	properties.load(new InputStreamReader(is, "UTF-8"));
         	if (properties.getProperty("upload.mode") != null) {
         		UPLOAD_MODE = properties.getProperty("upload.mode");
             }
         	if (properties.getProperty("upload.nas.dir") != null) {
         		UPLOAD_NAS_DIR = properties.getProperty("upload.nas.dir");
             }
         	if (properties.getProperty("upload.nas.access") != null) {
         		UPLOAD_NAS_ACCESS = properties.getProperty("upload.nas.access");
             }
 		} catch (Exception e) {
 			LOGGER.error("读取常量配置文件异常:",e);
 		}
         LOGGER.info("读取配置文件中的常量 End...");
     }

}
