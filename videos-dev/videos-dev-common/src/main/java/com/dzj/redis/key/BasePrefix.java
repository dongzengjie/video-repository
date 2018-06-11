package com.dzj.redis.key;

public class BasePrefix implements KeyPrefix {

	private int expireSeconds ;
	private String key;
	
	
	
	public BasePrefix(int expireSeconds, String key) {
		this.expireSeconds = expireSeconds;
		this.key = key;
	}
	
	public BasePrefix(String key) {
		this(0, key);
		
	}
	//0代表不会过期
	public int expireSeconds() {
		
		return expireSeconds;
	}

	public String getKey() {
		String className = getClass().getName();
		return className+":" + key;
	}

}
