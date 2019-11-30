package com.heltec.tools.ideal;

import java.io.File;
import java.util.List;

public interface IFileDeal<T> {
	void execute(File file, List<T> backList);  
}
