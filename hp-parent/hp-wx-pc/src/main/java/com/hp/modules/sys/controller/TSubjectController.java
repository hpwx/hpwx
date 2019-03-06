package com.hp.modules.sys.controller;

import com.hp.common.utils.PageUtils;
import com.hp.common.utils.R;
import com.hp.common.validator.ValidatorUtils;
import com.hp.common.validator.group.AddGroup;
import com.hp.common.validator.group.UpdateGroup;
import com.hp.modules.sys.entity.TQuestionnaire;
import com.hp.modules.sys.entity.TSubject;
import com.hp.modules.sys.service.TQuestionnaireService;
import com.hp.modules.sys.service.TSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/sys/subject")
public class TSubjectController extends AbstractController{

    @Autowired
    private TSubjectService tSubjectService;

    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){

        //获取所有问卷集合
        PageUtils page = tSubjectService.getAll(params);

        return R.ok().put("page", page);
    }

    @GetMapping("/add")
    public R add(@RequestBody TSubject tSubject){

        ValidatorUtils.validateEntity(tSubject, AddGroup.class);
        //获取用户
        tSubject.setCreateUser(getUserId().toString());
        //获取所有问卷集合
        tSubjectService.save(tSubject);

        return R.ok();
    }

    @GetMapping("/update")
    public R update(@RequestBody TSubject tSubject){

        ValidatorUtils.validateEntity(tSubject, UpdateGroup.class);
        //获取用户
        tSubject.setCreateUser(getUserId().toString());
        //获取所有问卷集合
        tSubjectService.update(tSubject);

        return R.ok();
    }

    //@SysLog("删除问卷")  先不记录日志
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        tSubjectService.deleteBatch(ids);
        return R.ok();
    }


}
