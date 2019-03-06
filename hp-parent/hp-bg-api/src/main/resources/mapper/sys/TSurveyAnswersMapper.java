package com.hp.wx.mapper;

import com.hp.wx.entity.TSurveyAnswers;

public interface TSurveyAnswersMapper {
    int deleteByPrimaryKey(Long objectId);

    int insert(TSurveyAnswers record);

    int insertSelective(TSurveyAnswers record);

    TSurveyAnswers selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(TSurveyAnswers record);

    int updateByPrimaryKey(TSurveyAnswers record);
}