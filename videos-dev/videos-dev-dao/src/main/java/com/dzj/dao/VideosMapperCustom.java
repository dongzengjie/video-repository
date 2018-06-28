package com.dzj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dzj.pojo.Videos;
import com.dzj.pojo.vo.UsersVo;
import com.dzj.pojo.vo.VideoVo;
import com.dzj.utils.MyMapper;

public interface VideosMapperCustom extends MyMapper<Videos> {
	
	List<VideoVo> queryAllVideos(@Param("videos") Videos videos);
	
	/**
	 * 增加喜欢数
	 * @param videoId
	 */
	public void addLikeCounts(String videoId);
	
	/**
	 * 减少喜欢数
	 * @param videoId
	 */
	public void reduceLikeCounts(String videoId);
	
	/**
	 * 查询用户收藏的视频
	 * @param userId
	 * @return
	 */
	List<VideoVo> queryUserLikeVideo(String userId);
	
	
	
}