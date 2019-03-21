package com.hp.modules.sys.controller;

import com.hp.common.utils.*;
import com.hp.common.validator.ValidatorUtils;
import com.hp.common.validator.group.AddGroup;
import com.hp.common.validator.group.UpdateGroup;
import com.hp.hpenum.QuestionnaireEnum;
import com.hp.modules.sys.entity.TQuestionnaire;
import com.hp.modules.sys.entity.TSubject;
import com.hp.modules.sys.service.TQuestionnaireService;
import com.hp.modules.sys.service.TSubjectService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.event.ObjectChangeListener;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/sys/questionnaire")
public class TQuestionnaireController extends AbstractController {

    @Autowired
    private TQuestionnaireService tQuestionnaireService;

    @Autowired
    private TSubjectService tSubjectService;

    @Value("${spring.tomcat.home}")
    private String tomcat_home;

    /*
     *  查询所有答案统计结果
     *  @tQuestionnaireId
     *  @subjectType
     *  @result Map
     * */
    @PostMapping("/statisticsOnResult")
    public R statisticsOnResult(@RequestParam Map<String, Object> params) {

        if (StringUtils.isBlank(params.get("tQuestionnaireId").toString()) || StringUtils.isBlank(params.get("subjectType").toString())) {
            return R.error("请求参数缺失！");
        }

        //获取所有问卷集合
        Map subjectList = tQuestionnaireService.statisticsOnResult(params);

        return R.ok().put("page", subjectList);
    }


    /*
     * 问卷文章上传
     * */
    @PostMapping("/upload")
    public R upload(MultipartFile file) {

        return ImageExtUtils.uploadImage(file);
//        return ImagesWriterAndReader.uploadAwsImage(file);
    }

    /*
     *  查询所有题目答案
     * */
    @GetMapping("/statistics")
    public R statistics(@RequestParam Map<String, Object> params) {

        //获取所有问卷集合
        PageUtils subjectList = tQuestionnaireService.selectStatistics(params);

        return R.ok().put("page", subjectList);
    }

    /*
     *  查询所有题目答案
     * */
    @PostMapping("/statisticsOnSubject")
    public R statisticsOnSubject(@RequestParam Map<String, Object> params) {

        //获取所有问卷集合
        PageUtils subjectList = tQuestionnaireService.selectStatistics(params);

        return R.ok().put("page", subjectList);
    }

    /*
     *  查询所有题目答案
     * */
    @GetMapping("/answers/{questionnaireId}")
    public R subjectsAndAnswers(@PathVariable("questionnaireId") Long questionnaireId) {

        //获取所有问卷集合
        List<Map<String, Object>> subjectList = tSubjectService.selectAnswersByQID(questionnaireId);

        return R.ok().put("subjects", subjectList);
    }

    /*
     *  根据问卷查询所有题目
     * */
    @GetMapping("/subjects/{questionnaireId}")
    public R subjects(@PathVariable("questionnaireId") Long questionnaireId) {

        //获取所有问卷集合
        List<TSubject> subjectList = tSubjectService.selectSubjectsByQID(questionnaireId);

        return R.ok().put("subjects", subjectList);
    }

    @GetMapping("/info/{questionnaireId}")
    public R info(@PathVariable("questionnaireId") Long questionnaireId) {
        TQuestionnaire tQuestionnaire = tQuestionnaireService.selectById(questionnaireId);
        return R.ok().put("menu", tQuestionnaire);
    }

    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        WebApplicationContext webApplicationContext = (WebApplicationContext) SpringContextUtils.applicationContext;
        ServletContext servletContext = webApplicationContext.getServletContext();

        String projectPath = servletContext.getContextPath();
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String contextpath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + projectPath;

        System.out.println(contextpath);
        //获取所有问卷集合
        PageUtils page = tQuestionnaireService.getAll(params);

        return R.ok().put("page", page);
    }

    @PostMapping("/add")
    public R add(@RequestBody TQuestionnaire tQuestionnaire) {

        tQuestionnaire.setCreateTime(new Date());
        tQuestionnaire.setCreateUser(getUserId() == null ? null : getUserId().toString());
//        tQuestionnaire.setEnable(Constant.YES);
        tQuestionnaire.setDeleted(Constant.NO);

        ValidatorUtils.validateEntity(tQuestionnaire, AddGroup.class);
        //获取用户
        tQuestionnaire.setCreateUser(getUserId().toString());
        //获取所有问卷集合
        tQuestionnaireService.save(tQuestionnaire);

        return R.ok();
    }

    @PostMapping("/update")
    public R update(@RequestBody TQuestionnaire tQuestionnaire) {

        tQuestionnaire.setCreateTime(new Date());
        tQuestionnaire.setCreateUser(getUserId() == null ? null : getUserId().toString());
        tQuestionnaire.setEnable(Constant.YES);

        ValidatorUtils.validateEntity(tQuestionnaire, UpdateGroup.class);
        //获取用户
        tQuestionnaire.setCreateUser(getUserId().toString());
        //获取所有问卷集合
        tQuestionnaireService.update(tQuestionnaire);

        return R.ok();
    }

    //@SysLog("删除问卷")  先不记录日志
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        //进行逻辑删除
        tQuestionnaireService.deleteByIds(ids);

        return R.ok();
    }

    @PostMapping("/top")
    public R top(@RequestBody Long id) {

        tQuestionnaireService.top(id);

        return R.ok();
    }
    @PostMapping("/down")
    public R down(@RequestBody Long id) {

        tQuestionnaireService.down(id);

        return R.ok();
    }

    @PostMapping("/del")
    public R del(@RequestBody Long[] ids) {
        if (ids.length == 0) {
            return R.error("id为空");
        }
        tQuestionnaireService.del(ids);
        return R.ok();
    }

    @PostMapping("/enableOrUnEnable")
    public R enableOrUnEnable(@RequestParam Map<String, Object> map) {

        Long questionnaire = -1L;
        try {
            questionnaire =  Long.valueOf((String) map.get("questionnaireId"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (questionnaire == -1L) {
            return R.error("没有这个问卷");
        }
        TQuestionnaire tQuestionnaire = tQuestionnaireService.selectById(questionnaire);
        if (tQuestionnaire == null) {
            R.error("没有这个问卷");
        }
        String type = (String) map.get("enable");
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("questionnaireId", questionnaire);
        if (QuestionnaireEnum.UP.getMsg().equals(type.toLowerCase())) {
            m.put("enable", 1);
        } else if (QuestionnaireEnum.DOWN.getMsg().equals(type.toLowerCase())) {
            m.put("enable", 0);
        }
        tQuestionnaireService.updateQuestionEnable(m);

        return R.ok();
    }
}
