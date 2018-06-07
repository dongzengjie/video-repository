package com.dzj.server;



import org.springframework.web.multipart.MultipartFile;

import com.dzj.exception.UserException;
import com.dzj.pojo.Videos;

public interface VideoService {

	/**
	 * 视频处理接口
	 * @param file
	 * @param userId
	 * @throws UserException
	 */
	public boolean userVideoHandle(MultipartFile file,String userId,Videos videos,String bgmId)throws UserException;

}
