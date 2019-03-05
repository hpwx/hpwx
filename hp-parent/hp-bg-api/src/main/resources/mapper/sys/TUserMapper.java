package com.hp.wx.mapper;

import com.hp.wx.entity.TUser;

public interface TUserMapper {
    int deleteByPrimaryKey(Long objectId);

    int insert(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);
}