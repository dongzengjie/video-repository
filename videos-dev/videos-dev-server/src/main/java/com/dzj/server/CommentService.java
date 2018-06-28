package com.dzj.server;

import com.dzj.dto.PageResult;
import com.dzj.pojo.Comments;

public interface CommentService {

	/**
	 * 保存评论
	 * @param comments
	 */
	public void saveComment(Comments comments);
	
	public PageResult queryCommentByLimit(String videoId, Integer page, Integer pageSize);
}
