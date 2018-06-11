package com.dzj.server;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.dzj.exception.UserException;
import com.dzj.pojo.Users;

public interface UserService {

	/**
	 * 用户头像处理
	 * @param file
	 * @param userId
	 * @return 头像相对路径
	 * @throws UserException
	 */
	public String userFaceImageHandle(MultipartFile file,String userId) throws UserException;
	
	/**
	 * 根据用户Id 查询用户信息
	 * @param userId
	 * @return
	 */
	public Users queryUserById(String userId);
	
	/**
	 * 添加用户和粉丝的关系
	 * @param userId
	 * @param fansId
	 */
	public void saveUserFanRelation(String userId ,String fansId);
	
	/**
	 * 减少用户和粉丝的关系
	 * @param userId
	 * @param fansId
	 */
	public void deleteUserFanRelation(String userId ,String fansId);
	/**
	 * 判断视频用户和粉丝之间的关系
	 * @param userId
	 * @param fansId
	 * @return
	 */
	public boolean isFollow(String userId ,String fansId);
}
