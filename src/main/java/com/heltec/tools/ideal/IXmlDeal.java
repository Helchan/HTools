package com.heltec.tools.ideal;

import java.util.List;
import org.dom4j.Element;

public interface IXmlDeal<T> {
	void execute(List<Element> elements, List<T> back);
}
