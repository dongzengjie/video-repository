package com.dzj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dzj.pojo.Users;
import com.dzj.pojo.vo.UsersVo;
import com.dzj.server.UserRegisterOrLoginServer;
import com.dzj.utils.JSONResult;
import com.dzj.utils.MD5Utils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "注册登陆接口", tags = "注册登陆controller")
public class UserRegisterOrLogin {
	@Autowired
	private UserRegisterOrLoginServer registerOrLoginServer;

	@ApiOperation(value = "用户注册", notes = "用户注册接口")
	@PostMapping(value = "/register")
	public JSONResult register(@RequestBody Users user) throws Exception {
		if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
			return JSONResult.errorMsg("用户名或密码不能为空!");
		}
		boolean isexist = registerOrLoginServer.queryUserIsExist(user.getUsername());

		if (!isexist) {
			user.setNickname(user.getUsername());
			user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
			user.setFaceImage("");
			user.setFansCounts(0);
			user.setFollowCounts(0);
			user.setReceiveLikeCounts(0);
			UsersVo usersVo =registerOrLoginServer.saveUser(user);
			usersVo.setPassword("");
			return JSONResult.ok(usersVo);
		} else {
			return JSONResult.errorMsg("用户名已经存在!");
		}

	}

	@ApiOperation(value = "用户登陆", notes = "用户登陆接口")
	@PostMapping(value = "/login")
	public JSONResult login(@RequestBody Users user) throws Exception {
		if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
			return JSONResult.errorMsg("用户名或密码不能为空!");
		}
		UsersVo userResult = registerOrLoginServer.userLogin(user.getUsername(), MD5Utils.getMD5Str(user.getPassword()));
		if(userResult == null) {
			return JSONResult.errorMsg("用户名或密码不正确！!");
		}else {
			userResult.setPassword("");
			return JSONResult.ok(userResult);
		}
	}
	
	@ApiOperation(value = "用户注销", notes = "用户注销接口")
	@PostMapping(value = "/loginout")
	public JSONResult loginout(@RequestBody Users user) {
		if(user == null || user.getId() == null) {
			return JSONResult.errorMsg("error!");
		}
		boolean result =  registerOrLoginServer.loginout(user.getId());
		if(result) {
			return JSONResult.ok();
		}else {
			return JSONResult.errorMsg("注销失败!");
		}	
	}

}
