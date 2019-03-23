package com.hp.mobile.service;

import java.util.List;
import java.util.Map;
import com.hp.mobile.entity.QquestionNaire;
import com.hp.mobile.entity.UserAnswer;

public interface IQuestionNaire {

  Map<String, Object> getUserQustionNarie(String openId);

  Map<String, Object> commitSubject(Map<String, Object> map);

  Map<String, Object> findSubjectResult(Map<String, Object> map);


  List<UserAnswer> getQuestionNaireList(String openid);

  List<QquestionNaire> getActiveQuestionNaireInfo();

  Map<String, Object> checkQuestionNaireAnswer(String questionnaireid, String openid);



}
