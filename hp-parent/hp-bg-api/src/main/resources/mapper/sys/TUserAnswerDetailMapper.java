package com.hp.wx.mapper;

import com.hp.wx.entity.TUserAnswerDetail;

public interface TUserAnswerDetailMapper {
    int deleteByPrimaryKey(Long objectId);

    int insert(TUserAnswerDetail record);

    int insertSelective(TUserAnswerDetail record);

    TUserAnswerDetail selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(TUserAnswerDetail record);

    int updateByPrimaryKey(TUserAnswerDetail record);
}