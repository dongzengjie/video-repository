package com.dzj.redis;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {
	@Autowired
	private JedisPool jedisPool;

	/**
	 * 获取单个对象
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <T> T get(String key, Class<T> clazz) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String str = jedis.get(key);

			T t = stringToBean(str, clazz);
			return t;
		} finally {
			returnToPool(jedis);
		}
	}

	/**
	 * 判断是否存在
	 * 
	 * @param key
	 * @return
	 */
	public <T> boolean exist(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.exists(key);
		} finally {
			returnToPool(jedis);
		}
	}

	/**
	 * 增加一个值
	 * 
	 * @param keyprefix
	 * @param key
	 * @return
	 */
	public <T> Long incr(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();

			return jedis.incr(key);
		} finally {
			returnToPool(jedis);
		}

	}

	/**
	 * 减少一个值
	 * 
	 * @param keyprefix
	 * @param key
	 * @return
	 */
	public <T> Long decr(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			
			return jedis.decr(key);
		} finally {
			returnToPool(jedis);
		}

	}
	
	/**
	 * 删除一个值
	 * 
	 * @param keyprefix
	 * @param key
	 * @return
	 */
	public <T> Long del(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			
			return jedis.del(key);
		} finally {
			returnToPool(jedis);
		}

	}

	/**
	 * 设置值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public <T> boolean set(String key, T value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String str = beanToString(value);
			if (str == null || str.length() <= 0) {
				return false;
			}
			jedis.set(key, str);
			return true;
		} finally {
			returnToPool(jedis);
		}

	}

	/**
	 * 设置值和过期时间
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public <T> boolean setValueAndOverTime(String key, T value, int timeOut) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String str = beanToString(value);
			if (str == null || str.length() <= 0) {
				return false;
			}
			if (timeOut <= 0) {
				jedis.set(key, str);
			}
			jedis.setex(key, timeOut, str);
			return true;
		} finally {
			returnToPool(jedis);
		}

	}

	private static <T> T stringToBean(String str, Class<T> clazz) {

		if (str == null || str.length() <= 0 || clazz == null) {
			return null;
		}

		if (clazz == int.class || clazz == Integer.class) {
			return (T) Integer.valueOf(str);
		} else if (clazz == String.class) {
			return (T) str;
		} else if (clazz == long.class || clazz == Long.class) {
			return (T) Long.valueOf(str);
		} else {
			ObjectMapper objectMapper = new ObjectMapper();

			try {
				return objectMapper.readValue(str, clazz);
			} catch (Exception e) {
				return null;
			}
		}

	}

	public static <T> String beanToString(T value) {
		if (value == null) {
			return null;
		}
		Class<?> clazz = value.getClass();
		if (clazz == int.class || clazz == Integer.class) {
			return "" + value;
		} else if (clazz == String.class) {
			return (String) value;
		} else if (clazz == long.class || clazz == Long.class) {

			return "" + value;
		} else {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				return objectMapper.writeValueAsString(clazz);
			} catch (JsonProcessingException e) {
				return null;
			}
		}
	}

	private void returnToPool(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}

	}

}
