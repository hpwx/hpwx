package com.hp.mobile.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.hp.mobile.entity.QquestionNaire;
@Mapper
public interface QquestionNaireMapper {
    int deleteByPrimaryKey(Long objectId);

    int insert(QquestionNaire record);

    int insertSelective(QquestionNaire record);

    QquestionNaire selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(QquestionNaire record);

    int updateByPrimaryKey(QquestionNaire record);
}