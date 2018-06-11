package com.dzj.pojo.vo;

import com.dzj.pojo.Users;

public class UsersVo extends Users{

	private String token;
	private boolean isFollow;
	

	public boolean isFollow() {
		return isFollow;
	}

	public void setFollow(boolean isFollow) {
		this.isFollow = isFollow;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
