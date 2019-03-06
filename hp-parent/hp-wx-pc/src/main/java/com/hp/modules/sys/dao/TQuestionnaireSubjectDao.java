package com.hp.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hp.modules.sys.entity.TQuestionnaire;
import com.hp.modules.sys.entity.TQuestionnaireSubject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TQuestionnaireSubjectDao extends BaseMapper<TQuestionnaireSubject> {
    int deleteByPrimaryKey(Long objectId);


    int insertSelective(TQuestionnaireSubject record);

    TQuestionnaireSubject selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(TQuestionnaireSubject record);

    int updateByPrimaryKey(TQuestionnaireSubject record);
}