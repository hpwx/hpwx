package com.hp.mobile.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hp.mobile.entity.UserInfo;
@Mapper
public interface UserInfoMapper {
    int deleteByPrimaryKey(Long objectId);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
  
    UserInfo selectUserByOpenId(String openId);
    
    int  updatebyOpenid( UserInfo userinfo);
}