package com.heltec.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.heltec.tools.utils.ExcelUtil;
import com.heltec.tools.utils.TextUtil;
import com.heltec.tools.utils.ideal.IExcDeal;

public class TestJsoup {
	@Test
	public void func02() throws Exception{
		File textFile = new File("d:/星辰变后传2.txt");
		System.out.println(textFile.getAbsolutePath());
		String host = "https://m.duquanben.com";
		String url = "/xiaoshuo/11/11674/14161211.html";
		Document document = Jsoup.connect(host + url).get();
		FileOutputStream fos = null;
		PrintWriter pw = null;
		fos = new FileOutputStream(textFile, true);
		pw = new PrintWriter(fos, true);
		while(document.getElementById("pt_next")!=null){
			Elements txtElements = document.getElementsByClass("txt");
			if(txtElements.size()>1){
				throw new Exception("页面含多个txt");
			}
			//只有一个txt
			Element txtElement = txtElements.get(0);
			//标题
			Elements titleEle = txtElement.getElementsByIndexEquals(0);
			System.out.println("正在处理：" + titleEle.text());
			//循环txt标签中的段落文本写入文件
			for (Element pEle : txtElement.getElementsByTag("p")) {
				String pText = pEle.text();
				pw.println(pText);
			}
			//下一页信息
			Element ptNextEle = document.getElementById("pt_next");
			String nextPageUrl = ptNextEle.attr("href");
			String ptNextText = ptNextEle.text();
			if("无下章".equals(ptNextText)){
				break;
			}
			document = Jsoup.connect(host + nextPageUrl).get();
		}
		pw.close();
		fos.close();
		System.out.println("关闭文件成功！");
		//TextUtil.writeContent2textFile(file, "test", false);
	}
}
