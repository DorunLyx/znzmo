package com.pactera.znzmo.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * POI 读取Excel类
 * 
 * 功能说明：可支持 2003 - 2013
 * 
 * @author  lijing　2015年4月2日
 */
public class ExcelImport {
	
	public static final Logger logger = LoggerFactory.getLogger(ExcelImport.class);

	protected  Workbook wb = null;
	
	protected  String dateFormat = "yyyy-MM-dd hh:mm:ss";

	/**
	 * excel数据集
	 */
	protected  List<String[]> dataList = new ArrayList<String[]>(100);

	/**
	 * 初始化reader
	 * 
	 * @param path excel 文件物理路径
	 */
	public  ExcelImport(String path){
		try {
			InputStream input = new FileInputStream(path);
			wb = WorkbookFactory.create(input);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	/**
	 * 取Excel所有数据，包含header
	 * 
	 * @return List<行单元格数组>
	 */
	public List<String[]> getAllData(int sheetIndex) {
		int columnNum = 0;
		Sheet sheet = wb.getSheetAt(sheetIndex);
		if (sheet.getRow(0) != null) {
			columnNum = sheet.getRow(0).getLastCellNum()- sheet.getRow(0).getFirstCellNum();
		}
		if (columnNum > 0) {
			for (Row row : sheet) {
				String[] singleRow = new String[columnNum];
				int n = 0;
				for (int i = 0; i < columnNum; i++) {
					Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BLANK:
						singleRow[n] = "";
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						singleRow[n] = Boolean.toString(cell.getBooleanCellValue());
						break;
					// 数值
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							singleRow[n] = DateUtils.DateToString(cell.getDateCellValue(),dateFormat);
						} else {
							double temp = cell.getNumericCellValue();
							singleRow[n] = String.valueOf(new Double(temp)).trim();
						}
						break;
					case Cell.CELL_TYPE_STRING:
						singleRow[n] = cell.getStringCellValue().trim();
						break;
					case Cell.CELL_TYPE_ERROR:
						singleRow[n] = "";
						break;
					case Cell.CELL_TYPE_FORMULA:
						cell.setCellType(Cell.CELL_TYPE_STRING);
						singleRow[n] = cell.getStringCellValue();
						if (singleRow[n] != null) {
							singleRow[n] = singleRow[n].replaceAll("#N/A", "")
									.trim();
						}
						break;
					default:
						singleRow[n] = "";
						break;
					}
					n++;
				}
				//过滤空行
				if (isNotNullRow(singleRow)) {
					dataList.add(singleRow);
				}
			}
		}
		return dataList;
	}

	/**
	 * 返回Excel最大行index值，实际行数要加1
	 * 
	 * @return
	 */
	public int getRowNum(int sheetIndex) {
		Sheet sheet = wb.getSheetAt(sheetIndex);
		return sheet.getLastRowNum();
	}

	/**
	 * @Title: getColumnNum 
	 * @Description: 获取列数量
	 * @param sheetIndex 工作表索引
	 * @author LiuGuiChao
	 * @date 2019年3月8日 下午10:27:34 
	*/
	public int getColumnNum(int sheetIndex) {
		Sheet sheet = wb.getSheetAt(sheetIndex);
		Row row = sheet.getRow(0);
		if (row != null && row.getLastCellNum() > 0) {
			return row.getLastCellNum();
		}
		return 0;
	}

	/**
	 * 获取某一行数据
	 * 
	 * @param rowIndex
	 *            计数从0开始，rowIndex为0代表header行
	 * @return
	 */
	public String[] getRowData(int sheetIndex, int rowIndex) {
		String[] dataArray = null;
		if (rowIndex > this.getColumnNum(sheetIndex)) {
			return dataArray;
		} else {
			dataArray = new String[this.getColumnNum(sheetIndex)];
			if(dataList.size() <= rowIndex){
			    return dataArray;
			}
			return this.dataList.get(rowIndex);
		}

	}

	/**
	 * 获取某一列数据
	 * 
	 * @param colIndex
	 * @return
	 */
	public String[] getColumnData(int sheetIndex, int colIndex) {
		String[] dataArray = null;
		if (colIndex > this.getColumnNum(sheetIndex)) {
			return dataArray;
		} else {
			if (this.dataList != null && this.dataList.size() > 0) {
				dataArray = new String[this.getRowNum(sheetIndex) + 1];
				int index = 0;
				for (String[] rowData : dataList) {
					if (rowData != null) {
						dataArray[index] = rowData[colIndex];
						index++;
					}
				}
			}
		}
		return dataArray;
	}
	
	/**
	  * 校验是否整行数据都为空
	  *  
	  * @param cells 整行数据
	  * @return
	 */
	public  boolean isNotNullRow(String[]cells){
		boolean isNotNull = true;
		
		int count = 0;
		for(String cell : cells){
			if(cell.trim().length()<=0){
				count++;
			}
		}
		
		if (count >= cells.length) {
			 isNotNull = false;
		}
		return isNotNull;
	}
}
