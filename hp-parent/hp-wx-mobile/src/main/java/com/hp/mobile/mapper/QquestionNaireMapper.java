package com.hp.mobile.mapper;

import java.util.List;
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
    
     List<QquestionNaire> selectQquestionNaireByEnable(Byte  enable  ); 
    
    
}