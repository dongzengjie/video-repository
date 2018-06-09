package com.dzj.server.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dzj.dao.SearchRecordsMapper;
import com.dzj.server.HotSearchService;
@Service
public class HotSearchServiceImpl implements HotSearchService{
	@Autowired
	private SearchRecordsMapper searchRecordsMapper;
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> getHot() {
	
		return searchRecordsMapper.getHotSearch();
	}

}
