package com.dzj.server.impl;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dzj.config.ConfigClass;
import com.dzj.dao.BgmMapper;
import com.dzj.dao.VideosMapper;
import com.dzj.enums.UpLoadEnum;
import com.dzj.enums.VideoEnum;
import com.dzj.exception.UserException;
import com.dzj.pojo.Bgm;
import com.dzj.pojo.Videos;
import com.dzj.server.VideoService;
import com.dzj.utils.FetchVideoCover;
import com.dzj.utils.MergeVideoMp3;
import com.dzj.utils.PathUtil;
import com.dzj.utils.UploadUtil;

@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	private VideosMapper videosMapper;
	@Autowired
	private BgmMapper bgmMapper;

	@Transactional(propagation = Propagation.REQUIRED)
	public boolean userVideoHandle(MultipartFile file, String userId, Videos videos, String bgmId)
			throws UserException {

		if (userId == null || StringUtils.isEmpty(userId)) {
			throw new UserException("用户id不存在");
		}
		
		String dbpath = UploadUtil.handle(file, userId, UpLoadEnum.VIDEO);
		if (dbpath == null || StringUtils.isEmpty(dbpath)) {
			return false;
		}
		
		if (bgmId != null && !StringUtils.isEmpty(bgmId)) {
			Bgm bgm = bgmMapper.selectByPrimaryKey(bgmId);
			videos.setAudioId(bgmId);
			MergeVideoMp3 videoMp3 = new MergeVideoMp3(ConfigClass.FFMPEG_EXE);
			String videoMp4Path= PathUtil.getLocalPath(userId, UpLoadEnum.VIDEO.getMsg())+UUID.randomUUID().toString()+".mp4";
			
			
			String finalPath =PathUtil.getBasePath() + videoMp4Path;
		
			try {
		
				videoMp3.convertor(PathUtil.getBasePath()+dbpath, PathUtil.getBasePath() +bgm.getPath(), videos.getVideoSeconds(),finalPath);		
			} catch (IOException e) {
				throw new UserException("视频处理失败");
			}
			videos.setVideoPath(videoMp4Path);
			
		} else {
			videos.setVideoPath(dbpath);
		}
		
		//截图
		String coverPath =  PathUtil.getLocalPath(userId, UpLoadEnum.VIDEO.getMsg())+UUID.randomUUID().toString()+".jpg";
		FetchVideoCover fetchVideoCover =new FetchVideoCover(ConfigClass.FFMPEG_EXE);
		try {
			fetchVideoCover.getCover(PathUtil.getBasePath()+dbpath, PathUtil.getBasePath()+coverPath);
		} catch (IOException e) {
			throw new UserException("截图失败");
		}
		videos.setCoverPath(coverPath);
		videos.setUserId(userId);
		videos.setCreateTime(new Date());
		videos.setStatus(VideoEnum.SUCCESS.getStatus());

		int result = videosMapper.insertSelective(videos);
		if (result <= 0) {
			throw new UserException("视频上传失败");
		}
		return true;

	}

}
