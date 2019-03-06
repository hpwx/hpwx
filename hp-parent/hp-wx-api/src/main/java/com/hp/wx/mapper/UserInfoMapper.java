package com.hp.wx.mapper;

import com.hp.wx.entity.UserInfo;

public interface UserInfoMapper {
    int deleteByPrimaryKey(Long objectId);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
}