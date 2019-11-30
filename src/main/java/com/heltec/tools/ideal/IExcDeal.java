package com.heltec.tools.ideal;

import java.util.List;
import org.apache.poi.ss.usermodel.Row;

public interface IExcDeal<T> {
	/**
	 * 处理Excel行
	 * @param row 行
	 * @param back 返回值（若为null，需先初始化）
	 */
	 void execute(Row row, List<T> back);
}
