package com.heltec.tools.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

import com.google.common.collect.Lists;
import com.heltec.tools.utils.ideal.IXmlDeal;

/**
 * xml文件读写处理
 * 
 * @author EX-TANGHAIQIANG001
 * @date 2019-09-27
 */
public class XmlUtil {
	// 解析器
	private static final SAXReader reader = new SAXReader();
	// 设置不从网络获取dtd验证
	static {
		reader.setValidation(false);
		try {
			reader.setFeature(
					"http://apache.org/xml/features/nonvalidating/load-external-dtd",
					false);
		} catch (SAXException e) {
			System.out.println("解析器配置异常：" + e);
		}
	}

	/**
	 * 解析处理xml文件
	 * 
	 * @param xmlFile
	 * @param elementNames
	 *            元素名集合（区分大小写）
	 * @param deal
	 *            处理对象
	 * @return deal对象execute()方法最后一个参数
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> processXmlFile(File xmlFile,
			List<String> elementNames, IXmlDeal<T> deal) {
		System.out.println("开始处理文件：" + xmlFile.getAbsolutePath());
		// 返回读到的内容项，可在deal对象中决定是否写入返回
		ArrayList<T> backContent = new ArrayList<T>();
		Document document;
		List<Element> elements = new ArrayList<Element>();
		try {
			document = reader.read(xmlFile);
			Element root = document.getRootElement();
			for (String elementName : elementNames) {
				elements.addAll(root.elements(elementName));
			}
			deal.execute(elements, backContent);
		} catch (DocumentException e) {
			System.out.println("解析文件发生异常：" + xmlFile.getName() + ", " + e);
		}
		System.out.println("文件处理完毕：" + xmlFile.getAbsolutePath());
		return backContent;
	}

	/**
	 * 测试代码
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		File xmlFile = new File(
				"D:\\TANGHAIQIANG\\zTestFiles\\sqlmap-mapping-estimate.xml");
		List<String> elementNames = new ArrayList<String>();
		elementNames.add("update");
		processXmlFile(xmlFile, elementNames, new IXmlDeal<String>() {

			@SuppressWarnings("unchecked")
			@Override
			public void execute(List<Element> elements, List<String> back) {
				List<Element> toRemoveElements = new ArrayList<Element>();
				for (Element element : elements) {
					toRemoveElements.clear();
					Attribute attribute = element.attribute("id");
					String sql_id = attribute.getValue();
					if (sql_id.equals("estimate.updateDutyEstimateHistory")) {
						System.out.println();
					}
					System.out.println("SQL_ID=" + sql_id);
					toRemoveElements.addAll(element.elements("isNotEmpty"));
					toRemoveElements.addAll(element.elements("isEmpty"));
					for (Element tempEle : toRemoveElements) {
						element.remove(tempEle);
					}
					String sql = element.getStringValue();
					System.out.println(sql);
				}

			}
		});

	}
}