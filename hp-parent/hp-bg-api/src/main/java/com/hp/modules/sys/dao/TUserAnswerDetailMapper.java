package com.hp.modules.sys.dao;

import com.hp.modules.sys.entity.TUserAnswerDetail;

public interface TUserAnswerDetailMapper {
    int deleteByPrimaryKey(Long objectId);

    int insert(TUserAnswerDetail record);

    int insertSelective(TUserAnswerDetail record);

    TUserAnswerDetail selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(TUserAnswerDetail record);

    int updateByPrimaryKey(TUserAnswerDetail record);
}