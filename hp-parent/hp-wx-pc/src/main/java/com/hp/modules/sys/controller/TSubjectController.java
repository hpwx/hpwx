package com.hp.modules.sys.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.hp.common.utils.*;
import com.hp.common.validator.ValidatorUtils;
import com.hp.common.validator.group.AddGroup;
import com.hp.common.validator.group.UpdateGroup;
import com.hp.hpenum.SubjectEnum;
import com.hp.modules.sys.controller.ueditor.PublicMsg;
import com.hp.modules.sys.controller.ueditor.Ueditor;
import com.hp.modules.sys.entity.TQuestionnaire;
import com.hp.modules.sys.entity.TSubject;
import com.hp.modules.sys.entity.TSurveyAnswers;
import com.hp.modules.sys.form.SubjectForm;
import com.hp.modules.sys.service.TQuestionnaireService;
import com.hp.modules.sys.service.TSubjectService;
import com.hp.modules.sys.service.TSurveyAnswersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/subject")
public class TSubjectController extends AbstractController {

    @Autowired
    private TSubjectService tSubjectService;

    @Autowired
    private TSurveyAnswersService tSurveyAnswersService;


    @GetMapping("/addAnswers")
    public R addSubject(@RequestBody TSurveyAnswers tSurveyAnswers) {

        ValidatorUtils.validateEntity(tSurveyAnswers, AddGroup.class);

        //获取所有问卷集合
        tSurveyAnswersService.save(tSurveyAnswers);
        return R.ok();
    }


    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {

        //获取所有问卷集合
        if (params.containsKey("name") || params.containsKey("questionnaireId")) {
            Page<TSubject> page = tSubjectService.getSubjectByQuestionnaireId(params, new Query<TSubject>(params).getPage());
            return R.ok().put("page", new PageUtils(page));
        } else {
            PageUtils page = tSubjectService.getAll(params);
            return R.ok().put("page", page);
        }

    }

//
//    @GetMapping("/add")
//    public R add(@RequestBody List<TSubject> tSubjects){
//
//        for(TSubject subject : tSubjects){
//            ValidatorUtils.validateEntity(subject, AddGroup.class);
//            subject.setCreateTime(new Date());
//            subject.setCreateUser(getUserId().toString());
//            subject.setDeleted(Constant.NO);
//        }
//        //获取所有问卷集合
//        tSubjectService.insertBatch(tSubjects);
//
//        return R.ok();
//    }

//    @GetMapping("/update")
//    public R update(@RequestBody TSubject tSubject){
//
//        ValidatorUtils.validateEntity(tSubject, UpdateGroup.class);
//        //获取用户
//        tSubject.setCreateUser(getUserId().toString());
//        //获取所有问卷集合
//        tSubjectService.update(tSubject);
//
//        return R.ok();
//    }

    //@SysLog("删除问卷")  先不记录日志
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        tSubjectService.deleteBatch(ids);
        return R.ok();
    }

    /*
     * 题目文章上传
     * 暂时没有使用这个接口
     * */
    @PostMapping("/upload")
    public R upload(MultipartFile file) {

        return ImageExtUtils.uploadImage(file);

    }

    /**
     * ueditor配置文件输出
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/ueditor")
    public String ueditor(HttpServletRequest request) {

        return PublicMsg.UEDITOR_CONFIG;
    }


    /**
     * ueditor上传图片功能
     *
     * @param upfile
     * @return
     */
    @RequestMapping(value = "/imgUpload")
    public Ueditor ueditorUpload(MultipartFile upfile) {
        Ueditor ueditor = new Ueditor();
        if (upfile == null) {
            return ueditor;
        }
//        R r = ImageExtUtils.uploadImage(upfile);
        R r = ImagesWriterAndReader.uploadAwsImage(upfile);
        ueditor.setState(PublicMsg.UeditorMsg.SUCCESS.get());
        ueditor.setUrl((String) r.get("url"));
        ueditor.setOriginal(upfile.getOriginalFilename());
        return ueditor;
    }

    /**
     * 新增问卷题目接口
     *
     * @param subjectForm
     * @return
     */
    @PostMapping(value = "/add")
    public R add(@RequestBody SubjectForm subjectForm) {

        if (subjectForm == null) {
            return R.error("没有传过来的数据哦！");
        }
        String userID = getUserId().toString();

        return tSubjectService.insertSubject(subjectForm, userID);
    }

    /**
     * 编辑问题 问题id subjectId
     *
     * @param objectId
     * @return
     */
    @GetMapping(value = "/info/{objectId}")
    public R add(@PathVariable("objectId") Long objectId) {
        if (objectId == null) {
            return R.error("没有传过来的数据哦！");
        }
        String userID = getUserId().toString();
        if (StringUtils.isEmpty(userID)) {
            return R.error("没有登录哦！");
        }

        SubjectForm subjectForm = tSubjectService.selectSubjectForm(objectId);

        return R.ok().put("subject", subjectForm);
    }


    @PostMapping(value = "/update")
    public R update(@RequestBody SubjectForm subjectForm) {

        if (subjectForm == null) {
            return R.error("没有传过来的数据哦！");
        }
        String userID = getUserId().toString();
        if (StringUtils.isEmpty(userID)) {
            return R.error("没有登录哦！");
        }
        String subjectId = subjectForm.getSubjectId();
        if (StringUtils.isEmpty(subjectId)) {
            return R.error("没有问题id啊");
        }

        return tSubjectService.updateSubject(subjectForm, userID);

    }
}
