package com.hp.mobile.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.hp.mobile.entity.PoJoSubjectInfo;
import com.hp.mobile.entity.TSurveyAnswers;

@Mapper
public interface PoJoSubjectInfoMapper {

  
    List<PoJoSubjectInfo>  getSubjectList( Long  questionnaireid)  ;
    
    
    List<TSurveyAnswers>   getAllChoiceitemBySubjectIds(  List<Long> questionId);
  
}
