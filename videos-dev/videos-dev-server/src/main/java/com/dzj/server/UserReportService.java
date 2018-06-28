package com.dzj.server;

import com.dzj.exception.UserReportException;
import com.dzj.pojo.UsersReport;

public interface UserReportService {
	
	/**
	 * 添加举报信息
	 * @param usersReport
	 */
	public void addUserReport(UsersReport usersReport) throws UserReportException ;
}
