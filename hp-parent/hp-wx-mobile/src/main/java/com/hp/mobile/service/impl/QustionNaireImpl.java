package com.hp.mobile.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hp.mobile.entity.QquestionNaire;
import com.hp.mobile.entity.Subject;
import com.hp.mobile.entity.UserInfo;
import com.hp.mobile.mapper.QquestionNaireMapper;
import com.hp.mobile.mapper.SubjectMapper;
import com.hp.mobile.mapper.UserAnswerDeatilMapper;
import com.hp.mobile.mapper.UserInfoMapper;
import com.hp.mobile.service.IQuestionNaire;
import com.hp.mobile.service.IuserService;
import com.ym.ms.exception.SysException;
@Service
public class QustionNaireImpl implements IQuestionNaire {
  @Autowired
  private IuserService  userSerice;
  
  @Autowired  
  QquestionNaireMapper  qustionNaireMapper;
  
  @Autowired
  private  SubjectMapper  subject;
  
  @Autowired 
  private  UserAnswerDeatilMapper  userAnserDetail;
  
  private     
    
  public Map<String, Object> getUserQustionNarie(String openId) {
   
    UserInfo  userinfo= userSerice.getUserByOpenId(openId);
     if ( userinfo.get==null) {
       throw  new SysException("没有可做的问卷");
     }
   
     //  获取用户的问卷信息。
      String  qustionnares=  userinfo.getQuestionnaireids();
      String []   qustionnaresIds= qustionnares.split(",");
          
          String Id=  qustionnaresIds[0];
           
           
          Map<String ,Object> map= new HashMap<String, Object>();
    
    
          map.put("icon", qustionnaire.getIcon());
          map.put("conver", qustionnaire.getCover() ); 
          map.put("titile", qustionnaire.getTitle() );
          map.put("backimg", qustionnaire.getBackColor());
  //      for(String  qustinnareId : qustionnaresIds) {
  //                    
  //      }
    
    return null;
  }

  

}
