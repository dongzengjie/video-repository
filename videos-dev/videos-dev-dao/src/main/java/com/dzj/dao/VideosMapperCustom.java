package com.dzj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dzj.pojo.Videos;
import com.dzj.pojo.vo.VideoVo;
import com.dzj.utils.MyMapper;



public interface VideosMapperCustom extends MyMapper<Videos> {
	
	List<VideoVo> queryAllVideos(@Param("videos") Videos videos);
}