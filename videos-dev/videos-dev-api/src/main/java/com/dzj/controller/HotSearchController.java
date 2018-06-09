package com.dzj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dzj.server.HotSearchService;
import com.dzj.utils.JSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="热搜接口",tags="")
@RestController
@RequestMapping(value="/hot")
public class HotSearchController {
	@Autowired
	private HotSearchService hotSearchService;
	
	@ApiOperation(value="获取热搜的接口" , notes="")
	@GetMapping(value="/getHotSearch")
	public JSONResult getHotSearch() {
		
		return JSONResult.ok(hotSearchService.getHot());
		
	}
}
