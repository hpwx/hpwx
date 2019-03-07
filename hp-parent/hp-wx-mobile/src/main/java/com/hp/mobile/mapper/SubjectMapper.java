package com.hp.mobile.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.hp.mobile.entity.Subject;
@Mapper
public interface SubjectMapper {
    int deleteByPrimaryKey(Long objectId);

    int insert(Subject record);

    int insertSelective(Subject record);

    Subject selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(Subject record);

    int updateByPrimaryKey(Subject record);
    
    List<Subject> selectListBySubjectId(@Param("list")  List<Long> list);
    
    
}