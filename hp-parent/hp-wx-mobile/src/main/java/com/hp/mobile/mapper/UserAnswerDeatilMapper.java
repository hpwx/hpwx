package com.hp.mobile.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.hp.mobile.entity.UserAnswerDeatil;
@Mapper
public interface UserAnswerDeatilMapper {
    int deleteByPrimaryKey(Long objectId);

    int insert(UserAnswerDeatil record);

    int insertSelective(UserAnswerDeatil record);

    UserAnswerDeatil selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(UserAnswerDeatil record);

    int updateByPrimaryKey(UserAnswerDeatil record);
    
      
}