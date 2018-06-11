package com.dzj.server.impl;

import java.util.Date;
import java.util.UUID;

import org.apache.catalina.User;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dzj.dao.UsersMapper;
import com.dzj.pojo.Users;
import com.dzj.pojo.vo.UsersVo;
import com.dzj.redis.RedisService;
import com.dzj.redis.key.UserKey;
import com.dzj.server.UserRegisterOrLoginServer;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
@Service
public class UserRegisterOrLoginServerImpl implements UserRegisterOrLoginServer {
	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private Sid sid;
	@Autowired
	private RedisService redisService;
	
	
	
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public boolean queryUserIsExist(String username) {
		Users user =new Users();
		user.setUsername(username);
		
		Users users =usersMapper.selectOne(user);
		
		return users == null ? false : true;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	public UsersVo saveUser(Users user) {
		UsersVo usersVo = null;
		String id = sid.nextShort();
		user.setId(id);
		int result = usersMapper.insert(user);
		if(result >0) {
			usersVo = setUserRedisSessionToken(user);
		}
		return usersVo;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public UsersVo userLogin(String username, String password) {
		UsersVo usersVo = null;
		Example example =new Example(Users.class);
	
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("username",username);
		criteria.andEqualTo("password",password);
		Users users = usersMapper.selectOneByExample(example);
		if(users !=null) {
			usersVo =  setUserRedisSessionToken(users);
		}
		return usersVo;
		
		
	}
	
	/**
	 * 设置用户token
	 * @param user
	 * @param timeOut
	 * @return
	 */
	private UsersVo setUserRedisSessionToken(Users user) {
	
		Date date =new Date();
	
		String token = UUID.randomUUID().toString()+date.getTime();
		redisService.set(UserKey.token, user.getId(), token);
		UsersVo usersVo =new UsersVo();
		BeanUtils.copyProperties(user, usersVo);
		usersVo.setToken(token);
		return usersVo;
		
	}
	
	
	public boolean loginout(String userId) {
		
		long result = redisService.del(UserKey.token, userId);
		if(result<=0) {
			return false;
		}
		return true;
	}

}
