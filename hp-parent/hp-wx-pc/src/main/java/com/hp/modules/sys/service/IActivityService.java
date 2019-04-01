package com.hp.modules.sys.service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.service.IService;
import com.hp.common.utils.PageUtils;
import com.hp.modules.sys.entity.Activity;

public interface IActivityService extends IService<Activity> {

  List<Activity> getActivityList();

  PageUtils getAll(Map<String, Object> params);

  void save(Activity activity);

  void update(Activity activity);

  void deleteBatch(Long[] ids);

  PageUtils getActivityByCondition(Map<String, Object> map);


  Activity getActivityByObjectId(Long objectId);



}
