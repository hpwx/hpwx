package com.hp.mobile.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.hp.mobile.entity.UserAnswerDeatil;

@Mapper
public interface UserAnswerDeatilMapper {
  int deleteByPrimaryKey(Long objectId);

  int insert(UserAnswerDeatil record);

  int insertSelective(UserAnswerDeatil record);

  UserAnswerDeatil selectByPrimaryKey(Long objectId);

  int updateByPrimaryKeySelective(UserAnswerDeatil record);

  int updateByPrimaryKey(UserAnswerDeatil record);


  List<UserAnswerDeatil> getAnserSubjectList(@Param("openid") String openid,
      @Param("commitid") String commitid);


  Integer getPollByStarNum(@Param("starNum") String starNum, @Param("subjectId") Long subjectId);

  Integer getToalPoll(@Param("questionnareId") String questionnareid,
      @Param("subjectId") String subjectId, @Param("subjecttype") String subjecttype);
}
