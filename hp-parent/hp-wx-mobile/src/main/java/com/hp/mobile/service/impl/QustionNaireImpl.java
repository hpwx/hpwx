package com.hp.mobile.service.impl;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hp.mobile.entity.UserInfo;
import com.hp.mobile.mapper.UserInfoMapper;
import com.hp.mobile.service.IQuestionNaire;
import com.hp.mobile.service.IuserService;
import com.ym.ms.exception.SysException;
@Service
public class QustionNaireImpl implements IQuestionNaire {
  @Autowired
  private IuserService  userSerice;
  public Map<String, Object> getUserQustionNarie(String openId) {
   
    UserInfo  userinfo= userSerice.getUserByOpenId(openId);
    
     if ( userinfo==null) {
       
       throw  new SysException("没有可做的问卷");
     }
   
     
      String  qustionnares=  userinfo.getQuestionnaireids();
      String []   qustionnaresIds= qustionnares.split(",");
         
          String Id=  qustionnaresIds[0];
      
//      for (String  qustinnareId : qustionnaresIds) {
//                    
//      }
    
    return null;
  }

  

}
