package com.dzj.server.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
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
import com.dzj.dao.SearchRecordsMapper;
import com.dzj.dao.UsersLikeVideosMapper;
import com.dzj.dao.UsersMapper;
import com.dzj.dao.VideosMapper;
import com.dzj.dao.VideosMapperCustom;
import com.dzj.dto.PageResult;
import com.dzj.enums.UpLoadEnum;
import com.dzj.enums.VideoEnum;
import com.dzj.exception.UserException;
import com.dzj.exception.VideoException;
import com.dzj.pojo.Bgm;
import com.dzj.pojo.SearchRecords;
import com.dzj.pojo.UsersLikeVideos;
import com.dzj.pojo.Videos;
import com.dzj.pojo.vo.VideoVo;
import com.dzj.server.VideoService;
import com.dzj.utils.FetchVideoCover;
import com.dzj.utils.FetchVideoGif;
import com.dzj.utils.FileDel;
import com.dzj.utils.MergeVideoMp3;
import com.dzj.utils.PathUtil;
import com.dzj.utils.UploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	private VideosMapper videosMapper;
	@Autowired
	private BgmMapper bgmMapper;
	@Autowired
	private VideosMapperCustom videosMapperCustom;
	@Autowired
	private SearchRecordsMapper searchRecordsMapper;
	@Autowired
	private UsersLikeVideosMapper usersLikeVideosMapper;
	@Autowired
	private UsersMapper usersMapper;

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
			//FileDel.delFile(PathUtil.getBasePath()+dbpath);
			videos.setVideoPath(videoMp4Path);
			
		} else {
			videos.setVideoPath(dbpath);
		}
		
		//截图
		//String coverPath =  PathUtil.getLocalPath(userId, UpLoadEnum.VIDEO.getMsg())+UUID.randomUUID().toString()+".jpg";
		//FetchVideoCover fetchVideoCover =new FetchVideoCover(ConfigClass.FFMPEG_EXE);
		String coverPath =  PathUtil.getLocalPath(userId, UpLoadEnum.VIDEO.getMsg())+UUID.randomUUID().toString()+".gif";
		FetchVideoGif fetchVideoGif =new FetchVideoGif(ConfigClass.FFMPEG_EXE);
		try {
			fetchVideoGif.getGIF(PathUtil.getBasePath()+dbpath, videos.getVideoSeconds(),  PathUtil.getBasePath()+coverPath);
			//fetchVideoCover.getCover(PathUtil.getBasePath()+dbpath, PathUtil.getBasePath()+coverPath);
		} catch (IOException e) {
			throw new UserException("截图失败");
		}
		if (bgmId != null && !StringUtils.isEmpty(bgmId)) {
			FileDel.delFile(PathUtil.getBasePath()+dbpath);
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

	@Transactional(propagation = Propagation.REQUIRED)
	public PageResult getVideosByLimit(Videos videos,Integer isSaveHot,Integer page, Integer pageSize)throws VideoException {
		if(isSaveHot !=null && isSaveHot ==1 && videos.getVideoDesc() !=null && videos.getVideoDesc() !="") {
			SearchRecords record =new SearchRecords();
			record.setId(Sid.next());
			record.setContent(videos.getVideoDesc());
			searchRecordsMapper.insert(record);
		}
		
		PageHelper.startPage(page, pageSize);
		List<VideoVo> videolist=null;
		try {
			videolist = videosMapperCustom.queryAllVideos(videos);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		if(videolist ==null) {
			throw new VideoException("视频查询失败~~");
		}
		
		PageInfo<VideoVo> pageInfo = new PageInfo<>(videolist);
		PageResult pageResult =new PageResult();
		pageResult.setRows(videolist);
		pageResult.setPage(page);
		pageResult.setTotal(pageInfo.getPages());
		pageResult.setRecords(pageInfo.getTotal());
		return pageResult;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void userLikeVideo(String userId, String videoCreateUserId, String videoId) {

		videosMapperCustom.addLikeCounts(videoId);
		usersMapper.addReceiveLikeCounts(videoCreateUserId);
		UsersLikeVideos usersLikeVideos =new UsersLikeVideos();
		usersLikeVideos.setId(Sid.next());
		usersLikeVideos.setUserId(userId);
		usersLikeVideos.setVideoId(videoId);
		usersLikeVideosMapper.insert(usersLikeVideos);
		
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void userUnLikeVideo(String userId, String videoCreateUserId, String videoId) {

		videosMapperCustom.reduceLikeCounts(videoId);
		usersMapper.reduceReceiveLikeCounts(videoCreateUserId);
		
		Example example =new Example(UsersLikeVideos.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("videoId", videoId);
		usersLikeVideosMapper.deleteByExample(example);
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	public boolean isLikeVideo(String userId, String videoId) {
		if(userId == null || videoId == null) {
			return false;
		}
		Example example =new Example(UsersLikeVideos.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("videoId", videoId);
		List<UsersLikeVideos> likeVideosList = usersLikeVideosMapper.selectByExample(example);
		if(likeVideosList == null || likeVideosList.size() <= 0) {
			return false;
		}
		return true;
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	public PageResult getUserLikeVideoByLimit(String userId, Integer page, Integer pageSize) throws VideoException{
		PageHelper.startPage(page, pageSize);
		
		List<VideoVo> videoVosList = videosMapperCustom.queryUserLikeVideo(userId);
		if(videoVosList==null || videoVosList.size()<=0) {
			throw new VideoException("视频查询失败~~");
		}
		PageInfo<VideoVo> pageInfo = new PageInfo<>(videoVosList);
		PageResult pageResult =new PageResult();
		pageResult.setRows(videoVosList);
		pageResult.setPage(page);
		pageResult.setTotal(pageInfo.getPages());
		pageResult.setRecords(pageInfo.getTotal());
		return pageResult;
	}

}
