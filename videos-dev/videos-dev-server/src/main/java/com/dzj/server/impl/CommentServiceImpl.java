package com.dzj.server.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dzj.dao.CommentsMapper;
import com.dzj.dto.PageResult;
import com.dzj.exception.VideoException;
import com.dzj.pojo.Comments;
import com.dzj.pojo.vo.CommentVo;
import com.dzj.pojo.vo.VideoVo;
import com.dzj.server.CommentService;
import com.dzj.utils.TimeAgoUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentsMapper commentsMapper;

	@Transactional(propagation = Propagation.SUPPORTS)
	public void saveComment(Comments comments) {

		commentsMapper.insert(comments);

	}
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageResult queryCommentByLimit(String videoId, Integer page, Integer pageSize) {
		PageHelper.startPage(page, pageSize);

		List<CommentVo> CommentVoList = commentsMapper.queryComment(videoId);

		for (CommentVo c : CommentVoList) {
			String timeAgo = TimeAgoUtils.format(c.getCreateTime());
			c.setTimeAgoStr(timeAgo);
		}
		PageInfo<CommentVo> pageInfo = new PageInfo<>(CommentVoList);
		PageResult pageResult = new PageResult();
		pageResult.setRows(CommentVoList);
		pageResult.setPage(page);
		pageResult.setTotal(pageInfo.getPages());
		pageResult.setRecords(pageInfo.getTotal());
		return pageResult;
	}

}
