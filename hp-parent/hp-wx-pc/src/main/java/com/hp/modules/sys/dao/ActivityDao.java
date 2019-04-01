package com.hp.modules.sys.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hp.modules.sys.entity.Activity;

@Mapper
@Repository
public interface ActivityDao extends BaseMapper<Activity> {

  int deleteByPrimaryKey(Long objectId);

  int insertSelective(Activity record);

  Activity selectByPrimaryKey(Long objectId);

  int updateByPrimaryKeySelective(Activity record);

  int updateByPrimaryKey(Activity record);

  int deleteBatchByOjectids(@Param("list") Long[] list);

  List<Activity> selectSubjectListByCondition(Map<String, Object> map);



}
