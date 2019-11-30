package com.heltec.tools.ideal;

import java.util.List;

public interface ITextDeal<T> {
	 void execute(String textPart, int index, List<T> backList);
}
