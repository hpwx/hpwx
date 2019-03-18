package com.hp.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hp.modules.sys.entity.TQuestionnaire;
import com.hp.modules.sys.entity.TSurveyAnswers;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TSurveyAnswersDao extends BaseMapper<TSurveyAnswers> {
    int deleteByPrimaryKey(Long objectId);

    int insertSelective(TSurveyAnswers record);

    TSurveyAnswers selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(TSurveyAnswers record);

    int updateByPrimaryKey(TSurveyAnswers record);

    int selectIdByChoiceText(String text);

    List<TSurveyAnswers> selectTSurveyAnswersBySubjectId(Long objectId);
}