/**
 * 项目名：泰禾佣金
 * 包名：com.tahoecn.common.utils
 * 文件名：ExportUtil.java
 * 版本信息：1.0.0
 * 日期：2019年3月6日-上午10:59:57
 * Copyright (c) 2019 Pactera 版权所有
 */
 package com.pactera.znzmo.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;

/**
 * @ClassName：ExportUtil
 * @Description：easypoi 导出操作类（未实现模板导出）
 * @author LiuGuiChao
 * @date 2019年3月6日 上午10:59:57
 * @version 1.0.0
 */
public class ExcelUtils {
	
	public static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

	protected static final String[] EXCEL_EXT = {"xls","xlsx"};

    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass,String fileName,boolean isCreateHeader, HttpServletResponse response){
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);

    }
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass,String fileName, HttpServletResponse response, HttpServletRequest request){
        defaultExport(list, pojoClass, fileName, response, request, new ExportParams(title, sheetName));
    }

    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass,String fileName, HttpServletResponse response){
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }

    /**
     * @Title: getExcel
     * @Description: 向本地生成Excel，并获取Excel信息
     * @param request Http请求
     * @param list 数据信息
     * @param title 标题信息
     * @param sheetName 工作簿信息
     * @param pojoClass
     * @return File
     * @author LiuGuiChao
     * @date 2019年3月18日 下午2:32:42
    */
    public static ExcelExportBean getExcel(HttpServletRequest request, List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName){
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(title, sheetName),pojoClass,list);
        ExcelExportBean excelExportBean = new ExcelExportBean();
        if (workbook != null){
        	try {
            	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

            	//获取物理路径
            	String serverRealPath =  FileUtils.getRealPath(request);
            	//拼接文件夹相对路径
                String dirRelativePath = "exportExcel" + "/"  + sdf.format(new Date());
                //获取文件夹绝对路径
                String dirAbsolutePath = serverRealPath + dirRelativePath;
                //创建文件夹
                File fileDir = new File(dirAbsolutePath);
                if(!fileDir.exists()){
                    fileDir.mkdirs();
                }

                //创建文件
                File exceFile = new File(dirAbsolutePath + "/" + fileName);

                //获取文件输出流
                FileOutputStream fileOs = new FileOutputStream(exceFile);
                
                //生成Excel文件
                workbook.write(fileOs);

                String relativeUrl =   dirRelativePath + "/" + fileName;
                String domain = getAccessDomain(request);
                String accessUrl = domain +"/assetManage/"+relativeUrl;

                excelExportBean.setAccessAbsoluteUrl(accessUrl);
                excelExportBean.setAccessRelativeUrl("/" + relativeUrl);
                excelExportBean.setPhysicAbsoluteUrl((dirAbsolutePath + "/" + fileName).replaceAll("/", "\\\\"));
                excelExportBean.setFileName(fileName);
            } catch (IOException e) {
            	logger.error(e.getMessage(),e);
            }
        }
        return excelExportBean;
    }


    /**
     * @Title: getExcel
     * @Description: 向本地生成Excel，并获取Excel信息
     * @param request Http请求
     * @param list 数据信息
     * @param title 标题信息
     * @param sheetName 工作簿信息
     * @param pojoClass
     * @return File
     * @author LiuGuiChao
     * @date 2019年3月18日 下午2:32:42
     */
    public static ExcelExportBean getModelExcel(HttpServletRequest request, List<?>  list, String title, String sheetName, Class<?> pojoClass, String fileName){
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(title, sheetName),pojoClass,list);
        ExcelExportBean excelExportBean = new ExcelExportBean();
        if (workbook != null){
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

                //获取物理路径
                String serverRealPath =  FileUtils.getRealPath(request);
                //拼接文件夹相对路径
                String dirRelativePath = "exportExcel" + "/"  + sdf.format(new Date());
                //获取文件夹绝对路径
                String dirAbsolutePath = serverRealPath + dirRelativePath;
                //创建文件夹
                File fileDir = new File(dirAbsolutePath);
                if(!fileDir.exists()){
                    fileDir.mkdirs();
                }

                //创建文件
                File exceFile = new File(dirAbsolutePath + "/" + fileName);

                //获取文件输出流
                FileOutputStream fileOs = new FileOutputStream(exceFile);
                HSSFFont redFont = (HSSFFont) workbook.createFont();
                redFont.setColor(Font.COLOR_RED);
                //生成Excel文件
                workbook.write(fileOs);

                String relativeUrl =   dirRelativePath + "/" + fileName;
                String domain = getAccessDomain(request);
                String accessUrl = domain +"/assetManage/"+relativeUrl;

                excelExportBean.setAccessAbsoluteUrl(accessUrl);
                excelExportBean.setAccessRelativeUrl("/" + relativeUrl);
                excelExportBean.setPhysicAbsoluteUrl((dirAbsolutePath + "/" + fileName).replaceAll("/", "\\\\"));
                excelExportBean.setFileName(fileName);
            } catch (IOException e) {
            	logger.error(e.getMessage(),e);
            }
        }
        return excelExportBean;
    }

    /**
     * 获取文件访问域名
     * @return
     */
    public static String getAccessDomain(HttpServletRequest request){
//    	String accessDomain = UIHelper.getWebRoot(request);
        String accessDomain = request.getHeader("Origin");
    	String mode = PropConfigUtil.getProperty("upload.mode");
    	if("nas".equals(mode)){
    		String domain = UIHelper.getWebDomain(request);
    		accessDomain = domain + PropConfigUtil.getProperty("upload.nas.access");
    	}
    	return accessDomain;
    }
    /**
     * 一个excel 创建多个sheet
     *
     * @param list 多个Map key title 对应表格Title key entity 对应表格对应实体 key data
     *            Collection 数据
     * @param fileName
     * @param response
     */
    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response){
    	defaultExport(list, fileName, response);
    }

    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {
    	Workbook workbook = ExcelExportUtil.exportExcel(exportParams,pojoClass,list);
        if (workbook != null);
        downLoadExcel(fileName, response, workbook);
    }

    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, HttpServletRequest request,ExportParams exportParams) {
    	Workbook workbook = ExcelExportUtil.exportExcel(exportParams,pojoClass,list);
        if (workbook != null);
        downLoadExcel(fileName, response, request, workbook);
    }

    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
        	response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
        	logger.error(e.getMessage(),e);
        }
    }

    private static void downLoadExcel(String fileName, HttpServletResponse response, HttpServletRequest request, Workbook workbook) {
        try {
        	/*response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));*/
        	response.setCharacterEncoding("UTF-8");
        	//解决下载中文乱码
            if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            }
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
        	logger.error(e.getMessage(),e);
        }
    }
    /**
     * 一个excel 创建多个sheet
     *
     * @param list 多个Map key title 对应表格Title key entity 对应表格对应实体 key data
     *            Collection 数据
     * @param fileName
     * @param response
     */
    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
    	Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
    	if (workbook != null);
        downLoadExcel(fileName, response, workbook);
    }

    public static <T> List<T> importExcel(String filePath,Integer titleRows,Integer headerRows, Class<T> pojoClass){
        if (StringUtils.isBlank(filePath)){
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (Exception e) {
        }
        return list;
    }

    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass){
        if (file == null){
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        }catch (Exception e) {
        	logger.error(e.getMessage(),e);
        }
        return list;
    }

    /**
     * 检查文件名是否符合要求
     * @param fileName 待监测文件名
     * @param exts 后缀名集合
     * @return
     */
    public static boolean checkExt(String fileName,String[] exts){
        if (StringUtils.isBlank(fileName))
        	return false;
        if (exts == null || exts.length == 0)
        	return false;
        String ext = fileName.substring(fileName.lastIndexOf(".")+1);
        for (int i = 0;i<exts.length;i++){
            if (ext.equals(exts[i])){
                return true;
            }
        }
        return false;
    }

    /**
     * @Title: getBytes 
     * @Description: 获取byte[]
     * @param request
     * @param list
     * @param title
     * @param sheetName
     * @param pojoClass
     * @param fileName
     * @return byte[]
     * @date 2020年1月9日 下午8:56:45 
    */
    public static byte[] getBytes(List<?> list, String title, String sheetName, Class<?> pojoClass){
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(title, sheetName),pojoClass,list);
        byte[] bytes = null; 
        if (workbook != null){
        	try {
                //获取文件输出流
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                try {
                    workbook.write(bos);
                } finally {
                    bos.close();
                }
                bytes = bos.toByteArray();
            } catch (IOException e) {
            	logger.error(e.getMessage(),e);
            }
        }
        return bytes;
    }

    
}
