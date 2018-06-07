package com.dzj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dzj.pojo.Bgm;
import com.dzj.server.BgmService;
import com.dzj.utils.JSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="Bgm接口",tags="Bgm接口controller")
@RestController
@RequestMapping(value="/bgm")
public class BgmController {

	@Autowired
	private BgmService bgmService;
	
	@ApiOperation(value="获取背景音乐列表", notes="获取背景音乐列表的接口")
	@GetMapping("/bgmlist")
	public JSONResult getBgmList() {
		List<Bgm> list = bgmService.getBgmList();
		return JSONResult.ok(list);
		
	}
}
