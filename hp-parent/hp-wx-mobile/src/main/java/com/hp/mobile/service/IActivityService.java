package com.hp.mobile.service;

import java.util.List;
import com.hp.mobile.entity.Activity;

public interface IActivityService {


  List<Activity> getActivityBySatuts(Integer status);
}
