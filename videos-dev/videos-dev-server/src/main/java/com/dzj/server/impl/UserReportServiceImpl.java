package com.dzj.server.impl;

import java.util.Date;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzj.dao.UsersReportMapper;
import com.dzj.exception.UserReportException;
import com.dzj.pojo.UsersReport;
import com.dzj.server.UserReportService;
@Service
public class UserReportServiceImpl implements UserReportService {
	@Autowired
	private UsersReportMapper usersReportMapper;
	@Autowired
	private Sid sid;
	
	public void addUserReport(UsersReport usersReport) throws UserReportException{
		usersReport.setCreateDate(new Date());
		usersReport.setId(sid.nextShort());
		
		int result =usersReportMapper.insert(usersReport);
		if(result <=0) {
			throw new UserReportException("举报失败！");
		}
		
	}

}
