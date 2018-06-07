package com.dzj.dao;

import java.util.List;

import com.dzj.pojo.Videos;
import com.dzj.pojo.vo.VideoVo;
import com.dzj.utils.MyMapper;

public interface VideosMapperCustom extends MyMapper<Videos> {
	
	List<VideoVo> queryAllVideos();
}