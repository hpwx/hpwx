package com.hp.mobile.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hp.mobile.service.IQuestionNaire;
import com.ym.ms.entity.Result;

@RestController
@RequestMapping("/questionnaire")

 
public class QuestionNaireController {
  
 
  @Autowired
  private  IQuestionNaire  questionNaire;
  
  /**
   *  
   * @Author yuruyi
   * @Description    查询用户的问卷
   * @Date   2019年3月7日
   * @Param  
   * @return  
   *
   */
  @RequestMapping("/getQuestionNaireAndSubjectByOpenId")
  public Result  getQuestionNaireByOpenId(@RequestParam("openid")  String  openid) {
    
     Map<String,Object> map= questionNaire.getUserQustionNarie(openid);
     
     
     return Result.ok(map);
  }
  
  
  @RequestMapping("/commitQustionNaire")
  public Result  commitQustionNaire(@RequestParam  Map<String,Object>  map) {
    
     if  (map==null) {
       
       return Result.error("0002" ,"参数传递错误！");
     }
      
     
     
     return Result.ok(map);
  }
}
