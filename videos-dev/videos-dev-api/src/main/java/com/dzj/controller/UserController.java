package com.dzj.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dzj.dao.VideosMapper;
import com.dzj.pojo.Users;
import com.dzj.pojo.vo.UsersVo;
import com.dzj.server.UserService;
import com.dzj.server.VideoService;
import com.dzj.utils.JSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@Api(value="用户操作接口", tags="用户操作controller")
@RestController
@RequestMapping(value="/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private VideoService videoService;
	
	@ApiOperation(value="上传用户头像", notes="上传用户头像的接口")
	@ApiImplicitParam(name="userId",value="用户id",required=true,dataType="String",paramType="query")
	@PostMapping(value="/userFaceImage",headers="content-type=multipart/form-data")
	public JSONResult userFaceImage(String userId,MultipartFile file) {
		
		String path =userService.userFaceImageHandle(file, userId);	
		return JSONResult.ok(path);
		
	}
	
	@ApiOperation(value="查询用户信息接口",notes="")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId",value="用户id",required=true,dataType="String",paramType="query"),
		@ApiImplicitParam(name="fanId",value="粉丝id",required=true,dataType="String",paramType="query")
	})
	@PostMapping(value="/queryUserInfo")
	public JSONResult queryUserInfo(String userId,String fanId) {
		if (StringUtils.isBlank(userId)) {
			return JSONResult.errorMsg("用户id不能为空...");
		}
		Users user = userService.queryUserById(userId);
		UsersVo usersVo =new UsersVo();
		BeanUtils.copyProperties(user, usersVo);
		
		boolean isFollow = userService.isFollow(userId, fanId);
		usersVo.setFollow(isFollow);
		return JSONResult.ok(usersVo);
		
	}
	@ApiOperation(value="用户与视频的点赞关系", notes="")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId", value="用户id",required = true,dataType="String",paramType="query"),
		@ApiImplicitParam(name="videoId", value="视频id",required = true,dataType="String",paramType="query"),	
	})
	@PostMapping(value ="/queryIsLike")
	public JSONResult queryIsLike(String videoId,String userId) {
	boolean	isLike = videoService.isLikeVideo(userId, videoId);
		return JSONResult.ok(isLike);
		
	}
	
	@ApiOperation(value="添加关注接口", notes="")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId", value="视频创造者id",required = true,dataType="String",paramType="query"),
		@ApiImplicitParam(name="fanId", value="粉丝id(当前所登陆的用户)",required = true,dataType="String",paramType="query"),	
	})
	@PostMapping(value ="/beYoufans")
	public JSONResult beYoufans(String userId ,String fanId) {
		
		userService.saveUserFanRelation(userId, fanId);
		return JSONResult.ok();
		
	}
	
	@ApiOperation(value="取消关注接口", notes="")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId", value="视频创造者id",required = true,dataType="String",paramType="query"),
		@ApiImplicitParam(name="fanId", value="粉丝id(当前所登陆的用户)",required = true,dataType="String",paramType="query"),	
	})
	@PostMapping(value ="/notbeYoufans")
	public JSONResult notbeYoufans(String userId ,String fanId) {
		
		userService.deleteUserFanRelation(userId, fanId);
		return JSONResult.ok();
		
	}
}
