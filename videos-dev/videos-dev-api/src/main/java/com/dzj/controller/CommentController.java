package com.dzj.controller;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dzj.config.ConfigClass;
import com.dzj.dto.PageResult;
import com.dzj.pojo.Comments;
import com.dzj.server.CommentService;
import com.dzj.utils.JSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "评论接口", tags = "")
@RestController
@RequestMapping(value = "/comment")
public class CommentController {
	@Autowired
	private CommentService commentService;
	@Autowired
	private Sid sid;

	@ApiOperation(value = "添加评论")
	@PostMapping(value = "/addComment")
	public JSONResult addComment(@RequestBody Comments comments, String fatherCommentId, String toUserId) {

		if (fatherCommentId != null && !StringUtils.isBlank(fatherCommentId)) {
			comments.setFatherCommentId(fatherCommentId);

		}

		if (toUserId != null && !StringUtils.isBlank(toUserId)) {
			comments.setToUserId(toUserId);

		}

		comments.setCreateTime(new Date());

		comments.setId(sid.nextShort());
		commentService.saveComment(comments);
		return JSONResult.ok();

	}

	@PostMapping(value = "/getComment")
	public JSONResult getComment(String videoId, Integer page) {

		PageResult pageResult = commentService.queryCommentByLimit(videoId, page, ConfigClass.PAGR_SIZE);

		return JSONResult.ok(pageResult);

	}
}
