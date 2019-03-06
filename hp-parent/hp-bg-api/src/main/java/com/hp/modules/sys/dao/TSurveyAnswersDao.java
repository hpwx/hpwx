package com.hp.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hp.modules.sys.entity.TSurveyAnswers;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TSurveyAnswersDao extends BaseMapper<TSurveyAnswers> {
    int deleteByPrimaryKey(Long objectId);

    int insertSelective(TSurveyAnswers record);

    TSurveyAnswers selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(TSurveyAnswers record);

    int updateByPrimaryKey(TSurveyAnswers record);
}