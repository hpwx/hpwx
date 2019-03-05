package com.hp.wx.mapper;

import com.hp.wx.entity.TSubject;

public interface TSubjectMapper {
    int deleteByPrimaryKey(Long objectId);

    int insert(TSubject record);

    int insertSelective(TSubject record);

    TSubject selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(TSubject record);

    int updateByPrimaryKey(TSubject record);
}