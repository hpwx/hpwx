package com.hp.mobile.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.hp.mobile.entity.PoJoSubjectInfo;
import com.hp.mobile.entity.TSurveyAnswers;

@Mapper
public interface PoJoSubjectInfoMapper {


  List<PoJoSubjectInfo> getSubjectList(Long questionnaireid);


  List<TSurveyAnswers> getAllChoiceitemBySubjectIds(List<Long> questionId);



  /***
   * 
   * @Author yuruyi
   * @Description 查询 填空 、单选、多选
   * @Date 2019年3月25日
   * @Param
   * @return
   *
   */
  List<PoJoSubjectInfo> getSubjectListByQustionNaireIdAndSubjectType(
      @Param("questionnaireid") Long questionnaireid, @Param("typeid") Integer typeid);


  /***
   * 
   * @Author yuruyi
   * @Description 查询打分
   * @Date 2019年3月25日
   * @Param
   * @return
   *
   */
  List<PoJoSubjectInfo> getSubjectListForScoreStatics(
      @Param("questionnaireid") Long questionnaireid, @Param("typeid") Integer typeid);


}
