package com.hp.wx.mapper;

import com.hp.wx.entity.TUserAnswer;

public interface TUserAnswerMapper {
    int deleteByPrimaryKey(Long objectId);

    int insert(TUserAnswer record);

    int insertSelective(TUserAnswer record);

    TUserAnswer selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(TUserAnswer record);

    int updateByPrimaryKey(TUserAnswer record);
}