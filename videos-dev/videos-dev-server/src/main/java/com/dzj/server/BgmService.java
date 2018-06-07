package com.dzj.server;

import java.util.List;

import com.dzj.pojo.Bgm;

public interface BgmService {

	/**
	 * 获取所有bgm
	 * @return
	 */
	List<Bgm> getBgmList();
}
