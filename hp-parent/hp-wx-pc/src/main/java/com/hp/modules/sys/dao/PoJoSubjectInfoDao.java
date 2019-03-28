package com.hp.modules.sys.dao;

import java.util.List;

import com.hp.modules.sys.entity.PoJoSubjectInfo;
import com.hp.modules.sys.entity.TSurveyAnswers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PoJoSubjectInfoDao {


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
