package com.hp.modules.sys.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hp.common.utils.PageUtils;
import com.hp.modules.sys.dao.ActivityDao;
import com.hp.modules.sys.entity.Activity;
import com.hp.modules.sys.service.IActivityService;


@Service
public class ActivityServiceimpl extends ServiceImpl<ActivityDao, Activity>
    implements IActivityService {

  @Autowired
  private ActivityDao activityDao;

  @Override
  public List<Activity> getActivityList() {



    return null;
  }

  @Override
  public PageUtils getAll(Map<String, Object> params) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void save(Activity activity) {
    // TODO Auto-generated method stub
    activityDao.insertSelective(activity);
  }

  @Override
  public void update(Activity activity) {
    // TODO Auto-generated method stub
    activityDao.updateByPrimaryKeySelective(activity);
  }

  @Override
  public void deleteBatch(Long[] ids) {
    // TODO Auto-generated method stub

    activityDao.deleteBatchByOjectids(ids);

  }

  @Override
  public PageUtils getActivityByCondition(Map<String, Object> map) {
    // TODO Auto-generated method stub

    // Page<Activity> page = this.selectPage(new Query<Activity>(map).getPage());

    // PageHelper.startPage(pe.getPage(), pe.getLimit());
    List<Activity> list = activityDao.selectSubjectListByCondition(map);
    PageUtils pageutils = new PageUtils(list, list.size(),
        Integer.valueOf(map.get("limit").toString()), Integer.valueOf(map.get("page").toString()));

    return pageutils;



  }

  @Override
  public Activity getActivityByObjectId(Long objectId) {
    // TODO Auto-generated method stub

    return activityDao.selectByPrimaryKey(objectId);

  }



}
