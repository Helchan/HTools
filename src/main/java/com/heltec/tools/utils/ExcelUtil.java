package com.heltec.tools.utils;

import com.heltec.tools.ideal.IExcDeal;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel文件读写处理
 * 
 * @author EX-TANGHAIQIANG001
 * @date 2019-09-27
 */
public class ExcelUtil {
	/**
	 * 处理工作簿
	 * 
	 * @param excFile Excel文件
	 * @param sheetIndexs 索引 从0开始的集合，如果只读第一个表单给null即可
	 * @param deal 处理操作对象
	 * @return 处理对象execute方法的最后一个参数
	 */
	public static <T> List<T> processFile(File excFile, List<Integer> sheetIndexs, IExcDeal<T> deal) {
		System.out.println("开始处理文件：" + excFile.getAbsolutePath());
		if(sheetIndexs == null || sheetIndexs.size() == 0){
			sheetIndexs = new ArrayList<Integer>();
			sheetIndexs.add(0);
		}
		FileInputStream inputStream = null;
		List<T> backContent = new ArrayList<T>();
		try {
			inputStream = new FileInputStream(excFile);
			Workbook workbook = WorkbookFactory.create(inputStream);
			// 遍历处理每个sheet
			for (int sheetIndex : sheetIndexs) {
				Sheet sheet = workbook.getSheetAt(sheetIndex);
				// 遍历处理每行
				for (Row row : sheet) {
					// 行由处理对象处理
					deal.execute(row, backContent);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("文件不存在：" + excFile.getName() + ", " + e);
		} catch (Exception e) {
			System.out.println("操作文件：" + excFile.getName() + " 发生异常：" + e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					System.out
							.println("关闭文件：" + excFile.getName() + " 失败：" + e);
				}
			}
		}
		return backContent;
	}

	/**
	 * 读取Excel单元格中的内容存入List中
	 * 
	 * @param excFile
	 *            Excel文件
	 * @param sheetIndexs
	 *            sheet索引集合
	 * @param deal
	 *            处理对象
	 * @return cell内容组成的List组成的List
	 */
	public static List<List<String>> getCellsContent(File excFile, List<Integer> sheetIndexs) {
		if(sheetIndexs == null || sheetIndexs.size() == 0){
			sheetIndexs = new ArrayList<Integer>();
			sheetIndexs.add(0);
		}
		return processFile(excFile, sheetIndexs, new IExcDeal<List<String>>() {
			@Override
			public void execute(Row row, List<List<String>> back) {
				DataFormatter formatter = new DataFormatter();
				List<String> rowData = new ArrayList<String>();
				for (int i = 0; i < row.getLastCellNum(); i++) {
					String text = formatter.formatCellValue(row.getCell(i));
					rowData.add(text);
				}
				back.add(rowData);
			}
		});
	}

	/**
	 * 创建Excel文件，写入数据
	 * 
	 * @param outputexcFile
	 *            输出文件（.xls）
	 * @param headers
	 *            表头
	 * @param data
	 *            表数据
	 * @return 文件全路径
	 */
	public static String writeContent2Excel(File outputexcFile,
			List<String> headers, List<List<String>> data) {

		// 第一步创建workbook
		@SuppressWarnings("resource")
		HSSFWorkbook wb = new HSSFWorkbook();

		// 第二步创建sheet
		String outputexcFileName = outputexcFile.getName();
		String sheetName = outputexcFileName
				.replaceAll(outputexcFileName.substring(outputexcFileName
						.lastIndexOf(".")), "");
		HSSFSheet sheet = wb.createSheet(sheetName);

		// 第三步创建行row:添加表头0行
		HSSFRow row = sheet.createRow(0);
		HSSFCellStyle style = wb.createCellStyle();

		// 第四步插入表头
		if (headers != null) {
			int headIndex = 0;
			for (String head : headers) {
				HSSFCell cell = row.createCell(headIndex);
				cell.setCellValue(head);
				cell.setCellStyle(style);
				headIndex++;
			}
		}

		// 第五步插入数据
		int rowIndex = 1;
		for (List<String> rowData : data) {
			row = sheet.createRow(rowIndex);
			int cellIndex = 0;
			for (String cellData : rowData) {
				row.createCell(cellIndex).setCellValue(cellData);
				cellIndex++;
			}
			rowIndex++;
		}

		// 第六步将生成excel文件保存到指定路径下
		try {
			FileOutputStream fout = new FileOutputStream(outputexcFile);
			wb.write(fout);
			fout.close();
		} catch (IOException e) {
			System.out.println("Excel文件生成失败：" + e);
			return null;
		}

		System.out.println("Excel文件生成成功..." + outputexcFile.getAbsolutePath());
		return outputexcFile.getAbsolutePath();
	}

	/**
	 * 测试代码
	 * 
	 * @param args
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static void main(String[] args) throws EncryptedDocumentException,
			InvalidFormatException, IOException {

		String filePath = "D:\\TANGHAIQIANG\\zTestFiles\\test.xlsx";
		File file = new File(filePath);
		ArrayList<Integer> sheetIndexs = new ArrayList<Integer>();
		sheetIndexs.add(0);
		List<List<String>> data = getCellsContent(file, sheetIndexs);
		System.out.println("data:" + data);
		ExcelUtil.writeContent2Excel(new File(
				"D:\\TANGHAIQIANG\\zTestFiles\\testCreate2.xls"), null, data);
	}
}