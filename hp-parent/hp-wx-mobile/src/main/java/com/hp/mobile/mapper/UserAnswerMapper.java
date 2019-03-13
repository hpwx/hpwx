package com.hp.mobile.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.hp.mobile.entity.UserAnswer;
@Mapper
public interface UserAnswerMapper {
    int deleteByPrimaryKey(Long objectId);

    int insert(UserAnswer record);

    Long insertSelective(UserAnswer record);

    UserAnswer selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(UserAnswer record);

    int updateByPrimaryKey(UserAnswer record);
    
    
    UserAnswer   getAnserQustionaire(@Param("openid") String openid,  @Param("serialnum") String serialnum,@Param("questionId") Long questionId);
   List<UserAnswer>  getAnserQuestionNaireListByeOpenid( @Param("openid") String openid);


}