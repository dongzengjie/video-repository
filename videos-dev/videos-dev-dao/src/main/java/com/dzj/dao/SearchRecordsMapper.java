package com.dzj.dao;

import java.util.List;

import com.dzj.pojo.SearchRecords;
import com.dzj.utils.MyMapper;

public interface SearchRecordsMapper extends MyMapper<SearchRecords> {
	/**
	 * 获取热搜
	 * @return
	 */
	List<String> getHotSearch();
}