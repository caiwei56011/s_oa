package com.jsoft.oa.core.common.excel;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/***
 * 导入导出Excel的工具类
 * @author jack
 * @version V1.0
 */
public class ExcelUtils {
	/**
	 * 导出Excel方法(导出为03版)
	 * @param sheetName 工作单的名称
	 * @param excelFileName Excel的文件名
	 * @param titles 标题行
	 * @param data 中行的数据
	 * @param response 响应对象
	 * @param request 请求对象
	 * @throws Exception 异常
	 */
	public static void exportExcel(String sheetName, String excelFileName,
			String[] titles, List<?> data, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		/** 创建工作簿 */
		HSSFWorkbook workbook = new HSSFWorkbook();
		/** 通过工作簿创建工作单 */
		HSSFSheet sheet = workbook.createSheet(sheetName);
		/** 创建第一行作为标题行 */
		HSSFRow row = sheet.createRow(0);
		/** 循环创建第一行中列 */
		for (int i = 0; i < titles.length; i++){
			/** 创建列 */
			HSSFCell cell = row.createCell(i);
			/** 设置列中内容 */
			cell.setCellValue(titles[i]);
		}
		
		/** ##############中间行的数据(data集合)################## */
		/** 迭代data集合 */
		for (int i = 0; i < data.size(); i++){
			/** 循环创建行 */
			row = sheet.createRow(i + 1);
			/** 获取集合中的元素 ContactBean对象 */
			Object obj = data.get(i);
			/** 获取obj对象中的Field */
			Field[] fields = obj.getClass().getDeclaredFields();
			/** 循环fields创建列 */
			for (int j = 0; j < fields.length; j++){
				/** 循环创建列 */
				HSSFCell cell = row.createCell(j);
				/** 获取Field */
				Field field = fields[j];
				/** 设置Field可以访问 */
				field.setAccessible(true);
				/** 获取Field的值 */
				Object res = field.get(obj);
				/** 设置Cell中值 */
				cell.setCellValue(res == null ? "" : res.toString());
			}
		}
		
		/** ##############中间行的数据(data集合)################## */
		
		/** Excel导出时指定的文件名为中文，处理中文乱码 */
		// 响应数据的过程：
		// 第一种情况：服务器： utf-8  --> iso8859-1       浏览器：iso8859-1 --> utf-8 (除了msie以外的浏览器)
		//excelFileName = new String(excelFileName.getBytes("utf-8"), "iso8859-1");
		// 第二种情况：服务器： utf-8 --> gbk --> iso8859-1       浏览器：iso8859-1 --> gbk (msie浏览器)
		//excelFileName = new String(excelFileName.getBytes("gbk"), "iso8859-1");
		
		/** 定义默认的编码 */
		String encoding = "utf-8";
		/** 获取浏览器的类型，作判断 */
		String userAgent = request.getHeader("user-agent");
		if (userAgent.toLowerCase().indexOf("msie") != -1){
			encoding = "gbk";
		}
		excelFileName = new String(excelFileName.getBytes(encoding), "iso8859-1");
		/** 设置响应头，指定下载时的文件名 */
		response.setHeader("Content-Disposition", "attachment;filename=" + excelFileName + ".xls");
		workbook.write(response.getOutputStream());
		workbook.close();
	}
	
	/**
	 * 读取上传的Excel中的数据方法(兼容03和07)
	 * @param excel Excel文件
	 * @return List集合
	 */
	public static List<List<Object>> readExcel(File excel) throws Exception{
		/** 根据指定的Excel文件创建工作簿(只兼容03) */
		//HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(excel));
		/** 根据指定的Excel文件创建工作簿(poi-3.15中方法,此种方式兼容03和07) */
		Workbook workbook = WorkbookFactory.create(excel);
		/** 获取第一个工作单 */
		Sheet sheet = workbook.getSheetAt(0);
		// 获取工作单的总数量：workbook.getNumberOfSheets();
		/** 获取最后一行的索引号 */
		int lastRowNum = sheet.getLastRowNum();
		/** 定义最后返回的List集合 */
		List<List<Object>> excelData = new ArrayList<List<Object>>();
		/** 循环所有的行 (第一行不要) */
		for (int i = 1; i <= lastRowNum; i++){
			/** 获取行 */
			Row row = sheet.getRow(i);
			/** 获取这一行最后一列的索引号  (多加了1)*/
			int lastCellNum = row.getLastCellNum();
			/** 定义一行数据 */
			List<Object> rowData = new ArrayList<Object>();
			/** 循环这一行中所有的列 */
			for (int j = 0; j < lastCellNum; j++){
				/** 获取一列 */
				Cell cell = row.getCell(j);
				/** 判断列中的内容的数据类型 */
				if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){ // boolean
					rowData.add(cell.getBooleanCellValue());
				}else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC){ // number
					/** 判断是不是Date类型 */
					if (DateUtil.isCellDateFormatted(cell)){ // date
						rowData.add(cell.getDateCellValue()); // java.util.Date
					}else{
						rowData.add(cell.getNumericCellValue());
					}
				}else{ // string
					rowData.add(cell.getStringCellValue());
				}
			}
			excelData.add(rowData);
		}
		return excelData;
	}
}
