package com.heltec.tools.utils.ideal;

import java.util.List;

public interface ITextDeal<T> {
	 void execute(String textPart, int index, List<T> backList);
}
