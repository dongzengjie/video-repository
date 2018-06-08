package com.dzj.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.dzj.config.ConfigClass;

public class MergeVideoMp3 {

	private String ffmpegEXE;

	public MergeVideoMp3(String ffmpegEXE) {
		super();
		this.ffmpegEXE = ffmpegEXE;
	}

	public void convertor(String videoInputPath, String mp3InputPath, double seconds, String videoOutputPath) throws IOException {
		//ffmpeg.exe -i bgm.mp3 -i 苏州大裤衩.mp4 -t 7 新的视21频.mp4
		List<String> command = new ArrayList<>();
		command.add(ffmpegEXE);
		command.add("-i");
		command.add(mp3InputPath);
		command.add("-i");
		command.add(videoInputPath);
		command.add("-t");
		command.add(String.valueOf(seconds));
		//command.add("-y");
		command.add(videoOutputPath);
		ProcessBuilder processBuilder =new ProcessBuilder(command);
		Process process =processBuilder.start();
		
		
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
		String finalPath =PathUtil.getBasePath() + PathUtil.getLocalPath("12", "videoMp3")+UUID.randomUUID().toString()+".mp4";
		String videoMp4Path= PathUtil.getLocalPath("12", "videoMp3")+UUID.randomUUID().toString()+".mp4";
		MergeVideoMp3 ffmpeg = new MergeVideoMp3(ConfigClass.FFMPEG_EXE);
		try {
			ffmpeg.convertor("C:/MyVedio/upload/18053088Y40XB7R4/video/20180606143721348310000d83d2ae3-9adb-4606-9ef3-a15f6bbbc447.mp4", "C:/MyVedio/upload/bgm/Despacito 林俊杰 + LuisFonsi.mp3", 7, PathUtil.getBasePath() + PathUtil.getLocalPath("18053088Y40XB7R4", "video")+UUID.randomUUID().toString()+".mp4");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
