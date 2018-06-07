package com.dzj.utils;

public class PathUtil {
	private static String seperator = System.getProperty("file.separator");
	
	public static String getBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "C:/MyVedio";
		} else {
			basePath = "/usr/local/MyVedio";
		}
		//basePath = basePath.replace("/", seperator);
		return basePath;
	}
	
	public static String getLocalPath(String userId,String faceOrVideo) {
		
		String path = "/upload/"+userId+"/"+faceOrVideo+"/";
		
		//return path.replace("/", seperator);
		return path;
	}

}
