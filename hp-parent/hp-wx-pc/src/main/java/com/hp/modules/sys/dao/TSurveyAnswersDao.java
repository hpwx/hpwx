package com.hp.modules.sys.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hp.modules.sys.entity.TSurveyAnswers;

@Mapper
@Repository
public interface TSurveyAnswersDao extends BaseMapper<TSurveyAnswers> {
  int deleteByPrimaryKey(Long objectId);

  int insertSelective(TSurveyAnswers record);

  TSurveyAnswers selectByPrimaryKey(Long objectId);

  int updateByPrimaryKeySelective(TSurveyAnswers record);

  int updateByPrimaryKey(TSurveyAnswers record);

  int selectIdByChoiceText(@Param("text") String text, @Param("subjectId") Long subjectId);

  List<TSurveyAnswers> selectTSurveyAnswersBySubjectId(Long objectId);

  List<TSurveyAnswers> selectListBySubjectId(@Param("subjectid") Long subjectid);
}
