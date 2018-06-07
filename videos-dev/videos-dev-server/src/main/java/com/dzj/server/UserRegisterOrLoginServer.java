package com.dzj.server;

import com.dzj.pojo.Users;
import com.dzj.pojo.vo.UsersVo;

public interface UserRegisterOrLoginServer {
	
	/**
	 * 判断用户是否存在
	 * @param username
	 * @return
	 */
	public boolean queryUserIsExist(String username);
	
	/**
	 * 保存用户
	 * @param user
	 */
	public UsersVo saveUser(Users user);
	
	/**
	 * 用户登陆
	 * @param username
	 * @param password
	 * @return
	 */
	public UsersVo userLogin(String username,String password);
	
	/**
	 * 用户注销
	 * @param userId
	 * @return
	 */
	public boolean loginout(String userId);
	
	
}
