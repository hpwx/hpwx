package com.hp.modules.sys.dao;

import com.hp.modules.sys.entity.QquestionNaire;

public interface QquestionNaireMapper {
    int deleteByPrimaryKey(Long objectId);

    int insert(QquestionNaire record);

    int insertSelective(QquestionNaire record);

    QquestionNaire selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(QquestionNaire record);

    int updateByPrimaryKey(QquestionNaire record);
}