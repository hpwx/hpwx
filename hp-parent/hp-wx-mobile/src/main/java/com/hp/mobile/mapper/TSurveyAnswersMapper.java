package com.hp.mobile.mapper;

 
 
import com.hp.mobile.entity.TSurveyAnswers;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TSurveyAnswersMapper  {
   
    int deleteByPrimaryKey(Long objectId);

    int insertSelective(TSurveyAnswers record);

    TSurveyAnswers selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(TSurveyAnswers record);
    
    int updateByPrimaryKey(TSurveyAnswers record);
    
   List<TSurveyAnswers>  selectListBySubjectId(@Param("subjectid") Long subjectid);
   
   List<TSurveyAnswers>  selectListByObjectId(@Param("list") List<String> objectidlist);
   
   
   
   int  updateByobjectIds(@Param("list")  List<String> list);
    
  
    
    
}