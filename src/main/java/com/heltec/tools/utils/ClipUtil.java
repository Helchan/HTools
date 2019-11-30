package com.heltec.tools.utils;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

/**
 * 剪切板读写处理
 * @author EX-TANGHAIQIANG001
 * @date 2019-09-27
 */
public class ClipUtil {
	//剪切板
	private static Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
	
	/**
	 * 获取剪切板内容
	 * @return
	 */
	public static String readText4Clipboard() {
		Transferable contents = clip.getContents(null);
		if (contents == null) {
			return "";
		}
		if (!contents.isDataFlavorSupported(DataFlavor.stringFlavor)){
			return "";
		}
		try {
			return (String)contents.getTransferData(DataFlavor.stringFlavor);
		} catch (Exception e) {
			System.out.println("读取剪切板异常：" + e);
			return "";
		}
	}

	/**
	 * 写入文本内容到剪切板
	 * @param text
	 */
	public static void writeText2Clipboard(String text) {
		Transferable contents = new StringSelection(text);
		clip.setContents(contents, null);
	}
	
	
	
	/**
	 * 测试代码
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("剪切板内容：" + readText4Clipboard());
		writeText2Clipboard("Hello!");
		System.out.println("剪切板内容：" + readText4Clipboard());
	}
}
