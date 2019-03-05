package com.hp.modules.sys.dao;

import com.hp.modules.sys.entity.TUser;

public interface TUserMapper {
    int deleteByPrimaryKey(Long objectId);

    int insert(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);
}