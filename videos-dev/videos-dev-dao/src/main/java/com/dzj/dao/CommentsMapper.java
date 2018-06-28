package com.dzj.dao;

import java.util.List;

import com.dzj.pojo.Comments;
import com.dzj.pojo.vo.CommentVo;
import com.dzj.utils.MyMapper;

public interface CommentsMapper extends MyMapper<Comments> {
	
	public int insertComment(Comments comments);
	
	
	List<CommentVo> queryComment(String videoId);
}