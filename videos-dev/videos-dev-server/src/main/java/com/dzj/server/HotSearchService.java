package com.dzj.server;

import java.util.List;

public interface HotSearchService {

	/**
	 * 获取热搜词
	 * @return
	 */
	List<String> getHot();
}
