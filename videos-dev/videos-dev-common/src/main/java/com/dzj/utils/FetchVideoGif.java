package com.dzj.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.dzj.config.ConfigClass;
import com.dzj.enums.UpLoadEnum;

public class FetchVideoGif {
	private String ffmpeg;


	
	public FetchVideoGif(String ffmpeg) {
		super();
		this.ffmpeg = ffmpeg;
	}
	
	public void getGIF(String videoInputPath, double seconds, String coverOutputPath) throws IOException {
		List<String> command = new ArrayList<>();
		command.add(ffmpeg);
		command.add("-ss");
		command.add(seconds-3+"");
		command.add("-t");
		command.add("3");
		command.add("-i");
		command.add(videoInputPath);
		command.add("-f");
		command.add("gif");
		command.add(coverOutputPath);
		
		ProcessBuilder builder = new ProcessBuilder(command);
		Process process = builder.start();
		
		InputStream errorStream = process.getErrorStream();
		InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
		BufferedReader br = new BufferedReader(inputStreamReader);
		
		String line = "";
		while ( (line = br.readLine()) != null ) {
		}
		
		if (br != null) {
			br.close();
		}
		if (inputStreamReader != null) {
			inputStreamReader.close();
		}
		if (errorStream != null) {
			errorStream.close();
		}
		
	}
	public static void main(String[] args) {
		String coverPath =  PathUtil.getLocalPath("18053088Y40XB7R4", UpLoadEnum.VIDEO.getMsg())+UUID.randomUUID().toString()+".jpg";
		
		FetchVideoCover fetchVideoCover =new FetchVideoCover(ConfigClass.FFMPEG_EXE);
		try {
			fetchVideoCover.getCover("C:/MyVedio/upload/18053088Y40XB7R4/video/201806061450383515100005c66dc5f-d784-4450-ab0b-ec6cac1600c9.mp4", PathUtil.getBasePath()+coverPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
