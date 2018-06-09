package com.dzj.server;



import org.springframework.web.multipart.MultipartFile;

import com.dzj.dto.PageResult;
import com.dzj.exception.UserException;
import com.dzj.exception.VideoException;
import com.dzj.pojo.Videos;

public interface VideoService {

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
}
