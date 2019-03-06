package com.hp.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hp.modules.sys.entity.TUser;
import com.hp.modules.sys.entity.TUserAnswer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TUserAnswerDao extends BaseMapper<TUserAnswer> {
    int deleteByPrimaryKey(Long objectId);

    int insertSelective(TUserAnswer record);

    TUserAnswer selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(TUserAnswer record);

    int updateByPrimaryKey(TUserAnswer record);
}