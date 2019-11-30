package com.heltec.tools.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.heltec.tools.ideal.IFileDeal;
import com.heltec.tools.ideal.ITextDeal;

/**
 * 文件处理工具
 * 
 * @author EX-TANGHAIQIANG001
 */
public class FileUtil {
	/**
	 * 处理目录下的文件
	 * 
	 * @param file
	 *            目录或存储文件列表的文件
	 * @param filterOrParentPath
	 *            过滤器或文件列表的父目录
	 * @param deal
	 *            处理单个文件
	 * @return deal最后一个参数
	 */
	public static <T, K> List<T> processFiles(File file, K filterOrParentPath,
			IFileDeal<T> deal) {
		// 返回读到的内容项
		List<T> backContent = new ArrayList<T>();
		List<File> files;
		System.out.println("开始处理目录：" + file.getAbsolutePath());
		if (filterOrParentPath instanceof FileFilter) {
			files = getFilesInFolder(file, (FileFilter) filterOrParentPath);
		} else {
			files = getFilesInFileList(file, (String) filterOrParentPath);
		}
		for (File tempFile : files) {
			deal.execute(tempFile, backContent);
		}
		return backContent;
	}

	/**
	 * 获取目录下符合过滤要求的文件
	 * 
	 * @param file
	 *            文件或目录
	 * @param filter
	 *            过滤器
	 * @return 文件集合
	 */
	private static List<File> getFilesInFolder(File file, FileFilter filter) {
		List<File> fileList = new ArrayList<File>();
		if (!file.exists()) {
			return fileList;
		}
		if (file.isFile() && filter.accept(file)) {
			fileList.add(file);
		}
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File tempFile : files) {
				if (tempFile.isFile() && filter.accept(file)) {
					fileList.add(tempFile);
				} else {
					fileList.addAll(getFilesInFolder(tempFile, filter));
				}
			}
		}
		return fileList;
	}

	/**
	 *
	 * @param listFile
	 *            存储文件列表的文件
	 * @param parentPath
	 *            文件父目录（如果列表中是全路径则可为null）
	 * @return 文件集合
	 */
	private static List<File> getFilesInFileList(File listFile,
			String parentPath) {
		List<File> fileList = new ArrayList<File>();
		List<String> content = TextUtil.processFile(listFile, null,
				new ITextDeal<String>() {
					@Override
					public void execute(String text, int index,
							List<String> backList) {
						backList.add(text);
					}
				});
		for (String filePath : content) {
			String fileFullPath = StringUtils.trimToEmpty(parentPath)
					+ filePath;
			File file = new File(fileFullPath);
			if (!file.exists()) {
				System.out.println("文件不存在:" + fileFullPath);
				continue;
			}
			fileList.add(file);
		}
		return fileList;
	}

	public static void main(String[] args) {
		// 1、获取或操作目录下符合过滤条件的文件
		// 目录
		File file = new File(
				"D:/TANGHAIQIANG/workspace/auto_j2ee-common_qh/src/config/biz/");
		// 过滤器
		FileFilter filter = new FileFilter() {
			@Override
			public boolean accept(File file) {
				if (file.getName().endsWith(".xml")) {
					return true;
				}
				return false;
			}
		};
		// 执行方法
		List<File> p1Files = processFiles(file, filter, new IFileDeal<File>() {

			@Override
			public void execute(File file, List<File> backList) {
				// 操作文件，并指定返回值
				backList.add(file);
			}
		});

		System.out.println(p1Files.size());
		System.out.println(p1Files);

		// 获取或操作文件列表中的文件
		File listFile = new File("d:/TANGHAIQIANG/zTestFiles/fileList.txt");
		String parentPath = "D:/TANGHAIQIANG/workspace/auto_j2ee-common_qh/src/config/biz/";
		List<File> p2Files = processFiles(listFile, parentPath,
				new IFileDeal<File>() {

					@Override
					public void execute(File file, List<File> backList) {
						// 操作文件，并指定返回值
						backList.add(file);
					}
				});

		System.out.println(p2Files.size());
		System.out.println(p2Files);

	}
}