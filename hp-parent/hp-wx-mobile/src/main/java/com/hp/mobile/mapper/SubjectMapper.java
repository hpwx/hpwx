package com.hp.mobile.mapper;

import com.hp.mobile.entity.Subject;

public interface SubjectMapper {
    int deleteByPrimaryKey(Long objectId);

    int insert(Subject record);

    int insertSelective(Subject record);

    Subject selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(Subject record);

    int updateByPrimaryKey(Subject record);
}