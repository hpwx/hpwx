package com.hp.wx.mapper;

import com.hp.wx.entity.TQuestionnaireSubject;

public interface TQuestionnaireSubjectMapper {
    int deleteByPrimaryKey(Long objectId);

    int insert(TQuestionnaireSubject record);

    int insertSelective(TQuestionnaireSubject record);

    TQuestionnaireSubject selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(TQuestionnaireSubject record);

    int updateByPrimaryKey(TQuestionnaireSubject record);
}