package com.hp.mobile.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.hp.mobile.entity.QquestionNaireSubject;

@Mapper
public interface QquestionNaireSubjectMapper {
    int deleteByPrimaryKey(Long objectId);

    int insert(QquestionNaireSubject record);

    int insertSelective(QquestionNaireSubject record);

    QquestionNaireSubject selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(QquestionNaireSubject record);

    int updateByPrimaryKey(QquestionNaireSubject record);
     
   // List<Long>  selectQuestionIdsByQuestionNaireId( Long questionNaireId); 
    
    
    
}