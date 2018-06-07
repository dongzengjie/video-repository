package com.dzj.server;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.dzj.exception.UserException;

public interface UserService {

	/**
	 * 用户头像处理
	 * @param file
	 * @param userId
	 * @return 头像相对路径
	 * @throws UserException
	 */
	public String userFaceImageHandle(MultipartFile file,String userId) throws UserException;
	
	

}
