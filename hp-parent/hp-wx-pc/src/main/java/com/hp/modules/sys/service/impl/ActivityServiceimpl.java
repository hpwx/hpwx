package com.hp.modules.sys.service.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hp.common.utils.PageUtils;
import com.hp.common.utils.Query;
import com.hp.modules.sys.dao.ActivityDao;
import com.hp.modules.sys.entity.Activity;
import com.hp.modules.sys.service.IActivityService;
import com.ym.ms.paging.PagerEO;



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
  public PageUtils getActivityByCondition(Map<String, Object> map, PagerEO<?> pe) {
    // TODO Auto-generated method stub

    // Page<Activity> page = this.selectPage(new Query<Activity>(map).getPage());


    // PageHelper.startPage(pe.getPage(), pe.getLimit());
    // PageHelper.startPage(pe.getPage(), pe.getLimit());

    // List<Activity> list = activityDao.selectSubjectListByCondition(map);
    Page<Activity> page = null;

    if (map.get("name") == null) {
      page = this.selectPage(new Query<Activity>(map).getPage(), new EntityWrapper<Activity>());
    } else {
      page = this.selectPage(new Query<Activity>(map).getPage(), new EntityWrapper<Activity>().like(
          StringUtils.isNotBlank(map.get("name").toString()), "name", map.get("name").toString()));
    }



    return new PageUtils(page);



  }

  @Override
  public Activity getActivityByObjectId(Long objectId) {
    // TODO Auto-generated method stub

    return activityDao.selectByPrimaryKey(objectId);

  }



}
