package com.hp.modules.sys.controller;

import com.hp.common.utils.*;
import com.hp.common.validator.ValidatorUtils;
import com.hp.common.validator.group.AddGroup;
import com.hp.common.validator.group.UpdateGroup;
import com.hp.modules.sys.entity.TQuestionnaire;
import com.hp.modules.sys.entity.TSubject;
import com.hp.modules.sys.service.TQuestionnaireService;
import com.hp.modules.sys.service.TSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/sys/questionnaire")
public class TQuestionnaireController extends AbstractController{

    @Autowired
    private TQuestionnaireService tQuestionnaireService;

    @Autowired
    private TSubjectService tSubjectService;

    @Value("${spring.tomcat.home}")
    private String tomcat_home;



    /*
    * 问卷文章上传
    * */
    @PostMapping("/upload")
    public R upload(MultipartFile file){


        Map<String,Object> map = new HashMap<>();

        if (file.isEmpty()) {
            return R.error("上传文件为空！");
        }else {
            //保存时的文件名
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            Calendar calendar = Calendar.getInstance();
            String dateName = df.format(calendar.getTime())+file.getOriginalFilename();

            System.out.println(dateName);
            //保存文件的绝对路径
            WebApplicationContext webApplicationContext = (WebApplicationContext) SpringContextUtils.applicationContext;
            ServletContext servletContext = webApplicationContext.getServletContext();
            String realPath = servletContext.getRealPath("/");
            String filePath = tomcat_home+"hp-parent\\hp-wx-pc\\src\\main\\resources\\static\\hpwx\\static_images" + File.separator+dateName;
            System.out.println("文件的绝对路径:"+filePath);

            File newFile = new File(filePath);

            //MultipartFile的方法直接写文件
            try {
                //上传文件
                file.transferTo(newFile);

                //数据库存储的相对路径
                String projectPath = servletContext.getContextPath();
                HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
                String contextpath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+projectPath;
                String url = contextpath + "/hpwx/static_images/"+dateName;
                System.out.println("相对路径:"+url);
                //文件名与文件URL存入数据库表
                map.put("url",url);


                return R.ok(map);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
                return R.error("服务器未响应！");
            }

        }
    }

    /*
    *  查询所有题目答案
    * */
    @GetMapping("/answers/{questionnaireId}")
    public R subjectsAndAnswers(@PathVariable("questionnaireId") Long questionnaireId){

        //获取所有问卷集合
        List<Map<String,Object>> subjectList = tSubjectService.selectAnswersByQID(questionnaireId);

        return R.ok().put("subjects", subjectList);
    }

    /*
    *  根据问卷查询所有题目
    * */
    @GetMapping("/subjects/{questionnaireId}")
    public R subjects(@PathVariable("questionnaireId") Long questionnaireId){

        //获取所有问卷集合
        List<TSubject> subjectList = tSubjectService.selectSubjectsByQID(questionnaireId);

        return R.ok().put("subjects", subjectList);
    }

    @GetMapping("/info/{questionnaireId}")
    public R info(@PathVariable("questionnaireId") Long questionnaireId){
        TQuestionnaire tQuestionnaire = tQuestionnaireService.selectById(questionnaireId);
        return R.ok().put("menu", tQuestionnaire);
    }

    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        WebApplicationContext webApplicationContext = (WebApplicationContext) SpringContextUtils.applicationContext;
        ServletContext servletContext = webApplicationContext.getServletContext();

        String projectPath = servletContext.getContextPath();
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String contextpath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+projectPath;

        System.out.println(contextpath);
        //获取所有问卷集合
        PageUtils page = tQuestionnaireService.getAll(params);

        return R.ok().put("page", page);
    }

    @PostMapping("/add")
    public R add(@RequestBody TQuestionnaire tQuestionnaire){

        tQuestionnaire.setCreateTime(new Date());
        tQuestionnaire.setCreateUser(getUserId()==null ? null : getUserId().toString());
        tQuestionnaire.setEnable(Constant.YES);

        ValidatorUtils.validateEntity(tQuestionnaire, AddGroup.class);
        //获取用户
        tQuestionnaire.setCreateUser(getUserId().toString());
        //获取所有问卷集合
        tQuestionnaireService.save(tQuestionnaire);

        return R.ok();
    }

    @GetMapping("/update")
    public R update(@RequestBody TQuestionnaire tQuestionnaire){

        tQuestionnaire.setCreateTime(new Date());
        tQuestionnaire.setCreateUser(getUserId()==null ? null : getUserId().toString());
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
    public R delete(@RequestBody Long[] ids){
        //进行逻辑删除
        tQuestionnaireService.deleteByIds(ids);

        return R.ok();
    }


}
