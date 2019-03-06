package com.hp.mobile.mapper;

import com.hp.mobile.entity.UserAnswerDeatil;

public interface UserAnswerDeatilMapper {
    int deleteByPrimaryKey(Long objectId);

    int insert(UserAnswerDeatil record);

    int insertSelective(UserAnswerDeatil record);

    UserAnswerDeatil selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(UserAnswerDeatil record);

    int updateByPrimaryKey(UserAnswerDeatil record);
}