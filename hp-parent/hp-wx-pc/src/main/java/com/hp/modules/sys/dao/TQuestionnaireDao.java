package com.hp.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hp.modules.sys.entity.TQuestionnaire;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TQuestionnaireDao extends BaseMapper<TQuestionnaire> {
    int deleteByPrimaryKey(Long objectId);


    int insertSelective(TQuestionnaire record);

    TQuestionnaire selectByPrimaryKey(Long objectId);

    int updateByPrimaryKeySelective(TQuestionnaire record);

    int updateByPrimaryKey(TQuestionnaire record);

    List<Map<String,Object>> selectStatistics(@Param("params") Map<String, Object> params);

    int selectCountStatistics(@Param("params") Map<String, Object> params);

    List<Map<String,Object>> StatisticsOnTrue(@Param("params") Map<String, Object> params);

    List<Map<String,Object>> StatisticsOnAll(@Param("params") Map<String, Object> params);

    TQuestionnaire selectByIdIsHaveTop(Long id);

    void updateTopById(Long id);

    void updateEnableById(Map<String,Object> map);

    void updateDownById(Long id);

    void cancelEnableById(Long id);
}