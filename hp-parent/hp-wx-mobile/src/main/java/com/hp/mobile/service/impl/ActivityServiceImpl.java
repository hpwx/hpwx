package com.hp.mobile.service.impl;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hp.mobile.entity.Activity;
import com.hp.mobile.mapper.ActivityMapper;
import com.hp.mobile.service.IActivityService;

@Service
public class ActivityServiceImpl implements IActivityService {


  @Autowired
  ActivityMapper actiivtymapper;

  @Override
  public List<Activity> getActivityBySatuts(Integer status) {

    return actiivtymapper.selectBySatus(status);
  }



}
