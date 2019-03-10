package com.hp.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hp.modules.sys.entity.TQuestionnaire;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TQuestionnaireDao extends BaseMapper<TQuestionnaire> {
    int deleteByPrimaryKey(Long objectId);


    int insertSelective(TQuestionnaire record);

    TQuestionnaire selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(TQuestionnaire record);

    int updateByPrimaryKey(TQuestionnaire record);

    List<Map<String,Object>> selectStatistics(@Param("params") Map<String, Object> params);

    int selectCountStatistics(@Param("params") Map<String, Object> params);
}