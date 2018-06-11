package com.dzj.redis.key;

public interface KeyPrefix {

	public int expireSeconds();
	
	public String getKey();
}
