package com.dzj.server.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzj.dao.BgmMapper;
import com.dzj.pojo.Bgm;
import com.dzj.server.BgmService;

@Service
public class BgmServiceImpl implements BgmService {

	@Autowired
	private BgmMapper bgmMapper;
	
	public List<Bgm> getBgmList() {
		 
		return bgmMapper.selectAll();
	}

}
