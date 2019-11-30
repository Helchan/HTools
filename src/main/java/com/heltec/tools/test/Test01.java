package com.heltec.tools.test;

import java.io.File;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

import com.heltec.tools.ideal.IExcDeal;
import com.heltec.tools.utils.ExcelUtil;

public class Test01 {
	@Test
	public void func01(){
		ExcelUtil.processFile(new File("D:\\Others\\CodeTest\\test01.xlsx"), null, new IExcDeal<String>() {

			@Override
			public void execute(Row row, List<String> back) {
				System.out.println(row.getCell(0).toString());
			}
		});
		
		System.out.println(ExcelUtil.getCellsContent(new File("D:\\Others\\CodeTest\\test01.xlsx"), null));
	}
}
