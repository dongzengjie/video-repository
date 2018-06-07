package com.dzj.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dzj.enums.UpLoadEnum;

public class UploadUtil {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static Random random = new Random();

	private static String getRandomName() {
		return dateFormat.format(new Date()) + "" + random.nextInt(8999) + 10000+UUID.randomUUID().toString();
	}

	/**
	 * 文件上传处理
	 * 
	 * @return 返回文件的相对路径
	 */
	public static String handle(MultipartFile file, String userId, UpLoadEnum loadEnum) {
		
		
		InputStream inputStream=null;
		try {
			inputStream = file.getInputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String fileName = file.getOriginalFilename();
				
		OutputStream outputStream = null;
		String face_or_video = loadEnum.getMsg();
		String basePath = PathUtil.getBasePath();

		String localPath = PathUtil.getLocalPath(userId, face_or_video);
		String path = basePath + localPath;
		makedirPath(path);
		String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		String newFileName = getRandomName() + "." + suffixName;
		String newFilepath = path + newFileName;
		String dbPath = localPath +newFileName;
		try {
			File files =new File(newFilepath);
			outputStream = new FileOutputStream(files);
			IOUtils.copy(inputStream, outputStream);
		} catch (Exception e) {
			return null;
		}finally {
			try {
				if(outputStream !=null) {
					outputStream.close();
				}
				if(inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dbPath;
		
		
	}
	
	private static void makedirPath(String path) {
		File file =new File(path);
		if(!file.exists()) {
			file.mkdirs();
		}
	}
}
