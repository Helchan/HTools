package com.heltec.tools.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.heltec.tools.utils.ideal.ITextDeal;

/**
 * 文本文件读写处理
 * 
 * @author EX-TANGHAIQIANG001
 * @date 2019-09-27
 */
public class TextUtil {
	// 文件编码
	public static String encoding = "UTF-8";

	/**
	 * 处理文本文件
	 * 
	 * @param file
	 *            文件
	 * @param pattern
	 *            正则对象，若为null则逐行读取
	 * @param needReturnFlag
	 *            是否返回值
	 * @return
	 */
	public static <T> List<T> processFile(File file, Pattern pattern,
			ITextDeal<T> deal) {

		// 返回读到的内容项
		ArrayList<T> backContent = new ArrayList<T>();

		System.out.println("开始处理文件：" + file.getAbsolutePath());

		// 一、逐行读取文本文件
		if (pattern == null) {
			// 1、获取文件输入流
			FileInputStream fileInputStream = null;
			try {
				fileInputStream = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				System.out
						.println("文件不存在：" + file.getAbsolutePath() + ", " + e);
				return null;
			}

			// 2、循环读取文件内容
			String line = null;
			BufferedReader br = null;
			int index = 1;
			try {
				// 获取缓冲流
				br = new BufferedReader(new InputStreamReader(fileInputStream,
						"UTF-8"));

				// 逐行读取文件内容
				while ((line = br.readLine()) != null) {
					deal.execute(line, index, backContent);
					index++;
				}
			} catch (UnsupportedEncodingException e) {
				System.out.println("文件：" + file.getName() + "不支持编码：" + encoding
						+ ", " + e);
				return null;
			} catch (IOException e) {
				System.out.println("文件：" + file.getName() + "读取第" + index
						+ "失败, " + e);
				return null;
			} finally {
				// 4、关闭流
				try {
					if (br != null) {
						br.close();
					}
					if (fileInputStream != null) {
						fileInputStream.close();
					}
				} catch (IOException e) {
					System.out.println("文件：" + file.getName() + "关闭失败, " + e);
				}
			}
			// 二、正则读取文件
		} else {
			try {
				String fileContent = FileUtils.readFileToString(file, encoding);
				Matcher matcher = pattern.matcher(fileContent);
				// 开始匹配
				int index = 1;
				while (matcher.find()) {
					String matchItem = matcher.group();
					deal.execute(matchItem, index, backContent);
					index++;
				}
			} catch (IOException e) {
				System.out.println("读取文件失败：" + file.getName() + ", " + e);
			}
		}
		return backContent;
	}

	/**
	 * 将内容写入text文件
	 * 
	 * @param textFile
	 *            文本文件
	 * @param data
	 *            数据
	 * @param append
	 *            是否追加模式
	 * @return 文件全路径
	 */
	public static String writeContent2textFile(File textFile,
			List<String> data, boolean append) {
		System.out.println("开始写入文件：" + textFile.getAbsolutePath());
		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			fos = new FileOutputStream(textFile, append);
			pw = new PrintWriter(fos, true);
			for (String str : data) {
				pw.println(str);
			}
		} catch (FileNotFoundException e) {
			System.out.println("文件处理失败：" + textFile.getAbsolutePath() + ", "
					+ e);
		} finally {
			if (pw != null) {
				pw.close();
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					System.out.println("文件关闭失败：" + textFile.getAbsolutePath()
							+ ", " + e);
				}
			}
		}
		System.out.println("内容写入完毕：" + textFile.getAbsolutePath());
		return textFile.getAbsolutePath();
	}
	
	/**
	 * 将一行文本写入文件中
	 * @param textFile
	 * @param line
	 * @param append
	 * @return
	 */
	public static String writeContent2textFile(File textFile,
			String line, boolean append) {
		System.out.println("开始写入文件：" + textFile.getAbsolutePath());
		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			fos = new FileOutputStream(textFile, append);
			pw = new PrintWriter(fos, true);
			pw.println(line);
		} catch (FileNotFoundException e) {
			System.out.println("文件处理失败：" + textFile.getAbsolutePath() + ", "
					+ e);
		} finally {
			if (pw != null) {
				pw.close();
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					System.out.println("文件关闭失败：" + textFile.getAbsolutePath()
							+ ", " + e);
				}
			}
		}
		System.out.println("内容写入完毕：" + textFile.getAbsolutePath());
		return textFile.getAbsolutePath();
	}

	/**
	 * 测试代码
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// processFile 读文件
		Pattern pattern = Pattern.compile("biz\\S+xml");
		List<String> content = processFile(new File(
				"d:/TANGHAIQIANG/zTestFiles/fileList.txt"), pattern,
				new ITextDeal<String>() {
					@Override
					public void execute(String text, int index,
							List<String> backList) {
						backList.add(text);
						System.out.println(text);
					}
				});

		// writeContent2textFile 写文件
		writeContent2textFile(new File("d:/TANGHAIQIANG/zTestFiles/test.txt"),
				content, false);
	}
}