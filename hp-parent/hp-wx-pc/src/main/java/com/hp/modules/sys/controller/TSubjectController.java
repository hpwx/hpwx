package com.hp.modules.sys.controller;

import com.hp.common.utils.Constant;
import com.hp.common.utils.ImageExtUtils;
import com.hp.common.utils.PageUtils;
import com.hp.common.utils.R;
import com.hp.common.validator.ValidatorUtils;
import com.hp.common.validator.group.AddGroup;
import com.hp.common.validator.group.UpdateGroup;
import com.hp.modules.sys.controller.ueditor.PublicMsg;
import com.hp.modules.sys.controller.ueditor.Ueditor;
import com.hp.modules.sys.entity.TQuestionnaire;
import com.hp.modules.sys.entity.TSubject;
import com.hp.modules.sys.entity.TSurveyAnswers;
import com.hp.modules.sys.service.TQuestionnaireService;
import com.hp.modules.sys.service.TSubjectService;
import com.hp.modules.sys.service.TSurveyAnswersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/subject")
public class TSubjectController extends AbstractController{

    @Autowired
    private TSubjectService tSubjectService;

    @Autowired
    private TSurveyAnswersService tSurveyAnswersService;



    @GetMapping("/addAnswers")
    public R addSubject(@RequestBody TSurveyAnswers tSurveyAnswers){

        ValidatorUtils.validateEntity(tSurveyAnswers, AddGroup.class);

        //获取所有问卷集合
        tSurveyAnswersService.save(tSurveyAnswers);
        return R.ok();
    }





    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){

        //获取所有问卷集合
        PageUtils page = tSubjectService.getAll(params);



        return R.ok().put("page", page);
    }

    @GetMapping("/add")
    public R add(@RequestBody List<TSubject> tSubjects){

        for(TSubject subject : tSubjects){
            ValidatorUtils.validateEntity(subject, AddGroup.class);
            subject.setCreateTime(new Date());
            subject.setCreateUser(getUserId().toString());
            subject.setDeleted(Constant.NO);
        }
        //获取所有问卷集合
        tSubjectService.insertBatch(tSubjects);

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

    /*
     * 题目文章上传
     * 暂时没有使用这个接口
     * */
    @PostMapping("/upload")
    public R upload(MultipartFile file){

        return ImageExtUtils.uploadImage(file);

    }

    /**
     * ueditor配置文件输出
     * @param request
     * @return
     */
    @GetMapping(value="/ueditor")
    public String ueditor(HttpServletRequest request) {

        return PublicMsg.UEDITOR_CONFIG;
    }


    /**
     * ueditor上传图片功能
     * @param upfile
     * @return
     */
    @RequestMapping(value = "/imgUpload")
    public Ueditor ueditorUpload(MultipartFile upfile){
        Ueditor ueditor = new Ueditor();
        if(upfile ==null){
            return ueditor;
        }
        R r = ImageExtUtils.uploadImage(upfile);
        ueditor.setState(PublicMsg.UeditorMsg.SUCCESS.get());
        ueditor.setUrl((String) r.get("url"));
        ueditor.setOriginal(upfile.getOriginalFilename());
        return ueditor;
    }
}
