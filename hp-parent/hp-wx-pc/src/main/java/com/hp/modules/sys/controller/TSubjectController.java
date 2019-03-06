package com.hp.modules.sys.controller;

import com.hp.common.utils.PageUtils;
import com.hp.common.utils.R;
import com.hp.common.validator.ValidatorUtils;
import com.hp.common.validator.group.AddGroup;
import com.hp.common.validator.group.UpdateGroup;
import com.hp.modules.sys.entity.TQuestionnaire;
import com.hp.modules.sys.service.TQuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/sys/subject")
public class TSubjectController extends AbstractController{

    @Autowired
    private TQuestionnaireService tQuestionnaireService;

    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){

        //获取所有问卷集合
        PageUtils page = tQuestionnaireService.getAll(params);

        return R.ok().put("page", page);
    }

    @GetMapping("/add")
    public R add(@RequestBody TQuestionnaire tQuestionnaire){

        ValidatorUtils.validateEntity(tQuestionnaire, AddGroup.class);
        //获取用户
        tQuestionnaire.setCreateUser(getUserId().toString());
        //获取所有问卷集合
        tQuestionnaireService.save(tQuestionnaire);

        return R.ok();
    }

    @GetMapping("/update")
    public R update(@RequestBody TQuestionnaire tQuestionnaire){

        ValidatorUtils.validateEntity(tQuestionnaire, UpdateGroup.class);
        //获取用户
        tQuestionnaire.setCreateUser(getUserId().toString());
        //获取所有问卷集合
        tQuestionnaireService.update(tQuestionnaire);

        return R.ok();
    }

    //@SysLog("删除问卷")  先不记录日志
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        tQuestionnaireService.deleteBatch(ids);
        return R.ok();
    }


}
