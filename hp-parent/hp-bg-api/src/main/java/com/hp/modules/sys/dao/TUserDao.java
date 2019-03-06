package com.hp.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hp.modules.sys.entity.TUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TUserDao extends BaseMapper<TUser> {
    int deleteByPrimaryKey(Long objectId);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);
}