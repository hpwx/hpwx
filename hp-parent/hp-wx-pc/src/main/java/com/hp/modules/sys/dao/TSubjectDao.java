package com.hp.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hp.modules.sys.entity.TQuestionnaire;
import com.hp.modules.sys.entity.TSubject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TSubjectDao extends BaseMapper<TSubject> {
    int deleteByPrimaryKey(Long objectId);

    int insertSelective(TSubject record);

    TSubject selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(TSubject record);

    int updateByPrimaryKey(TSubject record);
}