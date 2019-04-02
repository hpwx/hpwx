package com.hp.modules.sys.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.hp.common.utils.ImagesWriterAndReader;
import com.hp.common.utils.PageUtils;
import com.hp.common.utils.R;
import com.hp.modules.sys.entity.Activity;
import com.hp.modules.sys.service.IActivityService;
import com.ym.ms.paging.PagerEO;



@RestController
@RequestMapping("/sys/activity")
public class ActivityController {

  @Autowired
  IActivityService activityService;

  /**
   * 
   * @Author yuruyi
   * @Description 批量删除
   * @Date 2019年3月29日
   * @Param
   * @return
   *
   */
  @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
  public R deleteBatchByOjectids(@RequestBody Long[] ids) {
    activityService.deleteBatch(ids);
    return R.ok();
  }



  /**
   * 
   * @Author yuruyi
   * @Description 查询
   * @Date 2019年3月29日
   * @Param
   * @return
   *
   */
  @RequestMapping("/getActivityList")
  public R getActivityList(@RequestParam Map<String, Object> map, PagerEO<?> pe) {

    PageUtils page = activityService.getActivityByCondition(map, pe);
    return R.ok().put("page", page);

  }


  /**
   * 
   * @Author yuruyi
   * @Description 修改
   * @Date 2019年3月29日
   * @Param
   * @return
   *
   */
  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public R updateActivity(@RequestBody Activity activityinfo) {
    activityService.update(activityinfo);
    return R.ok();
  }


  /**
   * 
   * @Author yuruyi
   * @Description 添加
   * @Date 2019年3月29日
   * @Param
   * @return
   *
   */
  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public R saveActivity(@RequestBody Activity activityinfo) {
    activityService.save(activityinfo);
    return R.ok();
  }

  /**
   * 
   * @Author yuruyi
   * @Description 获取单个 活动信息
   * @Date 2019年3月29日
   * @Param
   * @return
   *
   */
  @RequestMapping(value = "/getActivityById")
  public R getActivity(String id) {
    Map<String, Object> map = new HashMap<>();
    if (StringUtils.isEmpty(id)) {

      return R.error("Id 参数不能为空！！");
    }
    Activity activeinfo = activityService.getActivityByObjectId(Long.parseLong(id));

    map.put("data", activeinfo);

    return R.ok(map);
  }



  /***
   * 
   * @Author yuruyi
   * @Description 上传活动背景
   * @Date 2019年3月30日
   * @Param
   * @return
   *
   */
  @PostMapping("/upload")
  public R upload(MultipartFile file) {

    return ImagesWriterAndReader.uploadAwsImage(file);
  }

  /***
   * 设置是否打开活动。
   * 
   * @Author yuruyi
   * @Description
   * @Date 2019年3月30日
   * @Param
   * @return
   *
   */
  @RequestMapping("/openAndCloseActivity")
  public R openAndCloseActivity(@RequestParam Map<String, Object> map) {

    if (map.get("status") == null) {

      return R.error("status 参数不能为空！！");
    }
    if (map.get("objectid") != null) {


      return R.error("objectid 参数不能为空！！");
    }
    Activity activtyinfo = new Activity();
    activtyinfo.setObjectId(Long.valueOf(map.get("objectid").toString()));
    activtyinfo.setStatus(Byte.valueOf(map.get("status").toString()));
    activityService.update(activtyinfo);
    return R.ok();
  }



  @RequestMapping("/changeStatus")
  public R changeStatus(String id, String status) {
    if (status == null) {

      return R.error("status 参数不能为空！！");
    }
    Activity activtyinfo = new Activity();
    activtyinfo.setObjectId(Long.parseLong(id));
    activtyinfo.setStatus(Byte.valueOf(status));
    activityService.update(activtyinfo);
    return R.ok();

  }
}


