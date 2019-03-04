package com.hp.wx.mapper;

import com.hp.wx.entity.QuestionNaire;

public interface QuestionNaireMapper {
    int deleteByPrimaryKey(Long objectId);

    int insert(QuestionNaire record);

    int insertSelective(QuestionNaire record);

    QuestionNaire selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(QuestionNaire record);

    int updateByPrimaryKey(QuestionNaire record);
}