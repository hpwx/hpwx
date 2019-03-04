package com.hp.modules.sys.controller;

import com.hp.common.utils.Constant;
import com.hp.common.utils.PageUtils;
import com.hp.common.utils.R;
import com.hp.modules.sys.service.TQuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/sys/questionnaire")
public class TQuestionnaireController extends AbstractController{

    @Autowired
    private TQuestionnaireService tQuestionnaireService;

    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        //只有超级管理员，才能查看所有管理员列表
        if(getUserId() != Constant.SUPER_ADMIN){
            params.put("createUserId", getUserId());
        }
        PageUtils page = tQuestionnaireService.getAll(params);

        return R.ok().put("page", page);
    }


}
