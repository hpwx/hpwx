package com.hp.wx.mapper;

import com.hp.wx.entity.TQuestionnaire;

public interface TQuestionnaireMapper {
    int deleteByPrimaryKey(Long objectId);

    int insert(TQuestionnaire record);

    int insertSelective(TQuestionnaire record);

    TQuestionnaire selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(TQuestionnaire record);

    int updateByPrimaryKey(TQuestionnaire record);
}