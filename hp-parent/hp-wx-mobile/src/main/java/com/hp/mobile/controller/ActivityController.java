package com.hp.mobile.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hp.mobile.entity.Activity;
import com.hp.mobile.service.IActivityService;
import com.ym.ms.entity.Result;

@RestController
@RequestMapping("/Activity")
public class ActivityController {

  @Autowired
  IActivityService activeserive;


  /***
   * 
   * @Author yuruyi
   * @Description 获取活动信息
   * @Date 2019年3月29日
   * @Param
   * @return
   *
   */
  @RequestMapping("/getActivity")
  public Result getActivity() {

    List<Activity> list = activeserive.getActivityBySatuts(1);
    if (list.size() > 0) {
      return Result.ok(list.get(0));
    } else {
      return Result.ok(list);
    }
  }



}
