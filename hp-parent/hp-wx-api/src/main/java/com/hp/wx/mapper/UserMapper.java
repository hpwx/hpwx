package com.hp.wx.mapper;

import com.hp.wx.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(Long objectId);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Long objectId);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);
}