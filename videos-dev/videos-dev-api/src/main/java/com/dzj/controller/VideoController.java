package com.dzj.controller;

import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dzj.config.ConfigClass;
import com.dzj.dto.PageResult;
import com.dzj.pojo.Videos;
import com.dzj.server.VideoService;
import com.dzj.utils.JSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
@Api(value="视频作接口", tags="视频操作controller")
@RestController
@RequestMapping(value="/video")
public class VideoController {
	@Autowired
	private VideoService videoService;

	@ApiOperation(value="上传视频", notes="上传视频的接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId", value="用户id", required=true, 
				dataType="String", paramType="form"),
		@ApiImplicitParam(name="bgmId", value="背景音乐id", required=false, 
				dataType="String", paramType="form"),
		@ApiImplicitParam(name="videoSeconds", value="背景音乐播放长度", required=true, 
				dataType="String", paramType="form"),
		@ApiImplicitParam(name="videoWidth", value="视频宽度", required=true, 
				dataType="String", paramType="form"),
		@ApiImplicitParam(name="videoHeight", value="视频高度", required=true, 
				dataType="String", paramType="form"),
		@ApiImplicitParam(name="desc", value="视频描述", required=false, 
				dataType="String", paramType="form")
	})
	@PostMapping(value="/videoUpLoad",headers="content-type=multipart/form-data")
	public JSONResult videoUpLoad(String userId,String bgmId,double videoSeconds, 
			int videoWidth, int videoHeight,
			String desc,@ApiParam(value="短视频", required=true) MultipartFile file) {
		Videos videos =new Videos();
		String videoId= Sid.next();
		videos.setId(videoId);
		
		if(!StringUtils.isEmpty(bgmId)) {
			videos.setVideoDesc(desc);
		}
		
		videos.setVideoHeight(videoHeight);
		videos.setVideoWidth(videoWidth);
		videos.setVideoSeconds((float)videoSeconds);
		
		videoService.userVideoHandle(file, userId, videos,bgmId);
		return JSONResult.ok();
		
	}
	/**
	 * 
	 * @param videos
	 * @param isSaveHot 1 保存热搜词  0 不保存
	 * @param page
	 * @return
	 */
	@ApiImplicitParams({
		@ApiImplicitParam(name="page", value="当前页数",required = true,dataType="Integer",paramType="query"),
		@ApiImplicitParam(name="isSaveHot", value="是否保存热搜词语",required = true,dataType="Integer",paramType="query")
	})
	@PostMapping(value="/getAllVideo")
	public JSONResult getAllVideo(@RequestBody Videos videos,Integer isSaveHot, Integer page) {
		if(page == null || page <=0) {
			page = 1;
		}
	
		PageResult pageResult =videoService.getVideosByLimit(videos,isSaveHot,page, ConfigClass.PAGR_SIZE);
		
		return JSONResult.ok(pageResult);
		
	}
}
