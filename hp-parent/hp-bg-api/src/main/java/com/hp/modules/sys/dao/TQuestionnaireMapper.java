package com.hp.modules.sys.dao;

import com.hp.modules.sys.entity.TQuestionnaire;

public interface TQuestionnaireMapper {
    int deleteByPrimaryKey(Long objectId);

    int insert(TQuestionnaire record);

    int insertSelective(TQuestionnaire record);

    TQuestionnaire selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(TQuestionnaire record);

    int updateByPrimaryKey(TQuestionnaire record);
}