package com.dzj.server;
import org.springframework.web.multipart.MultipartFile;

import com.dzj.dto.PageResult;
import com.dzj.exception.UserException;
import com.dzj.exception.VideoException;
import com.dzj.pojo.Videos;

public interface VideoService {

	public boolean isLikeVideo(String userId,String videoId);
	
	/**
	 * 用户点赞
	 * @param userId 用户id
	 * @param videoCreateUserId 视频创造者id
	 * @param videoId 视频id
	 */
	public void userLikeVideo(String userId,String videoCreateUserId,String videoId);
	
	/**
	 * 用户取消点赞
	 * @param userId 用户id
	 * @param videoCreateUserId 视频创造者id
	 * @param videoId 视频id
	 */
	public void userUnLikeVideo(String userId,String videoCreateUserId,String videoId);
	
	/**
	 * 视频处理接口
	 * @param file
	 * @param userId
	 * @throws UserException
	 */
	public boolean userVideoHandle(MultipartFile file,String userId,Videos videos,String bgmId)throws UserException;

	/**
	 * 分页查询视频
	 * @param page 当前页数
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult getVideosByLimit(Videos videos,Integer isSaveHot,Integer page,Integer pageSize) throws VideoException;
	
	/**
	 * 分页查询用户收藏的视频
	 * @param userId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public PageResult getUserLikeVideoByLimit(String userId ,Integer page,Integer pageSize) throws VideoException;
}
