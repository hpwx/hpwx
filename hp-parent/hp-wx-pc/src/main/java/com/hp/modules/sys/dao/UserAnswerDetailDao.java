package com.hp.modules.sys.dao;

import java.util.List;

import com.hp.modules.sys.entity.UserAnswerDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserAnswerDetailDao {
  int deleteByPrimaryKey(Long objectId);

  int insert(UserAnswerDetail record);

  int insertSelective(UserAnswerDetail record);

  UserAnswerDetail selectByPrimaryKey(Long objectId);

  int updateByPrimaryKeySelective(UserAnswerDetail record);

  int updateByPrimaryKey(UserAnswerDetail record);


  List<UserAnswerDetail> getAnserSubjectList(@Param("openid") String openid,
                                             @Param("commitid") String commitid);


  Integer getPollByStarNum(@Param("starNum") String starNum, @Param("subjectId") Long subjectId);

  Integer getToalPoll(@Param("questionnareId") String questionnareid,
                      @Param("subjectId") String subjectId, @Param("subjecttype") String subjecttype);
}
