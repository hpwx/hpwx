package com.hp.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hp.modules.sys.entity.TQuestionnaire;
import com.hp.modules.sys.entity.TUserAnswerDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TUserAnswerDetailDao extends BaseMapper<TUserAnswerDetail> {
    int deleteByPrimaryKey(Long objectId);

    int insertSelective(TUserAnswerDetail record);

    TUserAnswerDetail selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(TUserAnswerDetail record);

    int updateByPrimaryKey(TUserAnswerDetail record);
}