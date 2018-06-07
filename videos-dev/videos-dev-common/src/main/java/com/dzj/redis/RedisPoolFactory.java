package com.dzj.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service
public class RedisPoolFactory {

	@Autowired
	private RedisConfig config;
	
	@Bean
	public JedisPool jedisPool() {
		
		JedisPoolConfig jedisPoolConfig =new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(config.getPoolMaxTotal());
		jedisPoolConfig.setMaxTotal(config.getPoolMaxTotal());
		jedisPoolConfig.setMaxWaitMillis(config.getPoolMaxWait() * 1000);

		JedisPool jedisPool = new JedisPool(jedisPoolConfig, config.getHost(), config.getPort(),
				config.getTimeout() * 1000, config.getPassword(), 0);
		return jedisPool;
	}
	
	
}
