package com.dzj.dao;

import java.util.List;

import com.dzj.pojo.Users;
import com.dzj.pojo.vo.UsersVo;
import com.dzj.utils.MyMapper;

public interface UsersMapper extends MyMapper<Users> {
	
	/**
	 * 增加用户喜欢数
	 * @param userId
	 */
	public void addReceiveLikeCounts(String userId);
	
	/**
	 * 减少用户喜欢数
	 * @param userId
	 */
	public void reduceReceiveLikeCounts(String userId);
	
	
	/**
	 * 增加粉丝数
	 * @param videoCreateUserId
	 */
	public void addFansCounts(String videoCreateUserId);
	
	/**
	 * 减少粉丝数
	 * @param videoCreateUserId
	 */
	public void reduceFansCounts(String videoCreateUserId);
	
	/**
	 * 增加关注数
	 * @param userId
	 */
	public void addFollowCounts(String userId);
	
	/**
	 * 减少关注数
	 * @param userId
	 */
	public void reduceFollowCounts(String userId);
	
	/**
	 * 查询用户的关注
	 * @param fansId
	 * @return
	 */
	List<Users> queryUserFollow(String fansId); 
}