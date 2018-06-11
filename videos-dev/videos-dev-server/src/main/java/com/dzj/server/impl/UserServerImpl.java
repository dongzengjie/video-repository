package com.dzj.server.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dzj.dao.UsersFansMapper;
import com.dzj.dao.UsersMapper;
import com.dzj.enums.UpLoadEnum;
import com.dzj.exception.UserException;
import com.dzj.pojo.Users;
import com.dzj.pojo.UsersFans;
import com.dzj.pojo.Videos;
import com.dzj.server.UserService;
import com.dzj.utils.UploadUtil;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class UserServerImpl implements UserService {

	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private UsersFansMapper usersFansMapper;
	@Autowired
	private Sid sid;

	@Transactional(propagation = Propagation.REQUIRED)
	public String userFaceImageHandle(MultipartFile file, String userId) throws UserException {
		if (userId == null || StringUtils.isEmpty(userId)) {
			throw new UserException("用户id不存在");
		}
		String dbpath = UploadUtil.handle(file, userId, UpLoadEnum.FACE);
		Users record = new Users();
		record.setFaceImage(dbpath);
		record.setId(userId);
		int result = usersMapper.updateByPrimaryKeySelective(record);
		if (result <= 0) {
			throw new UserException("上传失败！");
		}
		return dbpath;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public Users queryUserById(String userId) {
		Example example = new Example(Users.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("id", userId);
		Users users = usersMapper.selectOneByExample(example);

		return users;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void saveUserFanRelation(String userId, String fansId) {

		usersMapper.addFansCounts(userId);
		usersMapper.addFollowCounts(fansId);
		UsersFans usersFans = new UsersFans();
		usersFans.setId(sid.nextShort());
		usersFans.setUserId(userId);
		usersFans.setFanId(fansId);
		usersFansMapper.insert(usersFans);

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteUserFanRelation(String userId, String fansId) {
		usersMapper.reduceFansCounts(userId);
		usersMapper.reduceFollowCounts(fansId);
		Example example = new Example(UsersFans.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("fanId", fansId);
		usersFansMapper.deleteByExample(example);
	}

	@Override
	public boolean isFollow(String userId, String fansId) {

		Example example = new Example(UsersFans.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("fanId", fansId);

		List<UsersFans> usersFansList = usersFansMapper.selectByExample(example);
		if(usersFansList ==null || usersFansList.size()<=0) {
			return false;
		}
		return true;
	}

}
