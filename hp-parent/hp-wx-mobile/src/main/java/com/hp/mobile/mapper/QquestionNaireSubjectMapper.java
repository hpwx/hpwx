package com.hp.mobile.mapper;

import com.hp.mobile.entity.QquestionNaireSubject;

public interface QquestionNaireSubjectMapper {
    int deleteByPrimaryKey(Long objectId);

    int insert(QquestionNaireSubject record);

    int insertSelective(QquestionNaireSubject record);

    QquestionNaireSubject selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(QquestionNaireSubject record);

    int updateByPrimaryKey(QquestionNaireSubject record);
}