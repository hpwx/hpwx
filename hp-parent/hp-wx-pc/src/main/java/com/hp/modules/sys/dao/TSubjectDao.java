package com.hp.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.hp.modules.sys.entity.TQuestionnaire;
import com.hp.modules.sys.entity.TQuestionnaireSubject;
import com.hp.modules.sys.entity.TSubject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TSubjectDao extends BaseMapper<TSubject> {
    int deleteByPrimaryKey(Long objectId);

    int insertSelective(TSubject record);

    TSubject selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(TSubject record);

    int updateByPrimaryKey(TSubject record);

    List<TSubject> selectSubjectsByQID(@Param("objectId") Long objectId);

    List<Map<String,Object>> selectAnswersByQID(@Param("objectId") Long objectId);

    int insertBatch(List<TSubject> userList);

    List<TSubject> selectSubjectListByQuestionnaireId(Map<String,Object> map,Pagination page);

}