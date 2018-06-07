package com.dzj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dzj.server.UserService;
import com.dzj.utils.JSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
@Api(value="用户操作接口", tags="用户操作controller")
@RestController
@RequestMapping(value="/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@ApiOperation(value="上传用户头像", notes="上传用户头像的接口")
	@ApiImplicitParam(name="userId",value="用户id",required=true,dataType="String",paramType="query")
	@PostMapping(value="/userFaceImage",headers="content-type=multipart/form-data")
	public JSONResult userFaceImage(String userId,MultipartFile file) {
		
		String path =userService.userFaceImageHandle(file, userId);	
		return JSONResult.ok(path);
		
	}
	

}
