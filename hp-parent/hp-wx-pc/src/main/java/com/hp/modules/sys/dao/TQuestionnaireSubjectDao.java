package com.hp.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hp.modules.sys.entity.TQuestionnaire;
import com.hp.modules.sys.entity.TQuestionnaireSubject;
import com.hp.modules.sys.entity.TSubject;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface TQuestionnaireSubjectDao extends BaseMapper<TQuestionnaireSubject> {
    int deleteByPrimaryKey(Long objectId);


    int insertSelective(TQuestionnaireSubject record);

    TQuestionnaireSubject selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(TQuestionnaireSubject record);

    int updateByPrimaryKey(TQuestionnaireSubject record);

    int insertBatch(List<TQuestionnaireSubject> list);

}