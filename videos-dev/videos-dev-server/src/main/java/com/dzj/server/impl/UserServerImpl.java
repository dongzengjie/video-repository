package com.dzj.server.impl;

import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dzj.dao.UsersMapper;
import com.dzj.enums.UpLoadEnum;
import com.dzj.exception.UserException;
import com.dzj.pojo.Users;
import com.dzj.pojo.Videos;
import com.dzj.server.UserService;
import com.dzj.utils.UploadUtil;
@Service
public class UserServerImpl implements UserService {

	@Autowired
	private UsersMapper usersMapper;
	
	@Transactional(propagation=Propagation.REQUIRED)
	public String userFaceImageHandle(MultipartFile file, String userId) throws UserException {
		if(userId == null || StringUtils.isEmpty(userId)) {
			throw new UserException("用户id不存在");
		}
		String dbpath = UploadUtil.handle(file, userId,UpLoadEnum.FACE);
		Users record =new Users();
		record.setFaceImage(dbpath);
		record.setId(userId);
		int result =usersMapper.updateByPrimaryKeySelective(record);
		if(result <=0) {
			throw new UserException("上传失败！");
		}
		return dbpath;	
	}



}
