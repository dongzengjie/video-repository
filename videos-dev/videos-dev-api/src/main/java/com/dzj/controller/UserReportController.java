package com.dzj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dzj.pojo.UsersReport;
import com.dzj.server.UserReportService;
import com.dzj.utils.JSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="用户举报",tags="举报controller")
@RestController
@RequestMapping(value="/report")
public class UserReportController {
	@Autowired
	private UserReportService userReportService;
	
	@ApiOperation(value="添加举报信息接口",notes="")
	@PostMapping(value="/saveReport")
	public JSONResult saveReport(@RequestBody UsersReport usersReport) {
		
		userReportService.addUserReport(usersReport);
		
		return JSONResult.ok("举报成功");
		
	}
}
