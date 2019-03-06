package com.hp.modules.sys.dao;

import com.hp.modules.sys.entity.TQuestionnaireSubject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TQuestionnaireSubjectDao {
    int deleteByPrimaryKey(Long objectId);

    int insert(TQuestionnaireSubject record);

    int insertSelective(TQuestionnaireSubject record);

    TQuestionnaireSubject selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(TQuestionnaireSubject record);

    int updateByPrimaryKey(TQuestionnaireSubject record);
}