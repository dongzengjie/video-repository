package com.dzj.redis.key;

public class UserKey extends BasePrefix {

	private static final int TIMES = 60 * 60 * 3;//3小时
	public UserKey(int expireSeconds, String key) {
		super(expireSeconds, key);
		// TODO Auto-generated constructor stub
	}

	public static UserKey token =new UserKey(TIMES, "userToken");
}
