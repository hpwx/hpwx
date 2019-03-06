package com.hp.mobile.mapper;

import com.hp.mobile.entity.QquestionNaire;

public interface QquestionNaireMapper {
    int deleteByPrimaryKey(Long objectId);

    int insert(QquestionNaire record);

    int insertSelective(QquestionNaire record);

    QquestionNaire selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(QquestionNaire record);

    int updateByPrimaryKey(QquestionNaire record);
}