package com.dzj.utils;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

public class FileDel {

	
	public  static void delFile(String path) {
		if(path !=null && !StringUtils.isEmpty(path)) {
			File file =new File(path);
			file.delete();
		}
		
	}
}
