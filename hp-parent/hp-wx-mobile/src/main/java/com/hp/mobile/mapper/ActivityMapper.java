package com.hp.mobile.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.hp.mobile.entity.Activity;

@Mapper
public interface ActivityMapper {
  int deleteByPrimaryKey(Long objectId);

  int insert(Activity record);

  int insertSelective(Activity record);

  Activity selectByPrimaryKey(Long objectId);

  List<Activity> selectBySatus(@Param("status") Integer status);

  int updateByPrimaryKeySelective(Activity record);

  int updateByPrimaryKey(Activity record);
}
